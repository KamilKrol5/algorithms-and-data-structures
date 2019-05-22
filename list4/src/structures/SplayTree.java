package structures;

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

        @Override
        public void setKey(T t) {
            this.key = t;
            updateNumberOfNodeModifications(1);
        }

        public void setLeft(SplayNode node) {
            left = node;
            updateNumberOfNodeModifications(1);
        }

        public void setRight(SplayNode node) {
            right = node;
            updateNumberOfNodeModifications(1);
        }

        public void setParent(SplayNode node) {
            parent = node;
            updateNumberOfNodeModifications(1);
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
            updateNumberOfNodeModifications(1);
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
                    currentNode.setLeft(new SplayNode(element, currentNode));
                    splay(currentNode.left);
                    return true;
                }
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.right == null) {
                    currentNode.setRight(new SplayNode(element, currentNode));
                    splay(currentNode.right);
                    return true;
                }
                currentNode = currentNode.getRight();
            }
        }
        throw new IllegalStateException("Insert called with no existent value but the value has not been inserted. Value = " + element);
    }

    @Override
    public boolean deleteImpl(T element) {
        SplayNode nodeToDelete = find(element);
        if (nodeToDelete == null) return false;
        splay(nodeToDelete);
        if (nodeToDelete.left == null)
            replace(nodeToDelete, nodeToDelete.right);
        else if (nodeToDelete.right == null)
            replace(nodeToDelete, nodeToDelete.left);
        else {
            SplayNode y = findMinKeyNode(nodeToDelete.right);
            if (y.parent != nodeToDelete) {
                replace(y, y.right);
                y.setRight(nodeToDelete.right);
                y.right.setParent(y);
            }
            replace(nodeToDelete, y);
            y.setLeft(nodeToDelete.left);
            y.left.setParent(y);
        }
        return true;
    }

    @Override
    protected boolean searchImpl(T element) {
        var findResult = find(element);
        if (findResult == null) return false;
        else if (!findResult.key.equals(element)) throw new IllegalStateException("Find has found wrong value.");
        else {
            splay(findResult);
            return true;
        }
    }

    private SplayNode findMinKeyNode(SplayNode startNode) {
        if (startNode.left == null) {
            return startNode;
        } else {
            return findMinKeyNode(startNode.left);
        }
    }

    private void splay(SplayNode node) {
        if (node == null) return;
        while (node.parent != null) {
            if (node.parent.parent == null) {
                if (node.parent.left == node) rightRotation(node.parent); //zig
                else leftRotation(node.parent); //zag
            } else if (node.parent.left == node && node.parent.parent.left == node.parent) { //zig-zig
                rightRotation(node.parent.parent);
                rightRotation(node.parent);
            } else if (node.parent.right == node && node.parent.parent.right == node.parent) { //zag-zag
                leftRotation(node.parent.parent);
                leftRotation(node.parent);
            } else if (node.parent.left == node && node.parent.parent.right == node.parent) { //zag-zig
                rightRotation(node.parent);
                leftRotation(node.parent);
            } else { //zig-zag
                leftRotation(node.parent);
                rightRotation(node.parent);
            }
        }
    }

    private void replace(SplayNode node1, SplayNode node2) {
        if (node1.parent == null) {
            root = node2;
            updateNumberOfNodeModifications(1);
        } else if (node1 == node1.parent.left) {
            node1.parent.setLeft(node2);
        } else {
            node1.parent.setRight(node2);
        }
        if (node2 != null) {
            node2.setParent(node1.parent);
        }
    }

    private SplayNode find(T element) {
        SplayNode z = root;
        while (z != null) {
            if (getComparator().compare(z.key, element) < 0) {
                z = z.right;
            } else if (getComparator().compare(element, z.key) < 0) {
                z = z.left;
            } else return z;
        }
        return null;
    }

    private void leftRotation(SplayNode x) {
        SplayNode y = x.right;
        if (y != null) {
            x.setRight(y.left);
            if (y.left != null) {
                y.left.setParent(x);
            }
            y.setParent(x.parent);
        }

        if (x.parent == null) {
            root = y;
            updateNumberOfNodeModifications(1);
        } else if (x == x.parent.left) {
            x.parent.setLeft(y);
        } else {
            x.parent.setRight(y);
        }
        if (y != null) {
            y.setLeft(x);
        }
        x.setParent(y);
    }

    private void rightRotation(SplayNode x) {
        SplayNode y = x.left;
        if (y != null) {
            x.setLeft(y.right);
            if (y.right != null) {
                y.right.setParent(x);
            }
            y.setParent(x.parent);
        }
        if (x.parent == null) {
            root = y;
            updateNumberOfNodeModifications(1);
        } else if (x == x.parent.left) {
            x.parent.setLeft(y);
        } else {
            x.parent.setRight(y);
        }
        if (y != null) {
            y.setRight(x);
        }
        x.setParent(y);
    }

    private boolean checkParents(SplayNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Given node is null");
        }
        var cond1 = true;
        var cond2 = true;
        if (node.left != null) {
            cond1 = node.left.parent == node;
        }
        if (node.right != null) {
            cond2 = node.right.parent == node;
        }
        return cond1 && cond2;
    }

}
