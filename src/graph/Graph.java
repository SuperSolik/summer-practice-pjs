package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Graph{

    private List<Vertex> allVertices = new ArrayList<>();
    private List<Edge> allEdges = new ArrayList<>();

    public enum TYPE {
        DIRECTED, UNDIRECTED
    }

    private TYPE type = TYPE.DIRECTED;

    public Graph() { }

    public Graph(TYPE type) {
        this.type = type;
    }

    public Graph(Graph g) {
        type = g.getType();
        for (Vertex v : g.getVertices())
            this.allVertices.add(new Vertex(v));

        for (Vertex v : this.getVertices()) {
            for (Edge e : v.getEdges()) {
                this.allEdges.add(e);
            }
        }
    }

    public Graph(Collection<Vertex> vertices, Collection<Edge> edges) {
        this(TYPE.DIRECTED, vertices, edges);
    }

    public Graph(TYPE type, Collection<Vertex> vertices, Collection<Edge> edges) {
        this(type);

        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Edge e : edges) {
            final Vertex from = e.getFromVertex();
            final Vertex to = e.getToVertex();

            if (!this.allVertices.contains(from) || !this.allVertices.contains(to))
                continue;

            from.addEdge(e);
            if (this.type == TYPE.UNDIRECTED) {
                Edge reciprical = new Edge(to, from);
                to.addEdge(reciprical);
                this.allEdges.add(reciprical);
            }
        }
    }

    public TYPE getType() {
        return type;
    }

    public List<Vertex> getVertices() {
        return allVertices;
    }

    public List<Edge> getEdges() {
        return allEdges;
    }

    @Override
    public int hashCode() {
        int code = this.type.hashCode() + this.allVertices.size() + this.allEdges.size();
        for (Vertex v : allVertices)
            code *= v.hashCode();
        for (Edge e : allEdges)
            code *= e.hashCode();
        return 31 * code;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex v : allVertices)
            builder.append(v.toString());
        return builder.toString();
    }
}