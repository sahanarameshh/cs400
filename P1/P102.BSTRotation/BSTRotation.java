public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {
    public BSTRotation() {
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child is the node being rotated from child to parent position 
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     *     nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent)
        throws NullPointerException, IllegalArgumentException {
            if (child == null || parent == null) { throw new NullPointerException(); }

            if (parent.childLeft() != child && parent.childRight() != child) { throw new IllegalArgumentException(); }

            if (parent.up != null) {
                child.setParent(parent.up);
                if (parent.up.childLeft() == parent) {
                    parent.up.setChildLeft(child);
                } else {
                    parent.up.setChildRight(child);
                }
            } else {
                child.setParent(null);
                root = child;
            }

            if (parent.childRight() == child) {
                // left rotation
                if (child.childLeft() != null) {
                    parent.setChildRight(child.childLeft());
                    child.childLeft().setParent(parent);
                }
                child.setChildLeft(parent);
                parent.setParent(child);
            } else if (parent.childLeft() == child) {
                // right rotation
                if (child.childRight() != null) {
                    parent.setChildLeft(child.childRight());
                    child.childRight().setParent(parent);
                }
                child.setChildRight(parent);
                parent.setParent(child);
            }
    }

    public static void main(String[] args) {
        // test left rotation, zero children, with root
        BSTRotation bstOne = new BSTRotation<Integer>();
        bstOne.insert(20);
        bstOne.insert(40);
        if (bstOne.testZero()) { System.out.println("Passed Test 1"); }

        // test right rotation, one child, with root
        BSTRotation bstTwo = new BSTRotation<Integer>();
        bstTwo.insert(60);
        bstTwo.insert(40);
        bstTwo.insert(20);
        if (bstTwo.testOne()) { System.out.println("Passed Test 2"); }

        // test left rotation, two children, without root
        BSTRotation bstThree = new BSTRotation<Integer>();
        bstThree.insert(10);
        bstThree.insert(20);
        bstThree.insert(40);
        bstThree.insert(30);
        bstThree.insert(50);
        if (bstThree.testTwo()) { System.out.println("Passed Test 3"); }

        // test right rotation, three children, without root
        BSTRotation bstFour = new BSTRotation<Integer>();
        bstFour.insert(60);
        bstFour.insert(40);
        bstFour.insert(20);
        bstFour.insert(50);
        bstFour.insert(10);
        bstFour.insert(30);
        if (bstFour.testThree()) { System.out.println("Passed Test 4"); }
    }

    public boolean testZero() {
        rotate(root.childRight(), root);
        return (root.getData() == (Integer) 40) 
                && (root.childLeft().getData() == (Integer) 20);
    }
    
    public boolean testOne() {
        rotate(root.childLeft(), root);
        return (root.getData() == (Integer) 40) 
                && (root.childLeft().getData() == (Integer) 20)
                && (root.childRight().getData() == (Integer) 60);
    }

    public boolean testTwo() {
        rotate(root.childRight().childRight(), root.childRight());
        return (root.getData() == (Integer) 10)
            && (root.childRight().getData() == (Integer) 40)
            && (root.childRight().childLeft().getData() == (Integer) 20)
            && (root.childRight().childRight().getData() == (Integer) 50)
            && (root.childRight().childLeft().childRight().getData() == (Integer) 30);
    }

    public boolean testThree() {
        rotate(root.childLeft().childLeft(), root.childLeft());
        return (root.getData() == (Integer) 60)
                && (root.childLeft().getData() == (Integer) 20)
                && (root.childLeft().childLeft().getData() == (Integer) 10)
                && (root.childLeft().childRight().getData() == (Integer) 40)
                && (root.childLeft().childRight().childLeft().getData() == (Integer) 30)
                && (root.childLeft().childRight().childRight().getData() == (Integer) 50);
    }
}