import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class uses Djisktra's algorithm to find the quickets paths
 * through a graph that contains various locations on the UW-Madison
 * campus and the time it takes to travel between them.
 */
public class Backend implements BackendInterface {
    private GraphADT<String, Double> graph = new DijkstraGraph();
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
        // loop through the list
        for (String node : graph.getAllNodes()) {
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
        while ((line = br.readLine()) != null && !line.equals("}")) {            List<Object> lineElements = lineSplitHelper(line);
            // using a helper method, get the node, edge, and weight from the line
            String nodeData = (String) lineElements.get(0);
            String edgeData = (String) lineElements.get(1);
            Double weightData = (Double) lineElements.get(2);

            // insert data into graph
            graph.insertNode(nodeData);
            graph.insertNode(edgeData);
            graph.insertEdge(nodeData, edgeData, weightData);
        }
    }
    catch (IOException e) {
        // throw an exception if error
        throw new IOException("Error reading file.");
    }
  }

  /**
   * Assists with splitting up each line of the dot file to store 
   * the node, edge, and weight data, while checking for edge cases
   */
  private List<Object> lineSplitHelper(String line) {
    // create a list to store the node, edge, and weight data
    List<Object> lineElements = new ArrayList<>();
    
    try {
        // find the indices of the quoted strings
        int firstQuoteStart = line.indexOf("\"");
        int firstQuoteEnd = line.indexOf("\"", firstQuoteStart + 1);
        int secondQuoteStart = line.indexOf("\"", firstQuoteEnd + 1);
        int secondQuoteEnd = line.indexOf("\"", secondQuoteStart + 1);
        
        // check if all quotes were found
        if (firstQuoteStart == -1 || firstQuoteEnd == -1 || 
            secondQuoteStart == -1 || secondQuoteEnd == -1) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        
        // extract node and edge data from between quotes
        String nodeData = line.substring(firstQuoteStart + 1, firstQuoteEnd);
        String edgeData = line.substring(secondQuoteStart + 1, secondQuoteEnd);
        
        // extract weight data
        int weightStart = line.indexOf("=", secondQuoteEnd) + 1;
        int weightEnd = line.indexOf("]", weightStart);
        if (weightStart == 0 || weightEnd == -1) {
            throw new IllegalArgumentException("Weight not found in line: " + line);
        }
        
        String weightString = line.substring(weightStart, weightEnd).trim();
        Double weightData = Double.parseDouble(weightString);
        
        // add elements to list
        lineElements.add(nodeData);
        lineElements.add(edgeData);
        lineElements.add(weightData);
        
    } catch (Exception e) {
        // if any parsing errors occur, we can handle them appropriately
        System.err.println("Error parsing line: " + line);
        throw new IllegalArgumentException("Failed to parse line: " + e.getMessage());
    }
    
    return lineElements;
}

  /**
   * Returns a list of all locations (node data) available in the graph.
   * @return list of all location names
   */
  public List<String> getListOfAllLocations() {
    // create an empty list to hold the node data
    List<String> locations = new ArrayList<>();
    
    // loop through the list
    for (String node : graph.getAllNodes()) {
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
        if (!currentGraph.contains(startLocation) || !currentGraph.contains(endLocation)) {
            return shortestLocationPath;
        }
        
        // if start and end locations are the same, return a list with just that location
        if (startLocation.equals(endLocation)) {
            shortestLocationPath.add(startLocation);
            return shortestLocationPath;
        }

        // find the shortest path using the graph's shortestPathData method
        shortestLocationPath = graph.shortestPathData(startLocation, endLocation);
        
        // if shortestPathData returned null, create a new empty list
        if (shortestLocationPath == null) {
            shortestLocationPath = new ArrayList<>();
        }

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
        if (!currentGraph.contains(startLocation) || !currentGraph.contains(endLocation)) {
            return shortestTimes;
        }

        // create the list that will hold the locations
        List<String> shortestLocationPath = findLocationsOnShortestPath(startLocation, endLocation);
        // if path doesn't exist or has only one node, return empty list
        if (shortestLocationPath.isEmpty() || shortestLocationPath.size() < 2) {
            return shortestTimes;
        }
        
        // find the shortest time between each path using the placeholder shortestPathCost method
        for (int i = 0; i < shortestLocationPath.size() - 1; i++) {
            String fromNode = shortestLocationPath.get(i);
            String toNode = shortestLocationPath.get(i + 1);
            Double edgeWeight = graph.shortestPathCost(fromNode, toNode);
            shortestTimes.add(edgeWeight);
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

        // create a list to hold the list of reachable locations
        List<String> reachableLocations = new ArrayList<>();

        // loop through all nodes in the graph
        for (String node : currentGraph) {
            // skip the start location
            if (!node.equals(startLocation)) {
                // get the shortest path
                List<String> path = findLocationsOnShortestPath(startLocation, node);
                
                // check if a valid path of at least 2 nodes exists
                if (path.size() >= 2) {
                    // get the times along the path
                    List<Double> times = findTimesOnShortestPath(startLocation, node);
                    
                    // calculate total time
                    double totalTime = 0.0;
                    for (Double time : times) {
                        totalTime += time;
                    }
                    
                    // add to reachable locations IF within time limit
                    if (totalTime <= travelTime) {
                        reachableLocations.add(node);
                    }
                }
            }
        }
        
        return reachableLocations;
    }
}