import algorithms.EdmondsKarp;
import graphs.HypercubeGraphGenerator;
import graphs.Vertex;

public class MainHybercube {

    public static void main(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("Missing argument. Valid argument is --size k");
        if (args[0].equals("--size")) {
            try {
                int size = Integer.parseInt(args[1]);
                if (size <= 0) System.out.println("Size must be a positive number");
                HypercubeGraphGenerator hypercubeGraph = new HypercubeGraphGenerator(size);
                EdmondsKarp edmondsKarp = new EdmondsKarp(hypercubeGraph.getHybercubeGraph(), new Vertex(0), new Vertex((1 << size) - 1));
                System.out.println("Size is: "+size);
                System.out.println(hypercubeGraph.getHybercubeGraph().toString());
                long startTime = System.nanoTime();
                System.out.println("Max flow is: " + edmondsKarp.maxFlow());
                long endTime = System.nanoTime();
                System.out.println("Time: "+ (endTime - startTime) / 1_000_000.0 + "ms");

            } catch (IllegalArgumentException e) {
                System.out.println("Size must be a positive number");
            }
        } else if (!args[1].equals("--glpk")) {
            String filename = args[1];
            //TODO implement
        }
    }
}
