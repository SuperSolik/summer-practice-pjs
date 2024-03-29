package graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;


public class GraphAlgo {
    private ArrayList<ArrayList<Color>> states;
    private Graph graph;

    public GraphAlgo() {
        states = new ArrayList<>(); //иначе отдельный тест dfs крашится + так логичнее чем каждый рах перевыделять в алгосе
    }

    public void DFS1_step(Graph graph, VerticesList list) {
        //красит только из белого в черный
        this.graph = graph;
        states.add(createState());

        for(Vertex v : graph.getVertices()){
            if(v.getColor() == Color.WHITE)
                visit(v, list);
        }
    }


    public void DFS2_step(Graph graph, VerticesList list) {
        states.add(createState());
        this.graph = graph;

        ArrayList<Color> usedColors = new ArrayList<>();
        Supplier<Color> randomColor = () -> {
            Random rand = new Random();

            do {
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();

                Color color = new Color(r, g, b);
                if (!usedColors.contains(color)) {
                    usedColors.add(color);
                    return color;
                }
            } while (true);
        };

        while (list.hasNext()) {
            Color color = randomColor.get();

            Vertex v = list.getNext();
            if (v.getColor() == Color.WHITE) {
                visit(v, color, list);
            }
        }
    }

    public ArrayList<ArrayList<Color>> Kosaraju(Graph graph, VerticesList list){
        states.clear();
        list.clear();

        for(Vertex v : graph.getVertices()){
            v.setColor(Color.WHITE);
        }

        graph.invert();
        DFS1_step(graph, list);
        graph.invert();

        for(Vertex v : graph.getVertices()){
            v.setColor(Color.WHITE);
        }
        DFS2_step(graph, list);

        return states;
    }


    private void visit(Vertex v, Color color, VerticesList list) {
        v.setColor(color);
        list.remove(v);

        states.add(createState());
        for (Edge e : v.getEdges()) {
            if(e.getDest().getColor() == Color.WHITE) {
                visit(e.getDest(), color, list);
            }
        }
    }

    private void visit(Vertex v, VerticesList list) {
        v.setColor(Color.BLACK);
        states.add(createState());
        for (Edge e : v.getEdges()) {
            if(e.getDest().getColor() == Color.WHITE) {
                visit(e.getDest(), list);
            }
        }

        list.append(v);
    }

    private ArrayList<Color> createState(){
        ArrayList<Color> state = new ArrayList<>();
        graph.getVertices().forEach(el->state.add(el.getColor()));
        return state;
    }
}
