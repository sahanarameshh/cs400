import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is the interface that a backend developer will implement, so that
 * a frontend developer's code can make use of this functionality.  It makes
 * use of a GraphADT to perform shortest path computations.
 */
public class Backend implements BackendInterface {
    private GraphADT<String, Double> graph = new Graph_Placeholder();
  /*
   * Implementing classes should support the constructor below.
   * @param graph object to store the backend's graph data
   */
  public Backend(GraphADT<String,Double> graph) {
    this.graph = graph;
  }

  /**
   * Loads graph data from a dot file. If a graph was previously loaded, this
   * method should first delete the contents (nodes and edges) of the existing 
   * graph before loading a new one.
   * @param filename the path to a dot file to read graph data from
   * @throws IOException if there was any problem reading from this file
   */
  public void loadGraphData(String filename) throws IOException {
    // check if the graph's empty
    if (graph.getNodeCount() != 0) {
        // get a list of the nodes
        List<String> currentGraph = graph.getAllNodes();
        // loop through the list
        for (String node : currentGraph) {
            // remove each node
            graph.removeNode(node);
        }
    }

    // try reading the file
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        // pass the first line
        String headerLine = br.readLine();
        // use it to make sure the file isn't empty
        if (headerLine == null) {
            throw new IOException("File is empty");}

        // start reading each line after the header
        String line;
        while ((line = br.readLine()) != "}" && (line = br.readLine()) != null) {
            List<Object> lineElements = lineSplitHelper(line);
            // using a helper method, get the node, edge, and weight from the line
            String nodeData = (String) lineElements.get(0);
            String edgeData = (String) lineElements.get(1);
            Double weightData = (Double) lineElements.get(2);

            // insert data into graph
            graph.insertNode(nodeData);
            graph.insertEdge(nodeData, edgeData, weightData);
        }
    }
    catch (IOException e) {
        // throw an exception if error
        throw new IOException("Error reading file.");
    }
  }

  private List<Object> lineSplitHelper(String line) {
    // make a list to store the node, edge, and weight data
    List<Object> lineElements = new ArrayList<>();
    
    char[] chars = line.toCharArray();
    int counter = 2;

    // find node data
    String nodeData = "";
    while (chars[counter] != '"') {
        nodeData += chars[counter];
        counter++;
    }

    // find edge data
    String edgeData = "";
    counter += 6;
    while (chars[counter] != '"') {
        edgeData += chars[counter];
        counter++;
    }

    // find weight data
    Double weightData = Double.parseDouble(line.substring(line.indexOf("=") + 1, line.indexOf("]")));

    // add elements to list and return them
    lineElements.add(nodeData);
    lineElements.add(edgeData);
    lineElements.add(weightData);
    return lineElements;
  }

  /**
   * Returns a list of all locations (node data) available in the graph.
   * @return list of all location names
   */
  public List<String> getListOfAllLocations() {
    // create an empty list to hold the node data
    List<String> locations = new ArrayList<>();
    
    // get a list of the nodes currently in the graph
    List<String> currentGraph = graph.getAllNodes();
    // loop through the list
    for (String node : currentGraph) {
        locations.add(node);
    }

    return locations;
  }

  /**
   * Return the sequence of locations along the shortest path from 
   * startLocation to endLocation, or an empty list if no such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the nodes along the shortest path from startLocation 
   *         to endLocation, or an empty list if no such path exists
   */
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
    // create the list that will hold the shortest path
    List<String> shortestLocationPath = new ArrayList<>();
    // check that the starting and ending locations exist
    List<String> currentGraph = graph.getAllNodes();
    if (!currentGraph.contains(startLocation) || !currentGraph.contains(startLocation)) {
        return shortestLocationPath;
    }

    // find the shortest path using the placeholder shortestPathData method
    shortestLocationPath = graph.shortestPathData(startLocation, endLocation);

    return shortestLocationPath;
  }

  /**
   * Return the walking times in seconds between each two nodes on the 
   * shortest path from startLocation to endLocation, or an empty list of no 
   * such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the walking times in seconds between two nodes along 
   *         the shortest path from startLocation to endLocation, or an empty 
   *         list if no such path exists
   */
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
    // create the list that will hold the times
    List<Double> shortestTimes = new ArrayList<>();
    // check that the starting and ending locations exist
    List<String> currentGraph = graph.getAllNodes();
    if (!currentGraph.contains(startLocation) || !currentGraph.contains(startLocation)) {
        return shortestTimes;
    }

    // get a list of shortest path
    List<String> shortestLocationPath = findLocationsOnShortestPath(startLocation, endLocation);
    // find the shortest time between each path using the placeholder shortestPathCost method
    for (int i = 0; i < shortestLocationPath.size() - 1; i++) {
        shortestTimes.add(graph.shortestPathCost(shortestLocationPath.get(i), shortestLocationPath.get(i+1)));
    }

    return shortestTimes;
  }
    
  /**
   * Returns the list of locations that can be reached when starting from the 
   * provided startLocation, and travelling a maximum of travelTime seconds.
   * @param startLocation the location to find the reachable locations from
   * @param travelTime is the maximum number of seconds away the start location
   *         that a destination must be in order to be returned
   * @return the list of destinations that can be reached from startLocation 
   *         in travelTime seconds or less
   * @throws NoSuchElementException if startLocation does not exist
   */
    public List<String> getReachableFromWithin(String startLocation, double travelTime) throws NoSuchElementException {
        // check if startLocation exists
        List<String> currentGraph = graph.getAllNodes();
        if (!currentGraph.contains(startLocation)) {
            throw new NoSuchElementException("startLocation does not exist");
        }

        // create a list that will hold the list of locations that can be reached form startLocation in max travelTime seconds
        List<String> locations = new ArrayList<>();

        // loop through the list
        for (String node : currentGraph) {
            // try travelling to each node in the graph from the start location
            if (!node.equals(startLocation)) {
                // get a list of shortest path
                List<String> shortestLocationPath = findLocationsOnShortestPath(startLocation, node);
                
                // get the total time it takes to traverse the shortest path
                List<Double> shortestTimes = findTimesOnShortestPath(startLocation, node);
                Double sum = 0.0;
                for (Double d : shortestTimes) {
                    sum += d;
                }

                if (sum <= travelTime) {
                    locations.add(node);
                }
            }
        }

        return locations;
    }
}