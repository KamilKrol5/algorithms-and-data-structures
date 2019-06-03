import algorithms.EdmondsKarp;
import graphs.BipartiteGraphGenerator;
import graphs.DirectedGraph;

public class MainBipartiteMaxMatching {
    public static void main(String[] args) {
        if (args.length < 4)
            throw new IllegalArgumentException("Missing argument. Valid arguments are: --size k --degree i.\n");
        if (args[0].equals("--size")) {
            try {
                int size = Integer.parseInt(args[1]);
                if (size <= 0 || size > 16) {
                    System.out.println("Size must be a positive number between 1 and 16.\n");
                    return;
                }
                if (!args[2].equals("--degree")) {
                    System.out.println("Wrong argument provided. Valid arguments are: --size k --degree i.\n");
                    return;
                }
                int degree = Integer.parseInt(args[3]);
                if (degree > size) {
                    System.out.println("Degree cannot be larger than size.\n");
                    return;
                }

                BipartiteGraphGenerator generator = new BipartiteGraphGenerator(size, degree);
                DirectedGraph graph = generator.getBipartiteGraph();
                System.out.println(graph.toString());
                EdmondsKarp edmondsKarp = new EdmondsKarp(graph, generator.getStart(), generator.getEnd());
                long startTime = System.nanoTime();
                System.out.println("Size of maximum matching: " + edmondsKarp.maxFlow());
                long endTime = System.nanoTime();
                System.out.println("Time of computing size of maximum matching: " + (endTime - startTime) / 1_000_000 + "ms");


            } catch (IllegalArgumentException e) {
                System.out.println("Size and degree must be positive numbers.");
            }
        } else if (!args[1].equals("--glpk")) {
            String filename = args[1];
            //TODO implement
        }


    }
}
