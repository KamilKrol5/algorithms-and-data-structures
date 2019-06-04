import algorithms.EdmondsKarp;
import graphs.DirectedGraph;
import graphs.HypercubeGraphGenerator;
import graphs.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


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

//    public static void main(String[] args) throws InterruptedException {
//        compareWithGLPK();
//    }

    public static void compareWithGLPK() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(5);
        List<Future<?>> futures = new ArrayList<>(32);
        try (var writer = new PrintWriter(new FileWriter("comparingWithGLPK-results.txt"))) {
            for (int size = 1; size <= 16; size++) {
                HypercubeGraphGenerator hypercubeGraphGenerator = new HypercubeGraphGenerator(size);
                DirectedGraph hypercubeGraph = hypercubeGraphGenerator.getHybercubeGraph();
                final int sizee = size;
                Runnable myProgram = () -> {
                    EdmondsKarp edmondsKarp = new EdmondsKarp(hypercubeGraphGenerator.getCopyOfHypercubeGraph(), new Vertex(0), new Vertex((1 << sizee) - 1));
                    long start = System.nanoTime();
                    Integer res = edmondsKarp.maxFlow();
                    long end = System.nanoTime();
                    synchronized (writer) {
                        writer.append("for k=" + sizee + " max flow is: ");
                        writer.append(res.toString());
                        writer.append(" (time: " + (end-start) / 1_000_000 + "ms)\n");
                    }
                };
                Runnable glpkRunnable = () -> {
                    MainHybercube.prepareGlpkFile(sizee, hypercubeGraphGenerator);
                    Process process = null;
                    try {
                        process = new ProcessBuilder("glpsol.exe", "--model", "maxflow.mod", "--data", "hypercubeGLPK" + sizee + ".mod").start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String line;

                        synchronized (writer) {
                            writer.append("\nfor k=" + sizee + " max flow according to GLPK is: \n");
                            while ((line = br.readLine()) != null) {
                                writer.append(line);
                                writer.append("\n");
                            }
                            writer.append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };

                futures.add(executor.submit(myProgram));
                futures.add(executor.submit(glpkRunnable));
            }
            for (Future<?> f : futures) {
                try {
                    f.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        executor.awaitTermination(-1, TimeUnit.DAYS);
    }

}
