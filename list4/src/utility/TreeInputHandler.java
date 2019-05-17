package utility;

import structures.AbstractTree;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

public class TreeInputHandler {

    private final static String message = "Choose one of the operations:\n" +
            "i x (insert x); io (in order)\n" +
            "d x (delete x); s x (search x)\n" +
            "e (is empty);   p (print)\n";

    public static <T extends Comparable<T>> void handleInputInt(AbstractTree<T> tree,
                                                                InputStream inputStream,
                                                                PrintStream printStream,
                                                                Function<String, T> fromString,
                                                                String delimiterPattern) {
        Scanner scanner = new Scanner(inputStream);
        while (true) {
            printStream.println(message);
            scanner.reset();
            String input = scanner.next();
            scanner.useDelimiter(delimiterPattern);
            switch (input) {
                case "i":
                    if (scanner.hasNext())
                        tree.insert(fromString.apply(scanner.next()));
                    else
                        printStream.println("Wrong input provided.");
                    break;
                case "d":
                    if (scanner.hasNext())
                        tree.delete(fromString.apply(scanner.next()));
                    else
                        printStream.println("Wrong input provided.");
                    break;
                case "s":
                    if (scanner.hasNext())
                        tree.search(fromString.apply(scanner.next()));
                    else
                        printStream.println("Wrong input provided.");
                    break;
                case "e":
                    printStream.println(tree.isEmpty());
                    break;
                case "p":
                    printStream.println(tree.toString());
                    break;
                case "io":
                    printStream.println(tree.inOrder());
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    printStream.println("Wrong operation.");
            }
        }
    }

}
