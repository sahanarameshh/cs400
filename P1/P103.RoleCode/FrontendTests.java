import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class FrontendTests {
    public FrontendTests() {
    }

    // testing the readData method from the backendInterface
    @Test
    public void frontendTest1() {
        // make instances of the tester, tree placeholder, backend placeholder, and the frontend object we made
        TextUITester tester = new TextUITester("load songs.csv\nquit\n", false);        
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
	    BackendInterface backend = new Backend_Placeholder(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);

        // test load FILEPATH
        frontend.runCommandLoop();
        System.out.println(tree.size() == 4);

        String output = tester.checkOutput();
        Assertions.assertEquals(output, "true\n");
    }

    // test year MAX
    @Test
    public void frontendTest2() {
        // make instances of the tester, tree placeholder, backend placeholder, and the frontend object we made
        TextUITester tester = new TextUITester("load songs.csv\nyear 2016\nquit\n", false);        
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
	    BackendInterface backend = new Backend_Placeholder(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);

        frontend.runCommandLoop();
        List<String> expected = new ArrayList<>();
        expected.add("BO$$");
        expected.add("Cake By The Ocean");
        
        String output = tester.checkOutput();
        System.out.println(output.getClass());
        System.out.println(expected.getClass());
        Assertions.assertEquals(output, "[BO$$, Cake By The Ocean, DJ Got Us Fallin' In Love (feat. Pitbull)]\n");
    }

    // test loudness MAX
    @Test
    public void frontendTest3() {
        TextUITester tester = new TextUITester("load songs.csv\nloudness -5\nquit\n", false);        
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
	    BackendInterface backend = new Backend_Placeholder(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);
        
        frontend.runCommandLoop();

        String output = tester.checkOutput();
        Assertions.assertEquals(output, "[A L I E N S, BO$$, Cake By The Ocean, DJ Got Us Fallin' In Love (feat. Pitbull)]\n");
    }

    // test year MIN to MAX
    @Test
    public void frontendIntegrationTest1() {
        TextUITester tester = new TextUITester("load tenSongs.csv\nyear 2018 to 2019\nquit\n", false);        
        IterableSortedCollection<Song> tree = new IterableRedBlackTree();
	    BackendInterface backend = new Backend(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);
        
        frontend.runCommandLoop();

        String output = tester.checkOutput();
        Assertions.assertEquals(output, "[Bad At Love, Truth Hurts, Someone You Loved]\n");
    }

    // test loudness MAX
    @Test
    public void frontendIntegrationTest2() {
        TextUITester tester = new TextUITester("load tenSongs.csv\nloudness 8\nquit\n", false);        
        IterableSortedCollection<Song> tree = new IterableRedBlackTree();
	    BackendInterface backend = new Backend(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);
        
        frontend.runCommandLoop();

        String output = tester.checkOutput();
        Assertions.assertEquals(output, "[Jar of Hearts, Paradise, Birthday, The Hills, Here, Water Under the Bridge, Bad At Love, Truth Hurts, Someone You Loved]\n");
    }
    
    // test show most danceable
    @Test
    public void frontendIntegrationTest3() {
        TextUITester tester = new TextUITester("load tenSongs.csv\nshow most danceable\nquit\n", false);        
        IterableSortedCollection<Song> tree = new IterableRedBlackTree();
	    BackendInterface backend = new Backend(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);
        
        frontend.runCommandLoop();

        String output = tester.checkOutput();
        Assertions.assertEquals(output, "[Jar of Hearts, Here, Paradise, Someone You Loved, The Hills]\n");
    }

    // test year 2016
    @Test
    public void frontendIntegrationTest4() {
        TextUITester tester = new TextUITester("load tenSongs.csv\nyear 2016\nquit\n", false);        
        IterableSortedCollection<Song> tree = new IterableRedBlackTree();
	    BackendInterface backend = new Backend(tree);
        Scanner in = new Scanner(System.in);
        FrontendInterface frontend = new Frontend(in,backend);
        
        frontend.runCommandLoop();

        String output = tester.checkOutput();
        Assertions.assertEquals(output, "[Jar of Hearts, Paradise, Birthday, The Hills, Here]\n");
    }
}