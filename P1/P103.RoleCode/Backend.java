import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *The Backend class implements the BackendInterface and manages a collection of songs from a CSV file
 */
public class Backend implements BackendInterface{
    private IterableSortedCollection<Song> songTree;
    private Integer lowBound = null; // Tracks the low year bound for methods below
    private Integer highBound = null;  // tracks high year bound for methods below
    private Integer loudThreshold = null; // tracks the loudness filter set by user for methods below
    private static final Comparator<Song> yearComparator = Comparator.comparingInt(Song::getYear);

    /**
     *Creates new backend instance with a sorted collection of songs
     *
     * @param tree the collection that stores songs and maintain them in a specific order
     */
    public Backend(IterableSortedCollection<Song> tree) {
        this.songTree = tree;
    }

    /**
     * Loads data from the .csv file referenced by filename.  You can rely
     * on the exact headers found in the provided songs.csv, but you should
     * not rely on them always being presented in this order or on there
     * not being additional columns describing other song qualities.
     * After reading songs from the file, the songs are inserted into
     * the tree passed to this backend' constructor.  Don't forget to
     * create a Comparator to pass to the constructor for each Song object that
     * you create.  This will be used to store these songs in order within your
     * tree, and to retrieve them by year range in the getRange method.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    @Override
    public void readData(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String headerLine = br.readLine(); // reads first line (header)

            // parses the header to get relevant header names, if there is no header, file is empty so return exception
            if(headerLine == null) {
                throw new IOException("File is empty");}
            // Parse the header to extract column names
            List<String> headers = csvParse(headerLine);

            // create column index variables to get the position of each field
            int titleIndex = -1, artistIndex = -1, yearIndex = -1, genreIndex = -1,  bpmIndex = -1, energyIndex = -1,
                danceabilityIndex = -1, loudIndex = -1, liveIndex = -1;

            // Iterate through the headers to find the index of each colum
            for(int i = 0; i < headers.size(); i++) {
                String header = headers.get(i);
                switch (header) {
                    case "title": titleIndex = i; break;
                    case "artist": artistIndex = i; break;
                    case "top genre": genreIndex = i; break;
                    case "year": yearIndex = i; break;
                    case "bpm": bpmIndex = i; break;
                    case "nrgy": energyIndex = i; break;
                    case "dnce": danceabilityIndex = i; break;
                    case "dB": loudIndex = i; break;
                    case "live": liveIndex = i; break;
                }
            }
            // Reads the rest of the file
            String line;
            while ((line = br.readLine()) != null) {
                // Parses the current line into individual values
                List<String> splits = csvParse(line);

                // Gets and cleans each field based on the determined index
                String title = splits.get(titleIndex).replace("\"", "");
                String artist = splits.get(artistIndex).replace("\"", "");
                String genre = splits.get(genreIndex).replace("\"", "");
                int year = Integer.parseInt(splits.get(yearIndex).replace("\"", ""));
                int bpm = Integer.parseInt(splits.get(bpmIndex).replace("\"", ""));
                int energy = Integer.parseInt(splits.get(energyIndex).replace("\"", ""));
                int danceability = Integer.parseInt(splits.get(danceabilityIndex).replace("\"", ""));
                int loudness = Integer.parseInt(splits.get(loudIndex).replace("\"", ""));
                int liveness = Integer.parseInt(splits.get(liveIndex).replace("\"", ""));

                // Song object is created and inserted into the tree
                Song song = new Song(title, artist, genre, year, bpm, energy, danceability, loudness, liveness, yearComparator);
                songTree.insert(song);
            }

        } catch (IOException e) {
            // if any errors occur, IOException is thrown with error message
            throw new IOException("Error reading file" + e.getMessage());
        }
    }

    /**
     * Parses csv files into fields with commas handled
     * Source: Stack Overflow
     * https://stackoverflow.com/questions/15979688/how-to-handle-commas-in-the-data-of-a-csv-in-java
     *
     * @param line the line from the csv to be parsed
     * @return parsed line with the removal of any possible whitespace
     */
    private List<String> csvParse(String line) {
        // Splits CSV line to fields ensuring that commas onside double quotes are not split
        String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        List<String> parsedFields = new ArrayList<>();

        for(String part : parts) {
            part = part.trim(); // removes unnecessary spaces

            // If the part starts and ends with double quotes, outer quotes are removed
            if(part.length() > 1 && part.charAt(0) == '\"' && part.charAt(part.length()-1) == '\"') {
                part = part.substring(1, part.length()-1); // outer quotes removed
            }
            // double quotes are replaced by single quotes and added to parsedFields
            part = part.replace("\"\"", "\"");
            parsedFields.add(part);
        }
        return parsedFields;
    }

