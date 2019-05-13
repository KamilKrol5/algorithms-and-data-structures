package structures;

import java.io.File;

public class BSTree<T extends Comparable<T>> extends AbstractTree<T> {

    private class BSTNode implements Node<T> {
        T key;
        BSTNode left = null;
        BSTNode right = null;

        BSTNode(T key) {
            this.key = key;
        }

        @Override
        public T getKey() {
            return key;
        }

        @Override
        public BSTNode getLeft() {
            return left;
        }

        @Override
        public BSTNode getRight() {
            return right;
        }

        @Override
        public String toString()  {
            return key.toString();
        }
    }

    private BSTNode root = null;

    @Override
    protected BSTNode getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    protected boolean insertImpl(T element) {
        if (root == null) {
            root = new BSTNode(element);
            return true;
        }
        BSTNode currentNode = root;
        while (currentNode != null) {
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                return false;
            }
            if (compareResult < 0) {
                if (currentNode.left == null) {
                    currentNode.left = new BSTNode(element);
                    return true;
                }
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.right == null) {
                    currentNode.right = new BSTNode(element);
                    return true;
                }
                currentNode = currentNode.getRight();
            }
        }
        throw new IllegalStateException("Insert called with no existent value but the value has not been inserted. Value = " + element);
    }

    @Override
    protected boolean deleteImpl(T element) {
        BSTNode helpNode = new BSTNode(null);
        BSTNode previous = helpNode;
        previous.left = root;
        boolean leftPath = true;
        var currentNode = root;
        while (currentNode != null) {
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                if (currentNode.right == null && currentNode.left == null) {
                    if (leftPath) {
                        previous.left = null;
                    } else {
                        previous.right = null;
                    }
                } else if (currentNode.left == null) { // && currentNOde.right != null
                    if (leftPath) {
                        previous.left = currentNode.right;
                    } else {
                        previous.right = currentNode.right;
                    }
                } else if ( currentNode.right == null) { // && currentNode.left != null
                    if (leftPath) {
                        previous.left = currentNode.left;
                    } else {
                        previous.right = currentNode.left;
                    }
                } else {
                    var minParent = findMinKeyParent(currentNode.right);
                    if (minParent == null) {
                        swapKeys(currentNode, currentNode.right);
                        currentNode.right = currentNode.right.right;
                    } else {
                        swapKeys(minParent.left, currentNode);
                        minParent.left = minParent.left.right;
                    }

                }
                if (previous == helpNode) {
                    root = previous.left;
                }
                return true;
            }
            if (compareResult < 0) {
                leftPath = true;
                previous = currentNode;
                 currentNode = currentNode.left;
            } else {
                leftPath = false;
                previous = currentNode;
                 currentNode = currentNode.right;
            }
        }
        return false;
    }

    @Override
    protected boolean searchImpl(T element) {
        var currentNode = root;
        while (currentNode != null) {
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                return true;
            }
            if (compareResult < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    private void swapKeys(BSTNode node1, BSTNode node2) {
        T tmp = node1.key;
        node1.key = node2.key;
        node2.key = tmp;
    }

    private BSTNode findMinKeyParent(BSTNode startNode) {
        if (startNode.left == null) {
            return null;
        } else if (startNode.left.left == null){
            return startNode;
        } else {
            return findMinKeyParent(startNode.left);
        }


    }


    @Override
    protected void loadImpl(File file) {

    }


}
