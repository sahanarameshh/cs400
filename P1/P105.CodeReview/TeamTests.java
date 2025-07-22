import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class TeamTests {
	public TeamTests() {}

	@Test
	public void testReadData() {
		IterableSortedCollection<Song> tree = new Tree_Placeholder();
	    BackendInterface backend = new Backend(tree);

        try {
            backend.readData("songs.csv");
        }
        catch (IOException e) {
            e.printStackTrace();
            assertEquals(1, 0, "IO Exception while reading data");
        }
	}

    @Test
	public void testGetRange() {
		IterableSortedCollection<Song> tree = new Tree_Placeholder();
	    BackendInterface backend = new Backend(tree);

        List<String> returned = backend.getRange(0, 1000000);
        System.out.println("getRange returned: " + returned);
        List<String> songs = new ArrayList<>();
        songs.add("A L I E N S");
        songs.add("BO$$");
        songs.add("Cake By The Ocean");

        assertEquals(returned, songs);
	}

    @Test
	public void testFiveMost() {
		IterableSortedCollection<Song> tree = new Tree_Placeholder();
	    BackendInterface backend = new Backend(tree);

        backend.getRange(0, 100000);
        backend.filterSongs(2016);

        List<String> songs = new ArrayList<>();
        songs.add("A L I E N S");
        songs.add("BO$$");
        songs.add("Cake By The Ocean");
        
        assertEquals(backend.fiveMost(), songs);
	}
}