    /**
     * Retrieves a list of song titles from the tree passed to the contructor.
     * The songs should be ordered by the songs' year, and fall within
     * the specified range of year values.  This year range will
     * also be used by future calls to filterSongs and getFiveMost.
     *
     * If a loudness filter has been set using the filterSongs method
     * below, then only songs that pass that filter should be included in the
     * list of titles returned by this method.
     *
     * When null is passed as either the low or high argument to this method,
     * that end of the range is understood to be unbounded.  For example, a
     * argument for the hight parameter means that there is no maximum
     * year to include in the returned list.
     *
     * @param low is the minimum year of songs in the returned list
     * @param high is the maximum year of songs in the returned list
     * @return List of titles for all songs from low to high that pass any
     *     set filter, or an empty list when no such songs can be found
     */
    public List<String> getRange(Integer low, Integer high) {
        // setting the bounds
        this.lowBound = low;
        this.highBound = high;

        // Iterator to traverse through all songs in songTree
        Iterator<Song> songIterator = songTree.iterator();
        List<String> songs = new ArrayList<>();
        while (songIterator.hasNext()) {
            Song song = songIterator.next();

            // Checks if the song year is within the range specified
            boolean withinYear = (low == null || song.getYear() >= low ) && ( high == null || song.getYear() <= high);

            // Checks if loudness in within the threshold specified
            boolean loudnessThreshold = (loudThreshold == null || song.getLoudness() < loudThreshold);

            // If conditions are true, add the songs
            if(withinYear && loudnessThreshold) {
                songs.add(song.getTitle()); // song gets added
            }
        }
        return songs;
    }
    /**
     * Retrieves a list of song titles that have a loudness that is
     * smaller than the specified threshold.  Similar to the getRange
     * method: this list of song titles should be ordered by the songs'
     * year, and should only include songs that fall within the specified
     * range of year values that was established by the most recent call
     * to getRange.  If getRange has not previously been called, then no low
     * or high year bound should be used.  The filter set by this method
     * will be used by future calls to the getRange and fiveMost methods.
     *
     * When null is passed as the threshold to this method, then no
     * loudness threshold should be used.  This clears the filter.
     *
     * @param threshold filters returned song titles to only include songs that
     *     have a loudness that is smaller than this threshold.
     * @return List of titles for songs that meet this filter requirement and
     *     are within any previously set year range, or an empty list
     *     when no such songs can be found
     */
    public List<String> filterSongs(Integer threshold) {
        this.loudThreshold = threshold;
        // Iterator to traverse through all songs in songTree
        Iterator<Song> songIterator = songTree.iterator();
        List<String> filteredSongs = new ArrayList<>();
        while (songIterator.hasNext()) {
            Song song = songIterator.next();

            // Checks if the song year is within the range specified
            boolean withinYear = (lowBound == null || song.getYear() >= lowBound )
                                    && ( highBound == null || song.getYear() <= highBound);

            // Checks if loudness in within the threshold specified
            boolean loudnessThreshold = (loudThreshold == null || song.getLoudness() < loudThreshold);

            // If conditions are true, add the songs
            if(withinYear && loudnessThreshold) {
                filteredSongs.add(song.getTitle());
            }
        }
        return filteredSongs;
    }

    /**
     * This method returns a list of song titles representing the five
     * most danceable songs that both fall within any attribute range specified
     * by the most recent call to getRange, and conform to any filter set by
     * the most recent call to filteredSongs.  The order of the song titles
     * in this returned list is up to you.
     *
     * If fewer than five such songs exist, return all of them.  And return an
     * empty list when there are no such songs.
     *
     * @return List of five most danceable song titles
     */
    public List<String> fiveMost() {
        // Iterator to traverse through all songs in songTree
        Iterator<Song> songIterator = songTree.iterator();
        List<Song> songs = new ArrayList<>();
        while (songIterator.hasNext()) {
            Song song = songIterator.next();

            // Checks if the song year is within the range specified
            boolean withinYear = (lowBound == null || song.getYear() >= lowBound )
                                && ( highBound == null || song.getYear() <= highBound);

            // Checks if loudness in within the threshold specified
            boolean loudnessThreshold = (loudThreshold == null || song.getLoudness() < loudThreshold);

            // If conditions are true, add the songs
            if(withinYear && loudnessThreshold) {
                songs.add(song);
            }
        }
        // sort by ascending order
        Comparator<Song> danceability = Comparator.comparingInt(Song::getDanceability);
        songs.sort(danceability);

        List<String> fiveMostSongs = new ArrayList<>();
        int max = Math.min(5, songs.size()); // takes the min, either 5 or songs.size if less than 5
        for(int i =0; i < max; i++) {
            fiveMostSongs.add(songs.get(i).getTitle());
        }
        return fiveMostSongs;
    }
}