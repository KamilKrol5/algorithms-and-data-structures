import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        var list1 = new ArrayList<>(Arrays.asList(1,9,2,3,5,7,0));
        var list2 = new ArrayList<>(Arrays.asList(10,9,2,30,5,70,0,41,42,12,10,10));
        var so = new SortingAlgorithms(false,false);
        so.insertionSort(list1);
        so.insertionSort(list2);
        System.out.println(list1);
        System.out.println(list2);
    }
}
