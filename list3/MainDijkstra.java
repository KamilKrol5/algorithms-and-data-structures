import algorithms.Dijkstra;
import graphs.DirectedGraph;
import graphs.Graph;
import graphs.GraphEdge;
import graphs.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainDijkstra {
        private static DirectedGraph graph;
        private static Dijkstra dijkstra;
        private static Scanner scanner = new Scanner(System.in);
        private static Vertex start;


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

            graph = new DirectedGraph(vertices, edges);
            dijkstra = new Dijkstra(graph);

            System.out.print("\nStart vertex: ");
            start = new Vertex(scanner.nextInt());
            System.out.println();
        }

        private static void print() {
            StringBuilder builder = new StringBuilder();
            var paths = dijkstra.shortestPath(start);
            for (var entry : paths.entrySet()) {
                builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            System.out.println(builder.toString());
    }
}
