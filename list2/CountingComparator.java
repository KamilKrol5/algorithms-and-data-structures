import java.util.Comparator;

public class CountingComparator<T> implements Comparator<T> {
    private int count;
    private Comparator<T> comparator;
    private Boolean isSilent;

    public CountingComparator(Comparator<T> comparator, Boolean isSilent) {
        this.comparator = comparator;
        this.isSilent = isSilent;
    }

    @Override
    public int compare(T o1, T o2) {
        count++;
        if (!isSilent) System.err.printf("Comparing %s to %s\n",o1.toString(),o2.toString());
        return comparator.compare(o1, o2);
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }
}
