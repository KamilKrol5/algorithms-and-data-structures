package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UndirectedGraph implements Graph{
    private Map<Vertex, List<GraphEdge>> edges;
    private List<GraphEdge> edgesList;

    public UndirectedGraph(List<Vertex> vertices, List<GraphEdge> edges) {
        this.edgesList = edges;
        this.edges = new HashMap<>(vertices.size());
        for (int i = 0; i < vertices.size(); i++) {
            this.edges.put(vertices.get(i), new ArrayList<>());
        }
        for (GraphEdge e : edges) {
            if (!this.edges.get(e.getStart()).contains(e) && !this.edges.get(e.getEnd()).contains(e)) {
                this.edges.get(e.getStart()).add(e);
                this.edges.get(e.getEnd()).add(e);
            }
        }
    }

    @Override
    public void addEdge(GraphEdge edge) {
        if (edges.containsKey(edge.getStart()) && edges.containsKey(edge.getEnd())) {
            edges.get(edge.getStart()).add(edge);
            edges.get(edge.getEnd()).add(edge);
            edgesList.add(edge);
        } else {
            System.err.println("No vertex matching start or end in given vertex");
        }
    }

    @Override
    public void removeEdge(GraphEdge edge) {
        for (List<GraphEdge> neighbours: edges.values()) {
            for (GraphEdge edge1 : neighbours) {
                if (edge1.equals(edge)) {
                    neighbours.remove(edge);
                    break;
                }
            }
        }
    }

    @Override
    public Map<Vertex, List<GraphEdge>> getEdges() {
        return edges;
    }

    @Override
    public int getVerticesCount() {
        return edges.size();
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<Vertex, List<GraphEdge>> entry : edges.entrySet()) {
            b.append(entry.getKey()).append(": ");
            for (GraphEdge e : entry.getValue()) {
                b.append(e).append(" ");
            }
            b.append("\n");
        }
        b.append("\n");
        return b.toString();
    }

    public List<GraphEdge> getEdgesList() {
        return edgesList;
    }
}

