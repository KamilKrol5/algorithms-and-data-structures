package structures;

import java.io.File;
import java.util.List;
import java.util.function.Function;

public interface Tree <T extends Comparable<T>> {
    void insert(T element);
    void delete(T element);
    boolean search(T element);
    boolean isEmpty();
    void load(File file, Function<String, T> fromString, String delimiterPattern);
    List<T> inOrder();
}
