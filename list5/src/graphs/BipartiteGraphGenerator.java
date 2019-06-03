package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BipartiteGraphGenerator {

    private int size;
    private int degree;
    private Vertex start;
    private Vertex end;
    private DirectedGraph directedGraph;

    public BipartiteGraphGenerator(int size, int degree) {
        this(size, degree, 1);
    }

    public BipartiteGraphGenerator(int size, int degree, int edgeInitialCapacity) {
        if (size <= 0) throw new IllegalArgumentException("Size must be a positive number");
        if (degree < 0) throw new IllegalArgumentException("Degree must be a positive number");
        if (degree > size) throw new IllegalArgumentException("Degree cannot be larger than size");
        this.size = size;
        this.degree = degree;
        int verticesInSubgraphCount = 1 << size;

        //creating v1 and v2
        List<Vertex> v1 = new ArrayList<>(verticesInSubgraphCount);
        List<Vertex> v2 = new ArrayList<>(verticesInSubgraphCount);
        //ints list is made to provide uniform distribution
        List<Integer> ints = new ArrayList<>(verticesInSubgraphCount);
        for (int i = 0; i < verticesInSubgraphCount; i++) {
            v1.add(new Vertex(i));
            v2.add(new Vertex(i + verticesInSubgraphCount));
            ints.add(i);
        }

        //creating and choosing edges
        List<GraphEdge> edges = new ArrayList<>(verticesInSubgraphCount * degree);
        for (Vertex v : v1) {
            Collections.shuffle(ints);
            for (int j = 0; j < degree; j++) {
                var edge = new GraphEdge(v, v2.get(ints.get(j)), edgeInitialCapacity, 0);
                edges.add(edge);
            }
        }

        //create additional source and sink vertices
        start = new Vertex(-1);
        end = new Vertex(-2);

        //connect start and end with v1 and v2 respectively
        for (Vertex v1v : v1) {
            edges.add(new GraphEdge(start, v1v, edgeInitialCapacity, 0));
        }
        for (Vertex v2v : v2) {
            edges.add(new GraphEdge(v2v, end, edgeInitialCapacity, 0));
        }

        //move all vertices to v1
        v1.add(start);
        v1.add(end);
        v1.addAll(v2);
        this.directedGraph = new DirectedGraph(v1, edges);

    }

    public DirectedGraph getBipartiteGraph() {
        return directedGraph;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }
}
