import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        var list1 = new ArrayList<>(Arrays.asList(1,9,2,3,5,7,0));
        var list2 = new ArrayList<>(Arrays.asList(10,9,2,30,5,70,0,41,42,12,10,10));
        var list3 = new ArrayList<>(Arrays.asList(11,9,0,30,5,-8,0,41,42,12,10,4,5,1,2));
        var so = new SortingAlgorithms(false,false);
        so.modifiedQuickSort(list1);
        so.modifiedQuickSort(list2);
        so.modifiedQuickSort(list3);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
    }
}
