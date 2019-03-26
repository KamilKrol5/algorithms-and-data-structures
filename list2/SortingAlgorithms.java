import java.util.Comparator;
import java.util.List;

class SortingAlgorithms {
    private Boolean isSilentMode = false;
    private Boolean descending = false;
    Comparator<Integer> comparator;

    SortingAlgorithms(Boolean isSilentMode, Boolean descending) {
        if (descending)
            comparator = new CountingComparator<Integer>(Comparator.reverseOrder(), isSilentMode);
        else
            comparator = new CountingComparator<Integer>(Comparator.naturalOrder(), isSilentMode);

    }

    void selectSort(List<Integer> data) {
        if (data.isEmpty())
            return;
        Integer tmp;
        for (int i = 0; i < data.size(); i++) {
            var pair = findMin(data.subList(i, data.size()));
            swap(data,i,pair.getFirst() + i);
        }

    }

    void insertionSort(List<Integer> data) {
        if (data.isEmpty())
            return;
        for (int i = 1; i < data.size(); i++) {
            for (int j = i-1; j >= 0; j--) {
                if (comparator.compare(data.get(j+1),data.get(j)) >= 0) //larger
                    break;
                swap(data,j,j+1);
            }
        }
    }

    void heapSort(List<Integer> data) {

    }

    void quickSort(List<Integer> data) {

    }

    private Boolean compare(Integer first, Integer second) {
        if (!isSilentMode) System.err.println("Compare " + first + " with " + second);
        if (descending) return first <= second;
        else return first >= second;
    }

    private void swap(List<Integer> list, int index1, int index2) {
        Integer tmp = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,tmp);
    }

    private Pair<Integer, Integer> findMin(List<Integer> list) {
        if (!list.isEmpty()) {
            Integer min = list.get(0);
            Integer indexOfMin = 0;
            for (int i = 1; i < list.size(); i++) {
                if (comparator.compare(min, list.get(i)) >= 0) {
                    min = list.get(i);
                    indexOfMin = i;
                }
            }
            return new Pair<>(indexOfMin, min);
        } else return null;
    }
}
