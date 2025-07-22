import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class TeamTests {
    /** testing generateShortestPathPromptHTML 
     * and generateReachableFromWithinPromptHTML
     */
    @Test
    public void testHTMLPrompts() {
        // make instances of the graph placeholder, backend, and frontend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        FrontendInterface frontend = new Frontend(backend);

        // test generateShortestPathPromptHTML
        String shortestPathPrompt = frontend.generateShortestPathPromptHTML();
        // make sure the HTML line contains the right content
        Assertions.assertTrue(shortestPathPrompt.contains("id"), "HTML string must contain 'id'.");
        Assertions.assertTrue(shortestPathPrompt.contains("start"), "HTML string must contain 'start'.");
        Assertions.assertTrue(shortestPathPrompt.contains("end"), "HTML string must contain 'end'.");
        Assertions.assertTrue(shortestPathPrompt.contains("button"), "HTML string must contain 'button'.");
        Assertions.assertTrue(shortestPathPrompt.contains("Find Shortest Path"), "HTML string must contain 'Find Shortest Path'.");

        // test generateReachableFromWithinPromptHTML
        String reachableFromWithinPrompt = frontend.generateReachableFromWithinPromptHTML();
        // make sure the HTML line contains the right content
        Assertions.assertTrue(reachableFromWithinPrompt.contains("id"), "HTML string must contain 'id'.");
        Assertions.assertTrue(reachableFromWithinPrompt.contains("from"), "HTML string must contain 'from'.");
        Assertions.assertTrue(reachableFromWithinPrompt.contains("time"), "HTML string must contain 'time'.");
        Assertions.assertTrue(reachableFromWithinPrompt.contains("button"), "HTML string must contain 'button'.");
        Assertions.assertTrue(reachableFromWithinPrompt.contains("Reachable From Within"), "HTML String must contain 'Reachable From Within'.");
    }

    /** testing generateShortestPathResponseHTML
     */
    @Test
    public void testShortestPathGeneration() {
        // make instances of the graph placeholder, backend, and frontend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        FrontendInterface frontend = new Frontend(backend);

        //test generateShortestPathResponseHTML
        String shortestPathResponse = frontend.generateShortestPathResponseHTML("Union South", "Weeks Hall for Geological Sciences");
        // assert that the correct locations and time are including in the HTML String
        Assertions.assertTrue(shortestPathResponse.contains("Union South"), "HTML string should contain 'Union South'.");
        Assertions.assertTrue(shortestPathResponse.contains("Computer Sciences and Statistics"), "HTML string should contain 'Computer Sciences and Statistics'.");
        Assertions.assertTrue(shortestPathResponse.contains("Weeks Hall for Geological Sciences"), "HTML string should contain 'Weeks Hall for Geological Sciences'.");
        Assertions.assertTrue(shortestPathResponse.contains("3.0"), "Total time should be 3.0 seconds.");
    }

    /** testing generateReachableFromWithinResponseHTML
     */
    @Test
    public void testReachableFromWithinGeneration() {
        // make instances of the graph placeholder, backend, and frontend
        GraphADT<String, Double> graph = new Graph_Placeholder();
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        FrontendInterface frontend = new Frontend(backend);

        //test generateReachableFromWithinResponseHTML
        String reachableFromWithinResponse = frontend.generateReachableFromWithinResponseHTML("Union South", 3.0);
        // assert that the correct locations are including in the HTML String
        Assertions.assertTrue(reachableFromWithinResponse.contains("Union South"), "HTML string should contain 'Union South'.");
        Assertions.assertTrue(reachableFromWithinResponse.contains("Computer Sciences and Statistics"), "HTML string should contain 'Computer Sciences and Statistics'.");
        Assertions.assertTrue(reachableFromWithinResponse.contains("Weeks Hall for Geological Sciences"), "HTML string should contain 'Weeks Hall for Geological Sciences'.");
    }
}