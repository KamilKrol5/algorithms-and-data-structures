package structures;

import java.util.LinkedList;
import java.util.Queue;

class RedBlackTreePropertyViolatedException extends IllegalStateException {
    RedBlackTreePropertyViolatedException(String m) {super(m);}
}

public class RedBlackTree<T extends Comparable<T>> extends AbstractTree<T> {

    private enum Color {
        RED("R"), BLACK("B");
        private String text;

        Color(String s) {
            text = s;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private class RBNode implements AbstractTree.Node<T> {
        T key = null;
        RBNode left;
        RBNode right;
        RBNode parent;
        Color color = Color.RED;

        RBNode(T key, Color color) {
            this.key = key;
            this.color = color;
            this.parent = nil;
            this.left = nil;
            this.right = nil;
        }

        RBNode(T key, Color color, RBNode parent) {
            this.key = key;
            this.parent = parent;
            this.color = color;
            this.left = nil;
            this.right = nil;
        }

        RBNode(T key, Color color, RBNode parent, RBNode left, RBNode right) {
            this.key = key;
            this.parent = parent;
            this.color = color;
            this.left = left;
            this.right = right;
        }

        @Override
        public T getKey() {
            return key;
        }

        @Override
        public RBNode getLeft() {
            return left;
        }

        @Override
        public RBNode getRight() {
            return right;
        }

        @Override
        public String toString() {
            return (key == null) ? "nil" : key.toString() + ":" + color;
        }

        @Override
        public void setKey(T t) {
            this.key = t;
        }
    }

    private RBNode nil = new RBNode(null, Color.BLACK);
    private RBNode root = nil;

    public RedBlackTree() {
        nil.parent = nil;
        nil.right = nil;
        nil.left = nil;
    }

    @Override
    protected Node<T> getRoot() {
        return root;
    }

    @Override
    protected boolean insertImpl(T element) {
        if (root == nil) {
            root = new RBNode(element, Color.BLACK);
            return true;
        }
        RBNode currentNode = root;
        while (currentNode != nil) {
            assert currentNode != null;
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                return false;
            }
            if (compareResult < 0) {
                if (currentNode.left == nil) {
                    currentNode.left = new RBNode(element, Color.RED, currentNode);
                    insertFixup(currentNode.left);
                    return true;
                }
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.right == nil) {
                    currentNode.right = new RBNode(element, Color.RED, currentNode);
                    insertFixup(currentNode.right);
                    return true;
                }
                currentNode = currentNode.getRight();
            }
        }
        throw new IllegalStateException("Insert called with no existent value but the value has not been inserted. Value = " + element);
    }

    @Override
    protected boolean deleteImpl(T element) {
        RBNode z = find(element);
        if (z == null) return false;

        RBNode x, y;
        if (z.left == nil || z.right == nil) {
            y = z;
        } else {
            y = findMinKeyNode(z); //successor
        }

        if (y.left != nil) {
            x = y.left;
        } else {
           x = y.right;
        }
        x.parent = y.parent;
        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        if (y != z) {
            z.key = y.key;
        }
        if (y.color == Color.BLACK) {
            deleteFixup(x);
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected boolean searchImpl(T element) {
        var findResult = find(element);
        if (findResult == null) return false;
        else if (!findResult.key.equals(element)) throw new IllegalStateException("Find has found wrong value.");
        else return true;
    }

    private RBNode find(T element) {
        var currentNode = root;
        while (currentNode != nil) {
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                return currentNode;
            }
            if (compareResult < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return null;
    }

    private void leftRotate(RBNode node) {
        RBNode r = node.right;
        node.right = r.left;
        if (r.left != nil) {
            r.left.parent = node;
        }
        r.parent = node.parent;
        if (node.parent == nil) {
            root = r;
        } else if (node == node.parent.left) {
            node.parent.left = r;
        } else {
            node.parent.right = r;
        }
        r.left = node;
        node.parent = r;
    }

    private void rightRotate(RBNode node) {
        RBNode l = node.left;
        node.left = l.right;
        if (l.right != nil) {
            l.right.parent = node;
        }
        l.parent = node.parent;
        if (node.parent == nil) {
            root = l;
        } else if (node == node.parent.left) {
            node.parent.left = l;
        } else {
            node.parent.right = l;
        }
        l.right = node;
        node.parent = l;
    }

    private void insertFixup(RBNode node) {
        node.color = Color.RED;
        while (node != root && node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.left) {
                var y = node.parent.parent.right;
                if (y.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else if (node == node.parent.right) {
                    node = node.parent;
                    leftRotate(node);
                } else {
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                var y = node.parent.parent.left;
                if (y.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else if (node == node.parent.left) {
                    node = node.parent;
                    rightRotate(node);
                } else {
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    private RBNode findMinKeyNode(RBNode startNode) {
        if (startNode.left == nil) {
            return startNode;
        } else {
            return findMinKeyNode(startNode.left);
        }
    }

    private void deleteFixup(RBNode node) {
        var x = node;
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                var w = x.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else if (w.right.color == Color.BLACK) {
                    w.left.color = Color.BLACK;
                    w.color = Color.RED;
                    rightRotate(w);
                    w = x.parent.right;
                } else {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                var w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else if (w.left.color == Color.BLACK) {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    leftRotate(w);
                    w = x.parent.left;
                } else {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    @SuppressWarnings("Duplicates")
    private boolean checkParents(RBNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Given node is null");
        }
        var cond1 = true;
        var cond2 = true;
        if (node.left != nil) {
            cond1 = node.left.parent == node;
        }
        if (node.right != nil) {
            cond2 = node.right.parent == node;
        }
        return cond1 && cond2;
    }

    public boolean checkCorrectness() {
        var condParents = checkParents(root);
        var condRoot = root.color == Color.BLACK;
        checkProperties(root);
        return condParents && condRoot;

    }

    private int checkProperties(RBNode node) {
        if (node == nil) return 1;
        var h1 = checkProperties(node.right);
        var h2 = checkProperties(node.left);
        if (h1 != h2) throw new RedBlackTreePropertyViolatedException("RB tree condition violated. Height of subtrees is different.");
        if (node.color == Color.RED && (node.left.color == Color.RED || node.right.color == Color.RED))
            throw new RedBlackTreePropertyViolatedException("RB tree condition violated. Red node has a red child.");
        if (node.color == Color.BLACK)
            return h1 + 1;
        else
            return h1;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        Queue<RBNode> queue = new LinkedList<>();
        queue.add(root);
        var nonNulls = 0;
        var k = 0;
        while (!queue.isEmpty() && nonNulls < getCurrentNumberOfElements()) {
            var n = queue.remove();
            builder.append(" ");
            builder.append(n.toString());
            builder.append(" ");
            if ((k + 2 & (k + 1)) == 0) {
                builder.append("\n");
            }
            if (n != nil) {
                nonNulls++;
            }
            queue.add(n.left); //if (n != null)
            queue.add(n.right);//if (n != null)
            k++;
        }
        if (!checkCorrectness()) throw new RedBlackTreePropertyViolatedException("RedBlackTree properties are violated.");
        return builder.toString();
    }
}
