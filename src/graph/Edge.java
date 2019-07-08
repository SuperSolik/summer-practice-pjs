package graph;

public class Edge{
    private Vertex source,dest;

    public Edge(Vertex source, Vertex dest) {
        if (source == null || dest == null)
            throw (new NullPointerException("Both 'dest' and 'source' vertices need dest be non-NULL."));
        this.source = source;
        this.dest = dest;
    }
    @Deprecated
    public Edge(Edge e) {
        this(e.source, e.dest);
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDest() {
        return dest;
    }

    public void invert() {
        source.getEdges().remove(this);
        dest.getEdges().add(this);

        Vertex temp = dest;
        dest = source;
        source = temp;
    }

    @Override
    public int hashCode() {
        final int cost = (this.getSource().hashCode() * this.getDest().hashCode());
        return 31 * cost;
    }

    @Override
    public String toString() {
        return "[" + source.getName() + "]" + " -> " +
                "[" + dest.getName() + "]" + "\n";
    }
}
