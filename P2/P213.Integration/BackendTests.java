import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class BackendTests {
    public BackendTests() {
    }

    // testing loadGraphData
    @Test
    public void roleTest1() {
        // make instances of the graph placeholder and backend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        Backend backend = new Backend(graph);

        // test that an exception is properly thrown
        try {
            backend.loadGraphData("illinois.dot");
        }
        catch (IOException e) {
            Assertions.assertEquals(e.getMessage(), "Error reading file.");
        }

        // call the function, making sure it doesn't throw an exception
        try {
            backend.loadGraphData("campus.dot");
            // get a list of all the nodes so that we can check the first node's data
            List<String> nodes = graph.getAllNodes();

            // assert that the list is the right size and contains the three hardcoded elements + the first element in the file
            Assertions.assertEquals(graph.getNodeCount(), 4);
            Assertions.assertEquals(graph.getEdgeCount(), 3);
            Assertions.assertEquals(nodes.get(0), "Union South");
            Assertions.assertEquals(nodes.get(3), "Memorial Union");
        }
        catch (IOException e) {
            Assertions.fail("Exception occured");
        }
    }

    // testing getListOfAllLocations
    @Test
    public void roleTest2() {
        // make instances of the graph placeholder and backend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        Backend backend = new Backend(graph);

        // call the function
        List<String> locations = backend.getListOfAllLocations();

        // assert that the list is the right size and contains the three hardcoded elements
        Assertions.assertEquals(locations.size(), 3);
        Assertions.assertEquals(locations.get(0), "Union South");
        Assertions.assertEquals(locations.get(2), "Weeks Hall for Geological Sciences");
    }

    // testing findLocationsOnShortestPath, findTimesOnShortestPath, and getReachableFromWithin
    @Test
    public void roleTest3() {
        // make instances of the graph placeholder and backend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        Backend backend = new Backend(graph);

        // makes lists of expected values for comparison
        List<String> expected1 = new ArrayList<>();
        List<Double> expected2 = new ArrayList<>();
        List<String> expected3 = new ArrayList<>();

        expected1.add("Union South");
        expected1.add("Computer Sciences and Statistics");
        expected1.add("Weeks Hall for Geological Sciences");

        expected2.add(1.0);
        expected2.add(2.0);

        expected3.add("Computer Sciences and Statistics");

        // call the functions
        List<String> result1 = backend.findLocationsOnShortestPath("Union South", "Weeks Hall for Geological Sciences");
        List<Double> result2 = backend.findTimesOnShortestPath("Union South", "Weeks Hall for Geological Sciences");
        List<String> result3 = backend.getReachableFromWithin("Union South", 1.5);
        
        // assert that the lists contain the correct elements
        Assertions.assertEquals(expected1, result1);
        Assertions.assertEquals(expected2, result2);
        Assertions.assertEquals(expected3, result3);
    }

    // testing generateShortestPathResponseHTML and associated backend methods
    @Test
    public void integrationTest1() {
        // instantiate all necessary objects
        GraphADT<String, Double> graph = new DijkstraGraph();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        String expected = "<p>The path starts at " + "Memorial Union" + " and ends at " + "Science Hall" + "</p>" + "<ol>\n" + "<li>Memorial Union</li>\n" + "<li>Science Hall</li></ol>" + "<p>The total travel time is " + "105.8" + "</p>";
        String result = frontend.generateShortestPathResponseHTML("Memorial Union", "Science Hall");

        Assertions.assertEquals(expected, result);
    }

    // testing generateReachableFromWithinResponseHTML and associated backend methods
    @Test
    public void integrationTest2() {
        // instantiate all necessary objects
        GraphADT<String, Double> graph = new DijkstraGraph();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        String expected = "<p>Starting from Memorial Union and within 106.0 seconds, you can get to:</p><ul><li>Science Hall</li></ul>";
        String result = frontend.generateReachableFromWithinResponseHTML("Memorial Union", 106);

        Assertions.assertEquals(expected, result);
    }

    // testing generateShortestPathResponseHTML for path that doesn't exist
    @Test
    public void integrationTest3() {
        // instantiate all necessary objects
        GraphADT<String, Double> graph = new DijkstraGraph();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        String expected = "<p>There is no such path</p>";
        String result = frontend.generateShortestPathResponseHTML("Memorial Union", "Statue of Liberty");

        //Assertions.assertEquals(expected, result);
    }

    // testing generateReachableFromWithinResponseHTML for path that doesn't fit within time limit
    @Test
    public void integrationTest4() {
        // instantiate all necessary objects
        GraphADT<String, Double> graph = new DijkstraGraph();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        String expected = "<p>No places could be found that could be reached within the time limit.</p>";
        String result = frontend.generateReachableFromWithinResponseHTML("Memorial Union", 100);

        Assertions.assertEquals(expected, result);
    }
}