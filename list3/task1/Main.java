import java.util.Scanner;

public class Main {
    private static final String message = "Possible commands:\n" +
            "insert|empty|top|pop|priority|print|printTree\n" +
            "i|e|t|pp|pr|p|pT";
    static PriorityQueue queue = new PriorityQueue();
    public static void main(String[] args) {
        System.out.println("Enter number of operations:\n");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int m = scanner.nextInt();
            System.out.println(message);
            for (int i = 0; i < m; i++) {
                acceptInput(scanner);
            }
        } else {
            System.out.println("Given argument is not a number.");
            return;
        }
    }

    private static void acceptInput(Scanner scanner) {
        var command = scanner.next();
        switch (command) {
            case "insert":
            case "i":
                if (scanner.hasNextInt()) {
                    var value = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        var priority = scanner.nextInt();
                        queue.insert(value,priority);
                        System.out.println("Inserted value "+value+" (priority = "+priority+")");
                    } else {
                        System.out.println("Wrong priority value.");
                    }
                } else {
                    System.out.println("Wrong value provided.");
                }
                break;
            case "empty":
            case "e":
                System.out.println(queue.empty());
                break;
            case "print":
            case "p":
                System.out.println(queue.print());
                break;
            case "printTree":
            case "pT":
                System.out.println(queue.printAsTree());
                break;
            case "top":
            case "t":
                queue.top();
                break;
            case "pop":
            case "pp":
                queue.pop();
                break;
            case "priority":
            case "pr":
                if (scanner.hasNextInt()) {
                    var value = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        var priority = scanner.nextInt();
                        queue.priority(value,priority);
                        System.out.println("Called priority function with value "+value+" (priority = "+priority+")");
                    } else {
                        System.out.println("Wrong priority value.");
                    }
                } else {
                    System.out.println("Wrong value provided.");
                }
                break;
        }
    }
}
