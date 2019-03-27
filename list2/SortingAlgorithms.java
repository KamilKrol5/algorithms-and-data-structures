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
        makeHeap(data);
        int size = data.size();
        for (int j = 0; j < data.size(); j++) {
            swap(data,size-j-1,0);
            siftDown(data.subList(0,size-j-1), 0);
        }
    }


    void quickSort(List<Integer> data) {
        if (data.size() >=  1){
            int pivot = data.get(data.size() - 1);
            int limit = 0;
            for (int i = 0; i < data.size()-1; i++) {
                if ( comparator.compare(pivot,data.get(i)) >= 0) {
                    swap(data,limit,i);
                    limit++;
                }
            }
            swap(data,data.size() - 1,limit);
            quickSort(data.subList(0,limit));
            quickSort(data.subList(limit+1,data.size()));
        }
    }

    public void modifiedQuickSort(List<Integer> list) {
        int size = list.size();
        if (size >=  1){
            if (size > 16) {
                int pivotIndex = getMedianIndex(list, 0, size / 2 - 1, size - 1);
                int pivot = list.get(pivotIndex);
                swap(list, pivotIndex, size - 1);
                int limit = 0;
                for (int i = 0; i < size - 1; i++) {
                    if (comparator.compare(pivot, list.get(i)) >= 0) {
                        swap(list, limit, i);
                        limit++;
                    }
                }
                swap(list, size - 1, limit);
                quickSort(list.subList(0, limit));
                quickSort(list.subList(limit + 1, size));
            } else {
                insertionSort(list);
            }
        }
    }

    private void swap(List<Integer> list, int index1, int index2) {
        Integer tmp = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,tmp);
    }

    private void makeHeap(List<Integer> data) {
        int i = data.size() / 2 - 1;
        for (int k = i; k >= 0; k--) {
            siftDown(data,k);
        }
    }

    private void siftDown(List<Integer> list, int index) {
        int currentIndex, leftChild, rightChild;
        int size = list.size();
        while (index < size) {
            currentIndex = index;

            leftChild = 2 * currentIndex + 1;
            rightChild = leftChild + 1;

            if (leftChild < size && comparator.compare(list.get(leftChild),list.get(currentIndex)) > 0)
                currentIndex = leftChild;
            if (rightChild < size && comparator.compare(list.get(rightChild),list.get(currentIndex)) > 0)
                currentIndex = rightChild;

            if (currentIndex == index)
                return;

            swap(list,currentIndex,index);

            index = currentIndex;
        }
    }

    public int getMedianIndex(List<Integer> list, Integer i1,Integer i2,Integer i3) {
        int x = list.get(i1);
        int y = list.get(i2);
        int z = list.get(i3);
        if (comparator.compare(x,y) >=0) {
            if (comparator.compare(y, z) >= 0) return i2;
            else if (comparator.compare(x,z) >= 0) return i3;
            else return i1;
        } else {
            if (comparator.compare(x,z) >= 0) return i1;
            else if (comparator.compare(y,z) >= 0) return i3;
            else return i2;
        }
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
