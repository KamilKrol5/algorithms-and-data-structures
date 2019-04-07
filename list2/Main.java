import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;


public class Main {
    static Map<String, BiConsumer<SortingAlgorithms, List<Integer>>> sortingAlgorithms = new HashMap<>();

    static {
        sortingAlgorithms.put("select", SortingAlgorithms::selectSort);
        sortingAlgorithms.put("insert", SortingAlgorithms::insertionSort);
        sortingAlgorithms.put("heap", SortingAlgorithms::heapSort);
        sortingAlgorithms.put("quick", SortingAlgorithms::quickSort);
        sortingAlgorithms.put("mquick", SortingAlgorithms::modifiedQuickSort);
    }

    public static void main(String[] args) {
        var startTimeMain = System.nanoTime();
        int numberOfArguments = 0;
        List<Integer> numbers;
//        List<String> sortingAlgorithms = Arrays.asList("select","insert","heap","quick","mquick");
        var sortingAlgorithm = sortingAlgorithms.get("select");
        var ascending = true;
        String statisticsFilename = "stat.txt";
        int numberOfRepetitions = 0;
        boolean statisticsMode = false;

//        System.out.println("Hello World!");
//        var list1 = new ArrayList<>(Arrays.asList(1,9,2,3,5,7,0));
//        var list2 = new ArrayList<>(Arrays.asList(10,9,2,30,5,70,0,41,42,12,10,10));
//        var list3 = new ArrayList<>(Arrays.asList(11,9,0,30,5,-8,0,41,42,12,10,4,5,1,2));
//        var so = new SortingAlgorithms(false,false);
//        so.modifiedQuickSort(list1);
//        so.modifiedQuickSort(list2);
//        so.modifiedQuickSort(list3);
//        System.out.println(list1);
//        System.out.println(list2);
//        System.out.println(list3);
        /*  taking validating and interpreting arguments  */
        var scanner = new Scanner(System.in);
        String s = "Valid arguments are: --type select|insert|heap|quick|mquick " +
                "(to specify sorting algorithm),\n--asc|--desc (to specify order)\n" +
                " OR --stat <filename> <k> (to run program for statistics, they will be saved to a file, k single tests will be done)";
        if (args.length < 3) {
            System.out.println("Too little arguments given, default type is select sort and default order is ascending.\n" + s);
        } else {
            if (args[0].equals("--stat") && !args[1].isEmpty() && !args[2].isEmpty()) {
                statisticsFilename = args[1];
                try {
                    numberOfRepetitions = Integer.parseInt(args[2]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Last argument is not a number.\n" + s);
                    System.exit(0);
                }
                statisticsMode = true;
            } else if (!args[0].equals("--type") || !sortingAlgorithms.containsKey(args[1]) || !(args[2].equals("--asc")
                    || args[2].equals("--desc"))) {
                System.err.println("Invalid arguments.\n" + s);
            } else {
                sortingAlgorithm = sortingAlgorithms.get(args[1]);
                ascending = args[2].equals("--asc");
            }
        }
        if (!statisticsMode) {
            System.out.println("Enter amount of numbers to sort: ");
            while (true) {
                if (scanner.hasNextInt()) {
                    numberOfArguments = scanner.nextInt();
                    break;
                } else {
                    System.out.println("Given argument is not a number!");
                    System.out.println("Enter amount of numbers to sort: ");
                    scanner.next();
                }
            }
            numbers = new ArrayList<>(numberOfArguments);
            for (int i = 0; i < numberOfArguments; i++) {
                System.out.println("Enter " + (i + 1) + ". number: ");
                while (true) {
                    if (scanner.hasNextInt()) {
                        numbers.add(scanner.nextInt());
                        break;
                    } else {
                        System.out.println("Given argument is not an integer. Try again: ");
                        scanner.next();
                    }
                }
            }

            /*  creating comparator and sorting  */
            CountingComparator<Integer> countingComparator;
            if (!ascending)
                countingComparator = new CountingComparator<Integer>(Comparator.reverseOrder(), false);
            else
                countingComparator = new CountingComparator<Integer>(Comparator.naturalOrder(), false);
            var so = new SortingAlgorithms(false, countingComparator);
            var startTime = System.nanoTime();
            sortingAlgorithm.accept(so, numbers);
            var finishTime = System.nanoTime();
            var durationInMilliseconds = TimeUnit.NANOSECONDS.toMillis(finishTime - startTime);

            /*  providing user with statistics  */
            System.err.println("Number of comparisons: " + countingComparator.getCount());
            System.err.println("Number of swaps: " + so.getNumberOfSwaps());
            so.resetNumberOfSwaps();
            checkSorting(numbers, ascending, countingComparator);
            countingComparator.resetCount();
            System.err.println("Time of sorting: " + durationInMilliseconds + " milliseconds (with " + args[1] + " algorithm)");
            System.out.println("Number of sorted elements: " + numberOfArguments);
            System.out.println(numbers.toString());
        } else {
            CountingComparator<Integer> countingComparator;
            countingComparator = new CountingComparator<Integer>(Comparator.naturalOrder(), true);
            var random = new Random();
            try (FileWriter fileWriter = new FileWriter(statisticsFilename, false);
                 PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter), false)) {
                for (int n = 100; n <= 10000; n += 100) {
                    for (int kk = 0; kk < numberOfRepetitions; kk++) {
                        var list = new ArrayList<Integer>(n);
                        for (int h = 0; h < n; h++) {
                            list.add(random.nextInt());
                        }
                        var so = new SortingAlgorithms(true, countingComparator);

                        doSortingForStatistics(so, countingComparator, list, printWriter);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error, cannot create the file.");
                System.exit(0);
            }
        }
        var finishTimeMain = System.nanoTime();
        System.out.println("Time: "+ TimeUnit.NANOSECONDS.toMillis(finishTimeMain - startTimeMain));
    }

    private static void doSortingForStatistics(SortingAlgorithms so, CountingComparator<Integer> countingComparator,
                                               ArrayList<Integer> list, PrintWriter printWriter) throws IOException {
        for (var pair : sortingAlgorithms.entrySet()) {
            printWriter.print(pair.getKey());
            printWriter.print(',');
            doSingleSortingForStatistics(pair.getValue(), (List<Integer>) list.clone(), so, countingComparator, printWriter);
        }
    }

    private static void doSingleSortingForStatistics(BiConsumer<SortingAlgorithms,
            List<Integer>> consumer, List<Integer> list, SortingAlgorithms so, CountingComparator<Integer> countingComparator, PrintWriter printWriter) throws IOException {
        so.resetNumberOfSwaps();
        countingComparator.resetCount();
        var startTime = System.nanoTime();
        consumer.accept(so, list);
        var finishTime = System.nanoTime();
        //var durationInMilliseconds = TimeUnit.NANOSECONDS.toMillis(finishTime - startTime);
        printWriter.print(list.size());
        printWriter.print(',');
        printWriter.print(countingComparator.getCount());
        printWriter.print(',');
        printWriter.print(so.getNumberOfSwaps());
        printWriter.print(',');
        printWriter.print(TimeUnit.NANOSECONDS.toNanos(finishTime - startTime));
        printWriter.println();
        //System.out.println(list);
    }

    private static void checkSorting(List<Integer> numbers, Boolean ascedning, Comparator<Integer> comparator) {
        for (int j = 0; j < numbers.size() - 1; j++) {
            if (comparator.compare(numbers.get(j), numbers.get(j + 1)) > 0) {
                System.out.println("Given list is NOT sorted in the given order.");
                return;
            }
        }
        System.out.println("Given list is sorted in the given order.");
    }
}
