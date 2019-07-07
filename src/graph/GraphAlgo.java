package graph;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;


public class GraphAlgo {
    private Vector<Vector<Color>> states;
    private Graph graph;

    public GraphAlgo() { }

    private Vector<Vector<Color>> DFS1_step(Graph graph, VerticesList list) {
        //красит только из белого в черный
        states = new Vector<>();
        this.graph = graph;
        states.add(createState());

        for(Vertex v : graph.getVertices()){
            if(v.getColor() == Color.WHITE)
                visit(v, list);
        }
        return states;
    }


    private Vector<Vector<Color>> DFS2_step(Graph graph, VerticesList list) {
        //TODO реализовать
        //красит уже в различные цвета для каждой компоненты
        return new Vector<>();
    }

    public Vector<Vector<Color>> Kosaraju(Graph graph, VerticesList list){
        graph.invert();
        var states1 = DFS1_step(graph, list);
        graph.invert();

        for(Vertex v : graph.getVertices()){
            v.setColor(Color.WHITE);
        }
        var states2 = DFS2_step(graph, list);

        Vector<Vector<Color>> all_states = new Vector<>();
        all_states.addAll(states1);
        all_states.addAll(states2);
        return all_states;
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

    private Vector<Color> createState(){
        Vector<Color> state = new Vector<>();
        graph.getVertices().forEach(el->state.add(el.getColor()));
        return state;
    }
}
