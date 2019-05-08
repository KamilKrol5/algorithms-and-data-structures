package algorithms;

import graphs.DirectedGraph;
import graphs.GraphEdge;
import graphs.Vertex;
import structures.PriorityQueue;

import java.util.*;

public class Dijkstra {
    private DirectedGraph graph;

    public Dijkstra(DirectedGraph graph) {
        this.graph = graph;
    }

    public Map<Vertex, List<Vertex>> shortestPath(Vertex source) {
        Map<Vertex, Integer> distances = new HashMap<>(graph.getVerticesCount());
        HashMap<Vertex, Vertex> predecessors = new HashMap<>(graph.getVerticesCount());
        PriorityQueue<Vertex, Integer> queue = new PriorityQueue<>();

        for (Vertex v : graph.getEdges().keySet()) {
            distances.put(v, Integer.MAX_VALUE-1);
        }

        distances.replace(source, 0);

        for (Vertex v : graph.getEdges().keySet()) {
            queue.insert(v, distances.get(v));
        }

        while (!queue.empty()) {
            Vertex v = queue.pop();
            System.out.println(v);
            if (distances.get(v).equals(Integer.MAX_VALUE-1))
                break;

            for (GraphEdge edge : graph.getEdges().get(v)) {
                var edgeEnd = edge.getEnd();
                if (distances.get(v) + edge.getWeight() < distances.get(edgeEnd)) {
                    distances.replace(edgeEnd, distances.get(v) + edge.getWeight().intValue());
                    predecessors.put(edgeEnd, v);
                    queue.priority(edgeEnd, distances.get(edgeEnd));
                }
            }
        }

        Map<Vertex, List<Vertex>> result = new HashMap<>(graph.getVerticesCount());
        for (Vertex vertex : distances.keySet()) {
            List<Vertex> path = new ArrayList<>();
            result.put(vertex, path);
            if (predecessors.get(vertex) != null || vertex.equals(source)) { // check reachability of a vertex
                while (vertex != null) {
                    path.add(vertex);
                    vertex = predecessors.get(vertex);
                }
            }
            Collections.reverse(path);
        }
        return result;
    }

}
