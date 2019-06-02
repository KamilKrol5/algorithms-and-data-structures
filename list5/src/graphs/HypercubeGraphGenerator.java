package graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HypercubeGraphGenerator {

    private int size;
    private Random random;
    private DirectedGraph directedGraph;

    public HypercubeGraphGenerator(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size must be a positive number");
        this.size = size;
        random = new Random();
        int verticesNumber = 1 << size;
        List<Vertex> vertices = new ArrayList<>(verticesNumber);
        for (int i = 0; i < verticesNumber; i++) {
            vertices.add(new Vertex(i));
        }
        List<GraphEdge> edges = new ArrayList<>(verticesNumber * size / 2);
        int neighbour;
        int start, end;
        for (var v : vertices) {
            int powerOfTwo = 1;
            for (int y = 0; y < size; y++) {
                //choosing neighbour
                neighbour = v.getValue() ^ powerOfTwo; // ^ is xor

                //choosing direction of the edge being created
                if (v.getValue() < neighbour) { //if in order to avoid doubled edges
                    start = v.getValue();
                    end = neighbour;

                    //choosing capacity
                    int hWStart = computeHammingWeight(start);
                    int hWEnd = computeHammingWeight(end);
                    int max = Math.max(hWEnd, computeNumberOfZeros(hWStart));

                    //create and add new edge
                    GraphEdge newEdge = new GraphEdge(vertices.get(start), vertices.get(end), random.nextInt(1 << max) + 1, 0);
                    edges.add(newEdge);

                }
                powerOfTwo = powerOfTwo << 1;
            }
        }
        this.directedGraph = new DirectedGraph(vertices, edges);
    }

    public DirectedGraph getHybercubeGraph() {
        return directedGraph;
    }

    private int computeHammingWeight(int number) {
        int count = 0;
        while (number != 0) {
            if (number % 2 == 1) {
                count++;
            }
            number = number >> 1;
        }
        return count;
    }

    private int computeNumberOfZeros(int hammingWeight) {
        return size - hammingWeight;
    }
}
