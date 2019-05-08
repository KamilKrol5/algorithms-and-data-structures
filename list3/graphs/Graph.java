package graphs;

import java.util.List;
import java.util.Map;

public interface Graph {
    public void addEdge(GraphEdge edge);
    public void removeEdge(GraphEdge edge);
    public Map<Vertex, List<GraphEdge>> getEdges();
    public int getVerticesCount();
}
