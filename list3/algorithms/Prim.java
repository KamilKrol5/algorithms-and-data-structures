package algorithms;

import graphs.GraphEdge;
import graphs.UndirectedGraph;
import graphs.Vertex;
import structures.PriorityQueue;

import java.util.*;

public class Prim {
    private final UndirectedGraph graph;

    public Prim(UndirectedGraph graph) {
        this.graph = graph;
    }

    public UndirectedGraph findMST() {
        HashMap<Vertex, Vertex> predecessors = new HashMap<>(graph.getVerticesCount());
        HashMap<Vertex, Double> keys = new HashMap<>(graph.getVerticesCount());
        Set<Vertex> unvisited = new HashSet<>(graph.getVerticesCount());
        Vertex start = graph.getEdges().keySet().iterator().next();

        for (Vertex v : graph.getEdges().keySet()) {
            predecessors.put(v, null);
            keys.put(v, Double.MAX_VALUE - 1);
            unvisited.add(v);
        }

        keys.put(start, 0.0);

        PriorityQueue<Vertex, Double> queue = new PriorityQueue<>();
        for (Vertex v : graph.getEdges().keySet()) {
            queue.insert(v, keys.get(v));
        }

        while (!queue.empty()) {
            Vertex u = queue.pop();
            unvisited.remove(u);
            for (GraphEdge e : graph.getEdges().get(u)) {
                var v = e.getEnd();
                if (v.equals(u)) {
                    v = e.getStart();
                }
                if (unvisited.contains(v) && e.getWeight() < keys.get(v)) {
                    predecessors.put(v, u);
                    keys.put(v, e.getWeight());
                    queue.priority(v, e.getWeight());
                }

            }
        }

        List<GraphEdge> mstEdges = new ArrayList<>();
        UndirectedGraph mst = new UndirectedGraph(new ArrayList<>(graph.getEdges().keySet()), mstEdges);
        for (var entry : predecessors.entrySet()) {
            mst.addEdge(new GraphEdge(entry.getValue(), entry.getKey(), keys.get(entry.getKey())));
        }

        return mst;
    }
}
