package utility;

import java.util.Comparator;

public class CountingComparator<T> implements Comparator<T> {
    private long count;
    private Comparator<T> comparator;

    public CountingComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(T o1, T o2) {
        count++;
        return comparator.compare(o1, o2);
    }

    public long getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }
}
