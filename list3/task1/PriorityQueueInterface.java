public interface PriorityQueueInterface<T> {
    public void insert(Integer arg, T priority);
    public boolean empty();
    public T top();
    public T pop();
    public void priority(T arg, T priority);
    public String print();
}
