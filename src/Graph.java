import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Graph{

    private List<Vertex> allVertices = new ArrayList<Vertex>();
    private List<Edge> allEdges = new ArrayList<Edge>();

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
        this(TYPE.UNDIRECTED, vertices, edges);
    }

    public Graph(TYPE type, Collection<Vertex> vertices, Collection<Edge> edges) {
        this(type);

        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Edge e : edges) {
            final Vertex from = e.from;
            final Vertex to = e.to;

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

    public static class Vertex{

        private String value = null;
        private List<Edge> edges = new ArrayList<Edge>();

        public Vertex(String value) {
            this.value = value;
        }

        public Vertex(Vertex vertex) {
            this(vertex.value);

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
                if (e.to.equals(v))
                    return e;
            }
            return null;
        }

        public boolean pathTo(Vertex v) {
            for (Edge e : edges) {
                if (e.to.equals(v))
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

    public static class Edge{
        private Vertex from = null;
        private Vertex to = null;

        public Edge(Vertex from, Vertex to) {
            if (from == null || to == null)
                throw (new NullPointerException("Both 'to' and 'from' vertices need to be non-NULL."));

            this.from = from;
            this.to = to;
        }

        public Edge(Edge e) {
            this(e.from, e.to);
        }


        public Vertex getFromVertex() {
            return from;
        }

        public Vertex getToVertex() {
            return to;
        }

        @Override
        public int hashCode() {
            final int cost = (this.getFromVertex().hashCode() * this.getToVertex().hashCode());
            return 31 * cost;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[ ").append(from.value).append("]").append(" -> ")
                    .append("[ ").append(to.value).append("]").append(" = ").append("\n");
            return builder.toString();
        }
    }
}