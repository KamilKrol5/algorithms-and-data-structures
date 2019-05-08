package graphs;

public class GraphEdge {
    private Double weight;
    private Vertex start, end;

    public GraphEdge(Vertex start, Vertex end, Double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Vertex getEnd() {
        return end;
    }

    public Vertex getStart() {
        return start;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%s -(%f)-> %s", start, weight, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraphEdge) {
            return ((GraphEdge) obj).start == this.start && ((GraphEdge) obj).end == this.end && ((GraphEdge) obj).weight == this.weight;
        } else return false;
    }

    @Override
    public int hashCode() {
        return start.hashCode() * 1000 +end.hashCode();
    }
}
