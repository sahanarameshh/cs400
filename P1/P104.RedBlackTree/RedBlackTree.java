public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {
    // Constructor
    public RedBlackTree() {
        super();
    }

    /**
     * Ensures the Red Property by fixing violations caused by inserting a red node.
     * @param newNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newNode) {
        // look for violation
        if (newNode.parent() != null && newNode.parent().isRed()) {
            if (newNode.parent().parent() != null) {
                // cases when parent is right child, sibling is left
                if (newNode.parent().isRightChild()) {
                    if (newNode.parent().parent().childLeft() == null ||
                        !newNode.parent().parent().childLeft().isRed()) {
                        // case 2 - black/null sibling line
                        if (newNode.isRightChild()) {
                            rotate(newNode.parent(), newNode.parent().parent());
                            newNode.parent().isRed = false;
                            newNode.parent().childLeft().isRed = true;
                        } 
                        // case 3 - black/null sibling zig
                        else {
                            rotate(newNode, newNode.parent());
                            ensureRedProperty(newNode);
                        }
                    } 
                    // case 1 - red sibling
                    else {
                        newNode.parent().isRed = false;
                        newNode.parent().parent().childLeft().isRed = false;
                        newNode.parent().parent().isRed = true;
                        ensureRedProperty(newNode.parent().parent());
                    }
                }
                // cases when parent is left child, sibling is right
                else {
                    if (newNode.parent().parent().childRight() == null ||
                        !newNode.parent().parent().childRight().isRed()) {
                        // case 2 - black/null sibling line
                        if (!newNode.isRightChild()) {
                            rotate(newNode.parent(), newNode.parent().parent());
                            newNode.parent().isRed = false;
                            newNode.parent().childRight().isRed = true;
                        } 
                        // case 3 - black/null sibling zig
                        else {
                            rotate(newNode, newNode.parent());
                            ensureRedProperty(newNode);
                        }
                    } 
                    // case 1 - red sibling
                    else {
                        newNode.parent().isRed = false;
                        newNode.parent().parent().childRight().isRed = false;
                        newNode.parent().parent().isRed = true;
                        ensureRedProperty(newNode.parent().parent());
                    }
                }
            } else {
                newNode.parent().isRed = false;
            }
        } else {
            return;
        }
    }

    /**
     * Inserts a new value into the Red-Black Tree and ensures balancing.
     */
    @Override
    public void insert(T data){
        RBTNode<T> newNode = new RBTNode<>(data);
        if (root == null) {
            root = newNode;
        } else {
            insertHelper(newNode, root);
            ensureRedProperty(newNode);
        }
        ((RBTNode<T>)this.root).isRed = false;
    }
}