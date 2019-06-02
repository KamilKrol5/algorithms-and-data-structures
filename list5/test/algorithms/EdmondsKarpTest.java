package algorithms;

import graphs.DirectedGraph;
import graphs.GraphEdge;
import graphs.Vertex;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

class EdmondsKarpTest {

    @org.junit.jupiter.api.Test
    void maxFlow() {
        List<Vertex> vertices = new ArrayList<>(List.of(
                new Vertex(0), new Vertex(1), new Vertex(2), new Vertex(3), new Vertex(4), new Vertex(5)));
        List<GraphEdge> edges = new ArrayList<>(List.of(
                new GraphEdge(vertices.get(0), vertices.get(1), 16, 0),
                new GraphEdge(vertices.get(0), vertices.get(2), 13, 0),
                new GraphEdge(vertices.get(1), vertices.get(2), 6, 0),
                new GraphEdge(vertices.get(1), vertices.get(3), 12, 0),
                new GraphEdge(vertices.get(2), vertices.get(4), 14, 0),
                new GraphEdge(vertices.get(3), vertices.get(2), 9, 0),
                new GraphEdge(vertices.get(4), vertices.get(3), 7, 0),
                new GraphEdge(vertices.get(3), vertices.get(5), 20, 0),
                new GraphEdge(vertices.get(4), vertices.get(5), 4, 0)
        ));
        DirectedGraph graph = new DirectedGraph(vertices, edges);

        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, vertices.get(0), vertices.get(5));
        var res = edmondsKarp.maxFlow();
        System.err.println(res);
        Assertions.assertEquals(res, 23);
    }
}