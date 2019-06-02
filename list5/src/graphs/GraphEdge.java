package graphs;

public class GraphEdge {
    private Integer flow;
    private final Integer capacity;
    private Vertex start, end;
    private GraphEdge reversedEdge;

    public GraphEdge(Vertex start, Vertex end, Integer capacity, Integer flow, GraphEdge reversedEdge) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.flow = flow;
        this.reversedEdge = reversedEdge;
    }

    public GraphEdge(Vertex start, Vertex end, Integer capacity, Integer flow) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.flow = flow;
        this.reversedEdge = null;
    }

    public Vertex getEnd() {
        return end;
    }

    public Vertex getStart() {
        return start;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getFlow() { return flow; }

    public void setFlow(Integer flow) { this.flow = flow; }

    public void setReversedEdge(GraphEdge reversedEdge) { this.reversedEdge = reversedEdge; }

    public GraphEdge getReversedEdge() { return reversedEdge; }

    @Override
    public String toString() {
        return String.format("%s -(%d)-> %s", start, capacity, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraphEdge) {
            return ((GraphEdge) obj).start == this.start && ((GraphEdge) obj).end == this.end && ((GraphEdge) obj).capacity.equals(this.capacity);
        } else return false;
    }

    @Override
    public int hashCode() {
        return start.hashCode() * 1000 +end.hashCode();
    }

}
