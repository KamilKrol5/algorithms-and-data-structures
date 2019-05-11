package structures;

import java.io.File;
import java.util.List;

public interface Tree <T extends Comparable<T>> {
    public void insert(T element);
    public void delete(T element);
    public boolean search(T element);
    public boolean isEmpty();
    public void load(File file);
    public List<T> inOrder();
}
