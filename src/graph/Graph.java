package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Graph{
    /*
     Нам не нужна куча конструкторов
     Мы либо создаем пустой граф при запуске
     Либо считываем его из файла
     Никто его не будет копировать или делать из готовых коллекций
     Которые нам не понадобятся я пометил как Deprecated
     */
    private List<Vertex> vertices;
    private List<Edge> edges;

//    public enum TYPE {
//        DIRECTED, UNDIRECTED
//    }

//    private TYPE type = TYPE.DIRECTED;
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
        //TODO добавление в граф вершины с именем и координатами
        // возвращает false если есть нода с таким именем
        return false;
    }
    public boolean createEdge(String src, String dest){
        //TODO соединяет две вершины с данными именами (для удобного считывания с файла)
        return false;
    }
    public boolean createEdge(Vertex src, Vertex dest){
        //TODO соединяет по ссылкам (для GUI)
        return false;
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