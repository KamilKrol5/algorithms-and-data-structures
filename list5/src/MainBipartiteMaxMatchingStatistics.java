import algorithms.EdmondsKarp;
import graphs.BipartiteGraphGenerator;
import graphs.DirectedGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainBipartiteMaxMatchingStatistics {
    private static final int NUMBER_OF_TESTS = 100;

    //output is scv file: [size,degree,max matching size,time in nanoseconds]
    public static void main(String[] args) {
        for (int size = 3; size <= 10; size++) {
            try (var writer = new PrintWriter(new FileWriter("files/max-matching" + size + ".csv"))) {
                for (int degree = 1; degree <= size; degree++) {
                    for (int h = 0; h < NUMBER_OF_TESTS; h++) {
                        BipartiteGraphGenerator generator = new BipartiteGraphGenerator(size, degree);
                        DirectedGraph graph = generator.getBipartiteGraph();
                        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, generator.getStart(), generator.getEnd());
                        long startTime = System.nanoTime();
                        int maxMatchingSize = edmondsKarp.maxFlow();
                        long endTime = System.nanoTime();
                        long time = (endTime - startTime);
                        writer.print(size);
                        writer.print(';');
                        writer.print(degree);
                        writer.print(';');
                        writer.print(maxMatchingSize);
                        writer.print(';');
                        writer.print(time);
                        writer.print('\n');
                    }
                    System.out.println("Size: "+size+", degree: "+degree);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
