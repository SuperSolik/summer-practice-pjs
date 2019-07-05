package graph;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;


public class GraphAlgo {
    private Vector<Vector<Color>> states;
    private Graph graph;
    private int time;

    public GraphAlgo() { }


    private void DFS1_step() {
        //TODO реализовать
    }

    private void DFS2_step() {
        //TODO реализовать
    }

    public Vector<Vector<Color>> DFS(Graph graph, JTable list) {
        states = new Vector<>();
        this.graph = graph;
        time = 0;

        states.add(createState());

        for(Vertex v : graph.getVertices()){
            if(v.getColor() == Color.WHITE)
                visit(v, list);
        }
        return states;
    }

    private void visit(Vertex v, JTable list) {
        v.setColor(Color.BLACK);
        states.add(createState());

        for (Edge e : v.getEdges()) {
            if(e.getDest().getColor() == Color.WHITE) {
                visit(e.getDest(), list);
            }
        }
        list.setValueAt("Vertex " + v.getName()+ ", timeout = " + time++, graph.getVertices().indexOf(v), 0);
    }

    private Vector<Color> createState(){
        Vector<Color> state = new Vector<>();
        graph.getVertices().forEach(el->state.add(el.getColor()));
        return state;
    }
}
