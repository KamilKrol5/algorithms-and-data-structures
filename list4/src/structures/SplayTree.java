package structures;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class SplayTree<T extends Comparable<T>> extends AbstractTree<T> {

    private class SplayNode implements AbstractTree.Node<T> {
        T key;
        SplayNode left = null;
        SplayNode right = null;
        SplayNode parent = null;

        SplayNode(T key) {
            this.key = key;
        }

        SplayNode(T key, SplayNode parent) {
            this.key = key;
            this.parent = parent;
        }

        @Override
        public T getKey() {
            return key;
        }

        @Override
        public SplayNode getLeft() {
            return left;
        }

        @Override
        public SplayNode getRight() {
            return right;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    private SplayNode root = null;

    @Override
    protected SplayNode getRoot() {
        return root;
    }

    @Override
    protected boolean insertImpl(T element) {
        if (root == null) {
            root = new SplayNode(element);
            return true;
        }
        SplayNode currentNode = root;
        while (currentNode != null) {
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                splay(currentNode);
                return false;
            }
            if (compareResult < 0) {
                if (currentNode.left == null) {
                    currentNode.left = new SplayNode(element, currentNode);
                    splay(currentNode);
                    return true;
                }
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.right == null) {
                    currentNode.right = new SplayNode(element, currentNode);
                    splay(currentNode);
                    return true;
                }
                currentNode = currentNode.getRight();
            }
        }
        throw new IllegalStateException("Insert called with no existent value but the value has not been inserted. Value = " + element);
    }


    @Override
    protected boolean deleteImpl(T element) {
        SplayNode helpNode = new SplayNode(null);
        SplayNode previous = helpNode;
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
                    currentNode.right.parent = previous;
                } else if ( currentNode.right == null) { // && currentNode.left != null
                    if (leftPath) {
                        previous.left = currentNode.left;
                    } else {
                        previous.right = currentNode.left;
                    }
                    currentNode.left.parent = previous;
                } else {
                    var minParent = findMinKeyParent(currentNode.right);
                    if (minParent == null) {
                        swapKeys(currentNode, currentNode.right);
                        currentNode.right = currentNode.right.right;
                        if (currentNode.right.right != null) {
                            currentNode.right.right = currentNode;
                        }
                    } else {
                        swapKeys(minParent.left, currentNode);
                        minParent.left = minParent.left.right;
                        if (minParent.left.right != null) {
                            minParent.left.right.parent = minParent;
                        }
                    }
                }
                if (previous == helpNode) {
                    root = previous.left;
                }
                splay((previous == helpNode) ? null : previous);
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
                splay(currentNode);
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

    @Override
    protected void loadImpl(File file, Function<String, T> fromString, String delimiterPattern) throws IOException {
//        TODO: implement
    }

    private void swapKeys(SplayNode node1, SplayNode node2) {
        T tmp = node1.key;
        node1.key = node2.key;
        node2.key = tmp;
    }

    private SplayNode findMinKeyParent(SplayNode startNode) {
        if (startNode.left == null) {
            return null;
        } else if (startNode.left.left == null){
            return startNode;
        } else {
            return findMinKeyParent(startNode.left);
        }
    }

    private void splay(SplayNode node) {
//        TODO implement
    }

}
