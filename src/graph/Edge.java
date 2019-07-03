package graph;

public class Edge{
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

    void invert(){
        Vertex temp = new Vertex(to);
        to = new Vertex(from);
        from = new Vertex(temp);
    }

    @Override
    public int hashCode() {
        final int cost = (this.getFromVertex().hashCode() * this.getToVertex().hashCode());
        return 31 * cost;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ").append(from.getValue()).append("]").append(" -> ")
                .append("[ ").append(to.getValue()).append("]").append(" = ").append("\n");
        return builder.toString();
    }
}
