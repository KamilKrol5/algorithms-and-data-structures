package structures;

public interface PriorityQueueInterface<T, G extends Comparable<G>> {
    public void insert(T arg, G priority);
    public boolean empty();
    public T top();
    public T pop();
    public void priority(T arg, G priority);
    public String print();
}
