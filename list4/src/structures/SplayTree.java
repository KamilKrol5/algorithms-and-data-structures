package structures;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class SplayTree<T extends Comparable<T>> extends AbstractTree<T> {

    private class SplayNode implements AbstractTree.Node<T> {
        T key;
        SplayNode left = null;
        SplayNode right = null;

        SplayNode(T key) {
            this.key = key;
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
        public String toString()  {
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
        return false;
    }

    @Override
    protected boolean deleteImpl(T element) {
        return false;
    }

    @Override
    protected boolean searchImpl(T element) {
        return false;
    }

    @Override
    protected void loadImpl(File file, Function<String, T> fromString, String delimiterPattern) throws IOException {

    }

}
