import algorithms.Dijkstra;
import algorithms.Kosaraju;
import graphs.DirectedGraph;
import graphs.GraphEdge;
import graphs.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KosarajuMain {
    private static DirectedGraph graph;
    private static Kosaraju kosaraju;
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        takeInput();
        print();
    }

    private static void takeInput() {
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
            Integer w = scanner.nextInt();
            edges.add(new GraphEdge(new Vertex(u), new Vertex(v), w.doubleValue()));
        }
        var tS = System.nanoTime();
        graph = new DirectedGraph(vertices, edges);
        kosaraju = new Kosaraju(graph);
        var tE = System.nanoTime();
        System.out.println();
        System.out.println("TIME: "+ (tE - tS));
    }

    private static void print() {
        StringBuilder builder = new StringBuilder();
        var stronglyConnectedComponents = kosaraju.stronglyConnectedComponents();
        int counter = 1;
        for (var scc : stronglyConnectedComponents) {
            builder.append("SCC ").append(counter).append(":").append(scc).append("\n");
            counter++;
        }
        System.out.println(builder.toString());
    }
}
// e log v
// e log v lub v2(list) prim
// e log v krusk
// e + v kosr