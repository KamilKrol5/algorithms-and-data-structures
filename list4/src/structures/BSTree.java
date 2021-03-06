package structures;

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
        public String toString() {
            return key.toString();
        }

        @Override
        public void setKey(T t) {
            this.key = t;
            updateNumberOfNodeModifications(1);
        }

        public void setLeft(BSTNode node) {
            left = node;
            updateNumberOfNodeModifications(1);
        }

        public void setRight(BSTNode node) {
            right = node;
            updateNumberOfNodeModifications(1);
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
            updateNumberOfNodeModifications(1);
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
                    currentNode.setLeft(new BSTNode(element));
                    return true;
                }
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.right == null) {
                    currentNode.setRight(new BSTNode(element));
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
        previous.setLeft(root);
        boolean leftPath = true;
        var currentNode = root;
        while (currentNode != null) {
            var compareResult = getComparator().compare(element, currentNode.key);
            if (compareResult == 0) {
                if (currentNode.right == null && currentNode.left == null) {
                    if (leftPath) {
                        previous.setLeft(null);
                    } else {
                        previous.setRight(null);
                    }
                } else if (currentNode.left == null) { // && currentNOde.right != null
                    if (leftPath) {
                        previous.setLeft(currentNode.right);
                    } else {
                        previous.setRight(currentNode.right);
                    }
                } else if (currentNode.right == null) { // && currentNode.left != null
                    if (leftPath) {
                        previous.setLeft(currentNode.left);
                    } else {
                        previous.setRight(currentNode.left);
                    }
                } else {
                    var minParent = findMinKeyParent(currentNode.right);
                    if (minParent == null) {
                        swapKeys(currentNode, currentNode.right);
                        currentNode.setRight(currentNode.right.right);
                    } else {
                        swapKeys(minParent.left, currentNode);
                        minParent.setLeft(minParent.left.right);
                    }
                }
                if (previous == helpNode) {
                    root = previous.left;
                    updateNumberOfNodeModifications(1);
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

    private BSTNode findMinKeyParent(BSTNode startNode) {
        if (startNode.left == null) {
            return null;
        } else if (startNode.left.left == null) {
            return startNode;
        } else {
            return findMinKeyParent(startNode.left);
        }
    }

//    @Override
//    protected void loadImpl(File file, Function<String, T> fromString, String delimiterPattern) throws IOException {
//        if (file.canRead()) {
//            try {
//                FileReader fileReader = new FileReader(file);
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//                Scanner scanner = new Scanner(bufferedReader);
//                scanner.useDelimiter(delimiterPattern); //"\\W+"
//                String token;
//                while (scanner.hasNext()) {
//                    token = scanner.next();
//                    insert(fromString.apply(token));
//                }
//                bufferedReader.close();
//            } catch (FileNotFoundException e) {
//                throw new FileNotFoundException("Cannot find file" + file.getName());
//            } catch (IOException e) {
//                throw new IOException();
//            }
//        }
//    }
}
