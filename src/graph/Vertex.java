package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private float x,y;
    private String name;
    private List<Edge> edges = new ArrayList<>();

    public Vertex(String name) {
        this.name = name;
    }

    public Vertex(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    @Deprecated
    public Vertex(Vertex vertex) {
        this(vertex.name, vertex.x, vertex.y);
        this.edges.addAll(vertex.edges);
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Edge getEdge(Vertex v) {
        for (Edge e : edges) {
            if (e.getDest().equals(v))
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

    public boolean isConnected(Vertex v) {
        for (Edge e : edges) {
            if (e.getDest().equals(v))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int code = this.name.hashCode() + this.edges.size();
        return 31 * code;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Value=").append(name)
                .append(" x: ").append(this.x)
                .append(" y: ").append(this.y)
                .append("\n");
        for (Edge e : edges)
            builder.append("\t").append(e.toString());
        return builder.toString();
    }
}
