package algorithms;

import graphs.DirectedGraph;
import graphs.GraphEdge;
import graphs.Vertex;

import java.util.*;

public class Kosaraju {
    private final DirectedGraph graph;

    public Kosaraju(DirectedGraph graph) {
        this.graph = graph;
    }

    public List<Set<Vertex>> stronglyConnectedComponents() {
        List<Set<Vertex>> result = new ArrayList<>();
        Set<Vertex> unvisited = new HashSet<>();
        unvisited.addAll(graph.getEdges().keySet());

        Stack<Vertex> stack = new Stack<>();

        for (Vertex v : graph.getEdges().keySet()) {
            if (unvisited.contains(v)) {
                DFSstack(v, unvisited, stack);
            }
        }

        var graphTransposed = graph.transpose();
        unvisited.clear();
        unvisited.addAll(graphTransposed.getEdges().keySet());

        while (!stack.empty()) {
            var v = stack.pop();
            if (!unvisited.contains(v))
                continue;
            Set<Vertex> scc = new HashSet<>();
            DFSBuild(v, unvisited, scc, graphTransposed);
            result.add(scc);
        }
        return result;

    }

    private void DFSstack(Vertex v, Set<Vertex> unvisited, Stack<Vertex> stack) {
        unvisited.remove(v);
        for (GraphEdge e : graph.getEdges().get(v)) {
            var u = e.getEnd();
            if (u.equals(v)) {
                continue;
                //u = e.getStart();
            }
            if (unvisited.contains(u)) {
                DFSstack(u, unvisited, stack);
            }
            stack.push(u);
        }
    }

    private void DFSBuild(Vertex v, Set<Vertex> unvisited, Set<Vertex> resultSet, DirectedGraph graph) {
        unvisited.remove(v);
        resultSet.add(v);
        for (GraphEdge e : graph.getEdges().get(v)) {
            var u = e.getEnd();
            if (u.equals(v)) {
                u = e.getStart();
            }
            if (unvisited.contains(u)) {
                DFSBuild(u, unvisited, resultSet, graph);
            }
        }
    }
}
