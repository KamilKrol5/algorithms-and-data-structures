public class Pair<T, Y extends Comparable<Y>> implements Comparable<Pair<T, Y>> {
    private T value;
    private Y priority;

    Pair(T value, Y second) {
        this.value = value;
        this.priority = second;
    }

    public T getValue() {
        return value;
    }

    public Y getPriority() {
        return priority;
    }

    public void setPriority(Y priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Pair<T, Y> o) {
        return this.priority.compareTo(o.priority);
    }

    @Override
    public String toString() {
        return "(" + value + ", p = " + priority + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
