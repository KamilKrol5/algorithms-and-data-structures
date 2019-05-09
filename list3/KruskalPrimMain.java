import algorithms.Kruskal;
import algorithms.Prim;
import graphs.GraphEdge;
import graphs.UndirectedGraph;
import graphs.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Mode { KRUSKAL, PRIM }

public class KruskalPrimMain {
    private static UndirectedGraph graph;
    private static Kruskal kruskal;
    private static Prim prim;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Mode mode;
        if (args.length < 1) {
            System.err.println("No argument given. -k (Kruskal) or -p (Prim) are valid arguments.");
            return;
        }
        String modeString = args[0];
        if (modeString.equals("-k")) {
            mode = Mode.KRUSKAL;
        } else if (modeString.equals("-p")) {
            mode = Mode.PRIM;
        } else {
            System.err.println("Wrong argument given. -k (Kruskal) or -p (Prim) are valid arguments.");
            return;
        }
        takeInput(mode);
    }

    private static void takeInput(Mode mode) {
        System.out.print("\nn: ");
        int n = scanner.nextInt();
        System.out.print("\nm: ");
        int m = scanner.nextInt();

        List<Vertex> vertices = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            vertices.add(new Vertex(i));
        }

        List<GraphEdge> edges = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            System.out.print("\nsource: ");
            int u = scanner.nextInt();
            System.out.print("destination: ");
            int v = scanner.nextInt();
            System.out.print("weight: ");
            double w = scanner.nextDouble();
            edges.add(new GraphEdge(new Vertex(u), new Vertex(v), w));
        }

        graph = new UndirectedGraph(vertices, edges);
        UndirectedGraph mst;
        if (mode.equals(Mode.KRUSKAL)) {
            var tS = System.nanoTime();
            kruskal = new Kruskal(graph);
            mst = kruskal.findMST();
            var tE = System.nanoTime();
            System.out.println("TIME: " + (tE - tS));
        } else {
            var tS = System.nanoTime();
            prim = new Prim(graph);
            mst = prim.findMST();
            var tE = System.nanoTime();
            System.out.println("TIME: " + (tE - tS));
        }
        System.out.println();
        System.out.println(mst);
        Double weightSum = mst.getEdgesList().stream().mapToDouble(GraphEdge::getWeight).sum();
        System.out.println("Sum of weights: " + weightSum);
        System.out.println();
    }
}
