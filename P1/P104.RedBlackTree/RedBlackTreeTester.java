import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
* This class extends the RedBlackTree class to run submission check on it.
*/
public class RedBlackTreeTester extends RedBlackTree<Integer> {

    /**
    * @return true is check passes, false if it fails
    */
    @Test
    public void firstTest() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("A");
        tree.insert("B");
        tree.insert("C");
        tree.insert("D");
        tree.insert("E");
        tree.insert("F");
        Assertions.assertTrue(tree.root.toLevelOrderString().equals("[ B(b), A(b), D(r), C(b), E(b), F(r) ]"));
    }

    /**
    * @return true is check passes, false if it fails
    */
    @Test
    public void secondTest() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("N");
        tree.insert("G");
        tree.insert("T");
        tree.insert("E");
        tree.insert("J");
        tree.insert("R");
        tree.insert("W");
        tree.insert("H");
        tree.insert("K");
        Assertions.assertTrue(tree.root.toLevelOrderString().equals("[ N(b), G(r), T(b), E(b), J(b), R(r), W(r), H(r), K(r) ]"));
    }

    /**
    * @return true is check passes, false if it fails
    */
    @Test
    public void thirdTest() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("O");
        tree.insert("I");
        tree.insert("W");
        tree.insert("F");
        tree.insert("K");
        tree.insert("S");
        tree.insert("Y");
        tree.insert("J");
        tree.insert("L");
        Assertions.assertTrue(tree.root.toLevelOrderString().equals("[ O(b), I(r), W(b), F(b), K(b), S(r), Y(r), J(r), L(r) ]"));
    }
}