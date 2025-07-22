// === CS400 File Header Information ===
// Name: Sahana Ramesh
// Email: ramesh37@wisc.edu
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new HashtableMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // throw an exception if the given start or end node doesn't exist
        if (!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("Either start or end data do not correspond to a graph node.");
        }
    
        // create a priority queue and a visited set
        PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();
        MapADT<Node, Integer> visited = new HashtableMap<>();
    
        // add the starting node to the priority queue
        priorityQueue.add(new SearchNode(nodes.get(start), 0, null));

        // loop until the priority queue is empty
        while (!priorityQueue.isEmpty()) {
            // store the highest priority node
            SearchNode current = priorityQueue.poll();        

            // return the node if it is connected to the end node
            if (current.node.data.equals(nodes.get(end).data)) {
                return current;
            }

            // make sure the node is already visited
            if (!visited.containsKey(current.node)) {
                // put it into the visited set
                visited.put(current.node, 0);

                // loop through the nodes it points to
                for (Edge e : current.node.edgesLeaving) {
                    // add it to the priority queue
                    SearchNode neighbor = new SearchNode(e.successor, e.data.doubleValue() + current.cost, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        // throw an exception if no path is found
        throw new NoSuchElementException("No path from start to end is found");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // create a list to hold the nodes along this shortest path
        List<NodeType> nodeList = new ArrayList<>();
        
        // make sure the starting and ending node aren't the same
        if (start.equals(end)) {
            nodeList.add(start);
            return nodeList;
        }
        
        try {
            // find the shortest path
            SearchNode searchNode = computeShortestPath(start, end);
            
            // add those nodes to the list
            while (searchNode != null) {
                nodeList.add(searchNode.node.data);
                searchNode = searchNode.predecessor;
            }
            
            // reverse and return the list
            Collections.reverse(nodeList);
            return nodeList;
        } catch (NoSuchElementException e) {
            // return an empty list if no path exists
            return nodeList;
        }
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path from the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // get the cost of the shortest path through the searchNode returned by the helper method
        SearchNode searchNode = computeShortestPath(start, end);
        return searchNode.cost;
    }


    /*
    private DijkstraGraph<String, Double> graph;
    
    public void setUp() {
        graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");

        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("B", "C", 2.0);
        graph.insertEdge("C", "D", 6.0);
        graph.insertEdge("B", "D", 5.0);
    }

    @Test
    public void testShortestPath() {
        setUp();
        List<String> path = graph.shortestPathData("A", "C");
        double cost = graph.shortestPathCost("A", "C");

        List<String> expected = new ArrayList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, path);
        assertEquals(3.0, cost);
    }

    @Test
    public void testMultipleEquallyShortestPaths() {
        setUp();
        List<String> path1 = graph.shortestPathData("A", "D");
        List<String> path2 = graph.shortestPathData("C", "D");
        double cost1 = graph.shortestPathCost("A", "D");
        double cost2 = graph.shortestPathCost("C", "D");

        List<String> expected1 = new ArrayList<>();
        expected1.add("A");
        expected1.add("B");
        expected1.add("D");

        List<String> expected2 = new ArrayList<>();
        expected2.add("C");
        expected2.add("D");

        assertEquals(expected1, path1);
        assertEquals(expected2, path2);
        assertEquals(6.0, cost1);
        assertEquals(6.0, cost2);
    }

    @Test
    public void testNoPath() {
        setUp();
        graph.insertNode("E");

        try {
            graph.shortestPathData("A", "E");
            assertTrue(false);
        }
        catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }
    */
}