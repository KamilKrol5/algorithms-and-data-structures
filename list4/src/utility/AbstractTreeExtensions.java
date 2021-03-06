package utility;

import structures.AbstractTree;
import structures.Tree;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

public class AbstractTreeExtensions {
    public static <T extends Comparable<T>> void removeDataFromFile(Tree<T> tree, File file, Function<String, T> fromString, String delimiterPattern) {
        if (file.canRead()) {
            try {
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader);
                var scanner = new Scanner(bufferedReader);
                scanner.useDelimiter(delimiterPattern); //"\\W+"
                String token;
//                int i = 0;
                while (scanner.hasNext()) {
                    token = scanner.next();
//                    if (++i % 1 == 0)
//                        System.out.printf("Done %d (%s)\n", i, token);
                    tree.delete(fromString.apply(token));
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                //throw new FileNotFoundException("Cannot find file" + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static <T extends Comparable<T>> void searchDataFromFile(Tree<T> tree, File file, Function<String, T> fromString, String delimiterPattern) {
        if (file.canRead()) {
            try {
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader);
                var scanner = new Scanner(bufferedReader);
                scanner.useDelimiter(delimiterPattern); //"\\W+"
                String token;
                while (scanner.hasNext()) {
                    token = scanner.next();
                    tree.search(fromString.apply(token));
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                //throw new FileNotFoundException("Cannot find file" + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static <T extends Comparable<T>> void showStatistics(AbstractTree<T> t) {
        System.out.println("Current number of elements:" + t.getCurrentNumberOfElements());
        System.out.println("Maximum number of elements:" + t.getMaximumNumberOfElements());
        System.out.println("Number of comparisons:" + t.getNumberOfComparisons());
        System.out.println("Number of modifications:" + t.getNumberOfNodeModifications());
        System.out.println("Number of inserts:" + t.getNumberOfInserts());
        System.out.println("Number of deletions:" + t.getNumberOfDeletions());
        System.out.println("Number of inOrder calls:" + t.getNumberOfInOrders());
        System.out.println("Number of searches:" + t.getNumberOfSearches());
        System.out.println("Number of loads:" + t.getNumberOfLoads());
    }

    public static <T extends Comparable<T>> void showChosenStatistics(AbstractTree<T> t) {
//        System.out.println("Current number of elements:" + t.getCurrentNumberOfElements());
//        System.out.println("Number of comparisons:" + t.getNumberOfComparisons());
//        System.out.println("Number of modifications:" + t.getNumberOfNodeModifications());
        System.out.println(t.getNumberOfComparisons());
        System.out.println(t.getNumberOfNodeModifications());

    }

    public static <T extends Comparable<T>> void showAverageStatisticsForOperation(AbstractTree<T> t, String operationName, long numberOfOperationCalls, long execTime) {
        showAverageStatisticsForOperation(t, operationName, numberOfOperationCalls);
//        System.out.println("Average time per one " + operationName + ": " + (double)execTime / numberOfOperationCalls + " milliseconds");
        System.out.printf("%.3fms\n", (double)execTime / numberOfOperationCalls);
        System.out.println();
    }

    public static <T extends Comparable<T>> void showAverageStatisticsForOperation(AbstractTree<T> t, String operationName, long numberOfOperationCalls) {
//        System.out.println("Average number of comparisons per " + operationName + ": " + (double)t.getNumberOfComparisons() / numberOfOperationCalls + " comparisons");
        System.out.printf("%.3f\n",(double)t.getNumberOfComparisons() / numberOfOperationCalls);
//        System.out.println("Average number of node modifications per " + operationName + ": " + (double)t.getNumberOfNodeModifications() / numberOfOperationCalls + " modifications");
        System.out.printf("%.3f\n",(double)t.getNumberOfNodeModifications() / numberOfOperationCalls);
//        System.out.println();
    }

    public static long measureTime(Runnable func) {
        long start = System.nanoTime();
        func.run();
        long end = System.nanoTime();
        return end - start;
    }
}
