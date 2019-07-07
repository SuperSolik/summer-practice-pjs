package graph;

import java.util.*;

public class Graph {
    /*
     Нам не нужна куча конструкторов
     Никто его не будет копировать или делать из готовых коллекций
     Мы либо создаем пустой граф при запуске
     Либо считываем его из файла
     Которые нам не понадобятся я пометил как Deprecated
     */
    private List<Vertex> vertices;
    private List<Edge> edges;

    private boolean isDirected = true;

    public Graph() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
    }
    public Graph(boolean isDirected) {
        this();
        this.isDirected = isDirected;
    }

    @Deprecated
    public Graph(Graph g) {
        isDirected = g.isDirected();
        for (Vertex v : g.getVertices())
            this.vertices.add(new Vertex(v));

        for (Vertex v : this.getVertices()) {
            this.edges.addAll(v.getEdges());
        }
    }
    @Deprecated
    public Graph(Collection<Vertex> vertices, Collection<Edge> edges) {
        this(true, vertices, edges);
    }
    @Deprecated
    public Graph(boolean isDirected, Collection<Vertex> vertices, Collection<Edge> edges) {
        this(isDirected);

        this.vertices.addAll(vertices);
        this.edges.addAll(edges);

        for (Edge e : edges) {
            final Vertex from = e.getSource();
            final Vertex to = e.getDest();

            if (!this.vertices.contains(from) || !this.vertices.contains(to))
                continue;

            from.addEdge(e);
            if (!this.isDirected) {
                Edge reciprical = new Edge(to, from);
                to.addEdge(reciprical);
                this.edges.add(reciprical);
            }
        }
    }

    public boolean createVertex(String name, float x, float y){
        if(getVertex(name) == null){
            Vertex v = new Vertex(name, x, y);
            vertices.add(v);
            return true;
        }
        return false;
    }

    public boolean createVertex(String name) {
        return createVertex(name, 0, 0);
    }

    public boolean createEdge(String src, String dest){
        Vertex from = getVertex(src);
        Vertex to = getVertex(dest);
        if(from.isConnected(to)) return false;
        Edge e = new Edge(from, to);
        from.addEdge(e);
        edges.add(e);
        return true;
    }

    public boolean createEdge(Vertex src, Vertex dest){
        if(src.isConnected(dest)) return false;
        Edge e = new Edge(src, dest);
        src.addEdge(e);
        edges.add(e);
        return true;
    }
    public boolean removeEdge(Vertex src, Vertex dest){
        if(!src.isConnected(dest))return false;
        Edge delEdge = src.getEdge(dest);
        src.getEdges().remove(delEdge);
        edges.remove(delEdge);
        return true;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Vertex getVertex(String name){
        for(Vertex v : vertices){
            if(v.getName().equals(name))
                return v;
        }
        return null;
    }

    public void invert(){
        for(Edge e : edges){
            e.invert();
        }
    }

    public void clear() {
        edges.clear();
        vertices.clear();
    }

    @Override
    public int hashCode() {
        int code = ((Boolean)this.isDirected).hashCode() + this.vertices.size() + this.edges.size();
        for (Vertex v : vertices)
            code *= v.hashCode();
        for (Edge e : edges)
            code *= e.hashCode();
        return 31 * code;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex v : vertices)
            builder.append(v.toString());
        return builder.toString();
    }
}