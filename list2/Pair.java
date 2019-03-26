public class Pair<T,Y> {
    private T first;
    private Y second;
    Pair(T first, Y second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }
}
