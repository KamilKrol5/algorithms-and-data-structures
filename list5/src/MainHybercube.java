import algorithms.EdmondsKarp;
import graphs.GraphEdge;
import graphs.HypercubeGraphGenerator;
import graphs.Vertex;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainHybercube {

    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("Missing argument. Valid argument is --size k OR --glpkall OR --size k --glpk");
        else if (args[0].equals("--glpkall")) {
            for (int k = 1; k <= 16; k++) {
                HypercubeGraphGenerator hypercubeGraph = new HypercubeGraphGenerator(k);
                prepareGlpkFile(k, hypercubeGraph);
            }
        }
        if (args.length < 2)
            throw new IllegalArgumentException("Missing argument. Valid argument is --size k OR --glpkall OR --size k --glpk");
        if (args[0].equals("--size")) {
            try {
                int size = Integer.parseInt(args[1]);
                if (size <= 0 || size > 16) {
                    System.out.println("Size must be a positive number between 1 and 16");
                    return;
                }
                if (args.length == 3 && args[2].equals("--glpk")) {
                    HypercubeGraphGenerator hypercubeGraph = new HypercubeGraphGenerator(size);
                    prepareGlpkFile(size, hypercubeGraph);
                } else {
                    HypercubeGraphGenerator hypercubeGraph = new HypercubeGraphGenerator(size);
                    EdmondsKarp edmondsKarp = new EdmondsKarp(hypercubeGraph.getHybercubeGraph(), new Vertex(0), new Vertex((1 << size) - 1));
                    System.out.println("Size is: " + size);
//                System.out.println(hypercubeGraph.getHybercubeGraph().toString());
                    long startTime = System.nanoTime();
                    System.out.println("Max flow is: " + edmondsKarp.maxFlow());
                    long endTime = System.nanoTime();
                    System.out.println("Number of augmenting paths: " + edmondsKarp.getAugmentingPathCount());
                    System.out.println("Time: " + (endTime - startTime) / 1_000_000.0 + "ms");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Size must be a positive number");
            }
        }
    }

    static void prepareGlpkFile(int size, HypercubeGraphGenerator hypercubeGraph) {
        try (var writer = new PrintWriter(new FileWriter("hypercubeGLPK" + size + ".mod"))) {
            writer.print("data;\n");
            writer.print("param n := ");
            writer.print(1 << size);
            writer.print(";\n\nparam : E :   a :=");
            //System.out.println(hypercubeGraph.getHybercubeGraph().toString());
            var edges = hypercubeGraph.getHybercubeGraph().getEdgesList();
            for (GraphEdge e : edges) {
                writer.print("\n");
                writer.print(e.getStart().getValue() + 1);
                writer.print(" ");
                writer.print(e.getEnd().getValue() + 1);
                writer.print(" ");
                writer.print(e.getCapacity());
            }
            writer.print(";\n\nend;\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
