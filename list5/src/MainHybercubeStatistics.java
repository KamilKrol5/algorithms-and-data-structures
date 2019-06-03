import algorithms.EdmondsKarp;
import graphs.HypercubeGraphGenerator;
import graphs.Vertex;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MainHybercubeStatistics {
    private static final int NUMBER_OF_TESTS = 15;

    //output is scv file: [size,max flow,augmenting paths count,time in milliseconds]
    public static void main(String[] args) {
        for (int size = 1; size <= 16; size++) {
            try (var writer = new PrintWriter(new FileWriter("files/hybercube" + size + ".txt"))) {
                for (int h = 0; h < NUMBER_OF_TESTS; h++) {
                    HypercubeGraphGenerator hypercubeGraph = new HypercubeGraphGenerator(size);
                    EdmondsKarp edmondsKarp = new EdmondsKarp(hypercubeGraph.getHybercubeGraph(), new Vertex(0), new Vertex((1 << size) - 1));

                    long startTime = System.nanoTime();
                    var maxFlow = edmondsKarp.maxFlow();
                    long endTime = System.nanoTime();
                    var paths = edmondsKarp.getAugmentingPathCount();
                    long time = (long) ((endTime - startTime) / 1_000_000.0);
                    writer.print(size);
                    writer.print(';');
                    writer.print(maxFlow);
                    writer.print(';');
                    writer.print(paths);
                    writer.print(';');
                    writer.print(time);
                    writer.print(';');
                    writer.print('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
