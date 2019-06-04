import algorithms.EdmondsKarp;
import graphs.BipartiteGraphGenerator;
import graphs.DirectedGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
                    System.out.println("Size: " + size + ", degree: " + degree);
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
        try (var writer = new PrintWriter(new FileWriter("comparingMaxMatchingWithGLPK-results.txt"))) {
            for (int size = 1; size <= 16; size++) {
                for (int degree = 1; degree <= size; degree++) {
                    System.out.println("Begin for size = " + size + " and degree = " + degree);
                    BipartiteGraphGenerator generator = new BipartiteGraphGenerator(size, degree);
                    final int size2 = size;
                    final int degree2 = degree;
                    Runnable myProgram = () -> {
                        System.err.println("MyProgram thread started for k = " + size2 + " and i = " + degree2);
                        EdmondsKarp edmondsKarp = new EdmondsKarp(generator.getCopyOfBipartiteGraph(), generator.getStart(), generator.getEnd());
                        long start = System.nanoTime();
                        Integer res = edmondsKarp.maxFlow();
                        long end = System.nanoTime();
                        synchronized (writer) {
                            writer.append("for k=").append(String.valueOf(size2)).append(" and i=").append(String.valueOf(degree2)).append(" max matching size is: ");
                            writer.append(res.toString());
                            writer.append(" (time: ").append(String.valueOf((end - start) / 1_000_000)).append("ms)\n");
                        }
                    };
                    Runnable glpkRunnable = () -> {
                        System.err.println("GLPK thread started for k = " + size2 + " and i = " + degree2);
                        MainBipartiteMaxMatching.prepareGlpkFile(size2, degree2, generator);
                        Process process = null;
                        try {
                            process = new ProcessBuilder("glpsol.exe", "--model", "maxflow.mod", "--data", "maxMatchingGLPK-size-" + size2 + "-deg" + degree2 + ".mod").start();
                            InputStream is = process.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader br = new BufferedReader(isr);
                            String line;

                            synchronized (writer) {
                                writer.append("\nfor k=").append(String.valueOf(size2)).append(" and for i=").append(String.valueOf(size2)).append("max matching size according to GLPK is: \n");
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
