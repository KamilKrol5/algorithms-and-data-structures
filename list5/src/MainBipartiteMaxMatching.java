import algorithms.EdmondsKarp;
import graphs.BipartiteGraphGenerator;
import graphs.DirectedGraph;
import graphs.GraphEdge;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainBipartiteMaxMatching {
    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("Missing argument. Valid arguments are: --size k --degree i OR --glpkall OR --size k --degree i --glpk");
        else if (args[0].equals("--glpkall")) {
            try {
                for (int k = 3; k <= 16; k++) {
                    for (int i = 1; i <= k; i++) {
                        prepareGlpkFile(k, i);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (args.length < 4)
            throw new IllegalArgumentException("Missing argument. Valid arguments are: --size k --degree i OR --glpkall OR --size k --degree i --glpk.\n");
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
                if (args.length == 5 && args[4].equals("--glpk")) {
                    prepareGlpkFile(size, degree);
                } else {
                    BipartiteGraphGenerator generator = new BipartiteGraphGenerator(size, degree);
                    DirectedGraph graph = generator.getBipartiteGraph();
                    System.out.println(graph.toString());
                    EdmondsKarp edmondsKarp = new EdmondsKarp(graph, generator.getStart(), generator.getEnd());
                    long startTime = System.nanoTime();
                    System.out.println("Size of maximum matching: " + edmondsKarp.maxFlow());
                    long endTime = System.nanoTime();
                    System.out.println("Time of computing size of maximum matching: " + (endTime - startTime) / 1_000_000 + "ms");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Size and degree must be positive numbers.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private static void prepareGlpkFile(int size, int degree) throws IOException {
        BipartiteGraphGenerator generator = new BipartiteGraphGenerator(size, degree);
        DirectedGraph graph = generator.getBipartiteGraph();
        try (var writer = new PrintWriter(new FileWriter("maxMatchingGLPK-size-" + size + "-deg" + degree + ".mod"))) {
            writer.print("data;\n");
            writer.print("param n := ");
            writer.print(2 * (1 << size) + 2);
            writer.print(";\n\nparam s := ");
            writer.print(2 * (1 << size) + 1);
            writer.print(";\n\nparam t := ");
            writer.print(2 * (1 << size) + 2);
            writer.print(";\n\nparam : E :   a :=");
            //System.out.println(graph.toString());
            var edges = graph.getEdgesList();
            for (GraphEdge e : edges) {
                writer.print("\n");
                writer.print(e.getStart().getValue() + 1);
                writer.print(" ");
                writer.print(e.getEnd().getValue() + 1);
                writer.print(" ");
                writer.print(e.getCapacity());
            }
            writer.print(";\n\nend;\n");
        }
    }
}
//glpsol.exe --model maxflow.mod --data maxMatchingGLPK-size-2-deg2.mod
