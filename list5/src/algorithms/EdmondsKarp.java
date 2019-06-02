package algorithms;

import graphs.DirectedGraph;
import graphs.GraphEdge;
import graphs.Vertex;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class EdmondsKarp {

    private final DirectedGraph graph;
    private final Vertex source;
    private final Vertex destination;

    public EdmondsKarp(DirectedGraph graph, Vertex source, Vertex destination) {
        this.graph = graph;
        var set = graph.getEdges().keySet();
        if (!set.contains(source) || !set.contains(destination)) {
            throw new RuntimeException("Given source or destination is not present in given graph.");
        }
        this.source = source;
        this.destination = destination;

        for (var e : this.graph.getEdgesList()) {
            e.setFlow(0);
        }
        createReverseEdges();
    }

    private void createReverseEdges() {
        for (var edge : graph.getEdgesList()) {
            var reversedEdge = new GraphEdge(edge.getEnd(), edge.getStart(), 0, 0, edge);
            edge.setReversedEdge(reversedEdge);
            graph.addEdge(reversedEdge);
        }
    }

    public Integer maxFlow() {
        Integer maxFlow = 0;
        Queue<Vertex> queue = new ArrayDeque<>();
        Map<Vertex, GraphEdge> predessesors = new HashMap<>(graph.getVerticesCount());
        do {
            queue.clear();
            predessesors.clear();
            queue.add(source);

            while (!queue.isEmpty()) {
                Vertex current = queue.remove();
                for (GraphEdge e : graph.getEdges().get(current)) {
                    if (predessesors.get(e.getEnd()) == null && e.getEnd() != source && e.getCapacity() > e.getFlow()) {
                        predessesors.put(e.getEnd(), e);
                        queue.add(e.getEnd());
                    }
                    GraphEdge reversedE = e.getReversedEdge();
                    if (predessesors.get(reversedE.getEnd()) == null && reversedE.getEnd() != source && reversedE.getCapacity() > reversedE.getFlow()) {
                        predessesors.put(reversedE.getEnd(), reversedE);
                        queue.add(reversedE.getEnd());
                    }
                }
            }

            if (predessesors.get(destination) == null) {
//                We found an augmenting path. See how much flow we can send
                var deltaFlow = Integer.MAX_VALUE - 1;
                for (var e = predessesors.get(destination); e != null; e = predessesors.get(e.getStart())) {
                    deltaFlow = Math.min(deltaFlow, e.getCapacity() - e.getFlow());
                }
//                And update edges by that amount
                for (var e = predessesors.get(destination); e != null; e = predessesors.get(e.getStart())) {
                    e.setFlow(e.getFlow() + deltaFlow);
                    e.getReversedEdge().setFlow(e.getReversedEdge().getFlow() - deltaFlow);
                    maxFlow = maxFlow + deltaFlow;
                }
            }
        } while (predessesors.get(destination) != null);

        return maxFlow;
    }
}
