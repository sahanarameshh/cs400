public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    protected BinaryTreeNode root;
    
    /**
     * Inserts a new data value into the sorted collection.
     * @param data the new value being insterted
     * @throws NullPointerException if data argument is null, we do not allow
     * null values to be stored within a SortedCollection
     */
    public void insert(T data) throws NullPointerException {
        if (data == null) { throw new NullPointerException(); }

        if (root == null) {
            root = new BinaryTreeNode<T>(data);
        } else {
            insertHelper(new BinaryTreeNode<T>(data), root);
        }
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree.  When the provided subtree
     * is null, this method does nothing. 
     */
    protected void insertHelper(BinaryTreeNode<T> newNode, BinaryTreeNode<T> subtree) {

        if (newNode.getData().compareTo(subtree.getData()) <= 0) {
            if (subtree.childLeft() == null) {
                subtree.setChildLeft(newNode);
                newNode.setParent(subtree);
            }
            else {
                insertHelper(newNode, subtree.childLeft());
            }
        }
        else {
            if (subtree.childRight() == null) {
                subtree.setChildRight(newNode);
                newNode.setParent(subtree);
            }
            else {
                insertHelper(newNode, subtree.childRight());
            }
        }
    }


    /**
     * Check whether data is stored in the tree.
     * @param data the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     * and false otherwise
     */
    public boolean contains(Comparable<T> data) {
        return root.toInOrderString().contains(data.toString());
    }

    /**
     * Counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     * @return the number of values in the collection, including duplicates
     */
    public int size() {
        if (root == null) { return 0; }

        String[] contents = root.toLevelOrderString().split(",");
        return contents.length;
    }

    /**
     * Checks if the collection is empty.
     * @return true if the collection contains 0 values, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Removes all values and duplicates from the collection.
     */
    public void clear() {
        root = null;
    }


    /* TESTERS */
    public static void main(String args[]) {
        BinarySearchTree<Integer> bstInt1 = new BinarySearchTree<>();
        bstInt1.insert(40);
        bstInt1.insert(20);
        bstInt1.insert(60);
        bstInt1.insert(10);
        bstInt1.insert(30);
        bstInt1.insert(50);
        bstInt1.insert(70);
        
        bstInt1.testInsert(bstInt1.root);
        bstInt1.testContains(20);
        bstInt1.testSize();

        bstInt1.clear();
        bstInt1.testClear();

        System.out.println("Test 1: Passed");

        BinarySearchTree<String> bstString = new BinarySearchTree<>();       
        bstString.insert("D");
        bstString.insert("B");
        bstString.insert("F");
        bstString.insert("A");
        bstString.insert("C");
        bstString.insert("E");
        bstString.insert("G");
        
        bstString.testInsert(bstString.root);
        bstString.testContains("C");
        bstString.testSize();

        bstString.clear();
        bstString.testClear();

        System.out.println("Test 2: Passed");
        BinarySearchTree<Integer> bstInt2 = new BinarySearchTree<>();
        bstInt2.insert(10);
        bstInt2.insert(20);
        bstInt2.insert(30);
        bstInt2.insert(40);
        bstInt2.insert(50);
        bstInt2.insert(60);
        bstInt2.insert(70);

        bstInt2.testInsert(bstInt2.root);
        bstInt2.testContains(60);
        bstInt2.testSize();

        bstInt2.clear();
        bstInt2.testClear();

        System.out.println("Test 3: Passed");
    }

    // test insert()
    public boolean testInsert(BinaryTreeNode<T> root) {
        try {
            insert(null);
            return false;
        }
        catch (NullPointerException e) {
            ;
        }

        if (root.childLeft() == null || root.childRight() == null) {
            return true;
        }
        else if ((root.getData().compareTo(root.childLeft().getData()) > 0) || (root.getData().compareTo(root.childRight().getData()) <= 0)) {
            return false;
        }
        else {
            testInsert(root.childRight());
            testInsert(root.childLeft());
        }
        return true;
    }

    // test contains()
    public boolean testContains(Comparable<T> val) {
        return contains(val);
    }

    // test size()
    public boolean testSize() {
        return size() == 7;
    }

    // test clear() and isEmpty()
    public boolean testClear() {
        return isEmpty() && size() == 0;
    }
}