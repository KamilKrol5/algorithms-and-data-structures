import structures.SplayTree;
import utility.AbstractTreeExtensions;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static utility.AbstractTreeExtensions.*;

@SuppressWarnings("Duplicates")
public class MainSplayTree {

    public static void main(String[] args) {
        String delimiterPattern = "\\W+";
        long comparisons = 0;
        long modifications = 0;
        SplayTree<String> t = new SplayTree<String>();
        var file = new File("src/data/scrabble-polish-words.txt");
        file.setReadable(true);

        long insertTime = measureTime(() -> t.load(file, String::toString, "\\W+"));
        showChosenStatistics(t);
        System.out.println(TimeUnit.NANOSECONDS.toMillis(insertTime)+"ms");
        showAverageStatisticsForOperation(t, "insert", t.getNumberOfInserts(), TimeUnit.NANOSECONDS.toMillis(insertTime));
        comparisons += t.getNumberOfComparisons();
        modifications += t.getNumberOfNodeModifications();
        t.resetStatistics();

        long searchTime = measureTime(() -> searchDataFromFile(t, file, String::toString, delimiterPattern));
        showChosenStatistics(t);
        System.out.println(TimeUnit.NANOSECONDS.toMillis(searchTime)+"ms");
        showAverageStatisticsForOperation(t, "search", t.getNumberOfSearches(), TimeUnit.NANOSECONDS.toMillis(searchTime));
        comparisons += t.getNumberOfComparisons();
        modifications += t.getNumberOfNodeModifications();
//        showStatistics(t);
        t.resetStatistics();

        long deleteTime = measureTime(() -> removeDataFromFile(t, file, String::toString, delimiterPattern));
        showChosenStatistics(t);
        System.out.println(TimeUnit.NANOSECONDS.toMillis(deleteTime)+"ms");
        showAverageStatisticsForOperation(t, "delete", t.getNumberOfDeletions(), TimeUnit.NANOSECONDS.toMillis(deleteTime));
        comparisons += t.getNumberOfComparisons();
        modifications += t.getNumberOfNodeModifications();
        t.resetStatistics();

        System.out.println("Sum of comparisons: " + comparisons);
        System.out.println("Sum of modifications: " + modifications);
        System.out.println("All inserts time: " + TimeUnit.NANOSECONDS.toMillis(insertTime));
        System.out.println("All searches time: " + TimeUnit.NANOSECONDS.toMillis(searchTime));
        System.out.println("All deletions time: " + TimeUnit.NANOSECONDS.toMillis(deleteTime));
        //TreeInputHandler.handleInputInt(t, System.in, System.out, String::toString, delimiterPattern);
    }
}
