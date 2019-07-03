package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex{

    private float x;
    private float y;

    private String value = null;
    private List<Edge> edges = new ArrayList<>();

    public Vertex(String value) {
        this.value = value;
    }

    public Vertex(String value, float x, float y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Vertex(Vertex vertex) {
        this(vertex.value, vertex.x, vertex.y);

        this.edges.addAll(vertex.edges);
    }

    public String getValue() {
        return value;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Edge getEdge(Vertex v) {
        for (Edge e : edges) {
            if (e.getToVertex().equals(v))
                return e;
        }
        return null;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean pathTo(Vertex v) {
        for (Edge e : edges) {
            if (e.getToVertex().equals(v))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int code = this.value.hashCode() + this.edges.size();
        return 31 * code;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Value=").append(value).append("\n");
        for (Edge e : edges)
            builder.append("\t").append(e.toString());
        return builder.toString();
    }
}
