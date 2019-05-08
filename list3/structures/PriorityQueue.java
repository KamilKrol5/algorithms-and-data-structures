package structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueue<T, G extends Comparable<G>> implements PriorityQueueInterface<T, G> {

    private List<Pair<T,G>> data = new ArrayList<>();
    private Comparator<Pair<T,G>> comparator = Comparator.naturalOrder();

    public PriorityQueue() {

    }

    public PriorityQueue(List<Pair<T,G>> data) {
        this.data = data;
        makeHeap(data);
    }

    @Override
    public void insert(T arg, G priority) {
        data.add(new Pair<>(arg,priority));
        siftUp(data, data.size() - 1);
    }

    @Override
    public boolean empty() {
        return data.isEmpty();
    }

    @Override
    public T top() {
        if (!empty()) {
            //System.out.println(data.get(0).getValue());
            return data.get(0).getValue();
        } else return null;
    }

    @Override
    public T pop() {
        var returnValue = top();
        if (!empty()) {
            swap(data,0,data.size() - 1);
            data.remove(data.size() - 1);
            siftDown(data, 0);
        }
        return returnValue;
    }

    @Override
    public void priority(T arg, G priority) {
        for (int i = 0; i < data.size(); i++) {
            var p = data.get(i);
            if (arg.equals(p.getValue())) {
                if (priority.compareTo(p.getPriority()) < 0) {
                    p.setPriority(priority);
//                    siftUp(data, i);
                }
            }
        }
        makeHeap(data);
    }

    @Override
    public String print() {
        StringBuilder builder = new StringBuilder();
        for (int k = 0; k < data.size(); k++) {
            builder.append(data.get(k));
//            if ((k+2 & (k+1)) == 0) {
//                builder.append("\n");
//            }
        }
        return builder.toString();
    }

    public String printAsTree() {
        StringBuilder builder = new StringBuilder();
        for (int k = 0; k < data.size(); k++) {
            builder.append(data.get(k));
            if ((k+2 & (k+1)) == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private void makeHeap(List<Pair<T,G>> data) {
        int i = data.size() / 2 - 1;
        for (int k = i; k >= 0; k--) {
            siftDown(data, k);
        }
    }

    private void siftDown(List<Pair<T,G>> list, int index) {
        int currentIndex, leftChild, rightChild;
        int size = list.size();
        while (index < size) {
            currentIndex = index;

            leftChild = 2 * currentIndex + 1;
            rightChild = leftChild + 1;

            if (leftChild < size && comparator.compare(list.get(leftChild), list.get(currentIndex)) < 0)
                currentIndex = leftChild;
            if (rightChild < size && comparator.compare(list.get(rightChild), list.get(currentIndex)) < 0)
                currentIndex = rightChild;

            if (currentIndex == index)
                return;

            swap(list, currentIndex, index);

            index = currentIndex;
        }
    }

    private void siftUp(List<Pair<T,G>> list, int index) {
        int currentIndex, parent;
        while (index >= 0) {
            currentIndex = index;

            parent = (currentIndex - 1)/2;

            if (parent >= 0 && comparator.compare(list.get(parent), list.get(currentIndex)) > 0)
                currentIndex = parent;

            if (currentIndex == index)
                return;

            swap(list, currentIndex, index);

            index = currentIndex;
        }
    }

    private void swap(List<Pair<T,G>> list, int index1, int index2) {
//        if (!isSilentMode)
//            System.err.printf("Swapping %s with %s\n", list.get(index1).toString(), list.get(index2).toString());
        var tmp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, tmp);
    }
}
