package graphs;

public class Vertex {
    private int value;

    public Vertex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex) {
            return ((Vertex) obj).value == this.value;
        } else return false;
    }

    @Override
    public int hashCode() {
        return this.value;
    }
}
