package graph;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;


public class GraphAlgo {
    private Vector<Vector<Color>> states;
    private Graph graph;
    private int time;
    private int index;

    public GraphAlgo() { }

    private Vector<Vector<Color>> DFS1_step(Graph graph, JTable list) {
        //красит только из белого в черный
        states = new Vector<>();
        this.graph = graph;
        time = 0;
        index = 0;
        states.add(createState());

        for(Vertex v : graph.getVertices()){
            if(v.getColor() == Color.WHITE)
                visit(v, list);
        }
        return states;
    }


    private Vector<Vector<Color>> DFS2_step(Graph graph, JTable list) {
        //TODO реализовать
        //красит уже в различные цвета для каждой компоненты
        return null;
    }

    public Vector<Vector<Color>> Kosaraju(Graph graph, JTable list){
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

    private void visit(Vertex v, JTable list) {
        int ind = index++;
        v.setColor(Color.BLACK);
        states.add(createState());
        for (Edge e : v.getEdges()) {
            if(e.getDest().getColor() == Color.WHITE) {
                visit(e.getDest(), list);
            }
        }
        list.setValueAt("Vertex " + v.getName()+ ", timeout = " + time++, ind, 0); //тут поменяем в нужный формат записи, мб просто оставим имя
    }

    private Vector<Color> createState(){
        Vector<Color> state = new Vector<>();
        graph.getVertices().forEach(el->state.add(el.getColor()));
        return state;
    }
}
