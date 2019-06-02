package graphs;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Graph {
    void addEdge(GraphEdge edge);

    void removeEdge(GraphEdge edge);

    Map<Vertex, List<GraphEdge>> getEdges();

    int getVerticesCount();

    default void addEdges(Collection<GraphEdge> edges) {
        for (var e : edges) {
            addEdge(e);
        }
    }
}
