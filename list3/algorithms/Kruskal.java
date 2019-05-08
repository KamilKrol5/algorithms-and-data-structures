package algorithms;

import graphs.Graph;
import graphs.GraphEdge;
import graphs.UndirectedGraph;
import graphs.Vertex;

import java.util.*;

public class Kruskal {
    private final UndirectedGraph graph;

    public Kruskal(UndirectedGraph graph) {
        this.graph = graph;
    }

    public UndirectedGraph findMST() {
        //creating ordered edges list
        List<GraphEdge> edgesOrdered = new ArrayList<GraphEdge>(graph.getEdgesList());
        edgesOrdered.sort(Comparator.comparingDouble(GraphEdge::getWeight));

        //creating map of vertices to their sets
        Map<Vertex, Integer> vertexSets = new HashMap<>();
        int i = 0;
        for (Vertex v : graph.getEdges().keySet()) {
            vertexSets.put(v,i);
            i++;
        }

        //creating empty graph - mst tree
        List<GraphEdge> mstEdges = new ArrayList<>(edgesOrdered.size());
        UndirectedGraph mst = new UndirectedGraph(new ArrayList<>(graph.getEdges().keySet()), mstEdges);

        while (!edgesOrdered.isEmpty()){
            GraphEdge current = edgesOrdered.get(0);
            edgesOrdered.remove(current);

            if ( ! vertexSets.get(current.getStart()).equals(vertexSets.get(current.getEnd()))) {
                mst.addEdge(current);
                union(vertexSets, vertexSets.get(current.getStart()), vertexSets.get(current.getEnd()));
            }
        }
        return mst;
    }

    private void union(Map<Vertex, Integer> vertexSets, Integer id1, Integer id2) {
        var filtered = vertexSets.entrySet().stream().filter( x -> x.getValue().equals(id2));
        filtered.forEach( x -> x.setValue(id1));
    }
}
