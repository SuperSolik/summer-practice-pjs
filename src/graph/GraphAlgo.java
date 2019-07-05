package graph;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class GraphAlgo {

    public GraphAlgo() {
    }

    private void DFS1_step() {
        //TODO реализовать
    }

    private void DFS2_step() {
        //TODO реализовать
    }

    public void DFS(Graph graph, JTable list) {
        for(Vertex v : graph.getVertices()){
            if(v.getColor() == Color.WHITE)
                visit(v);
        }
    }

    private void visit(Vertex v) {
        v.setColor(Color.BLACK);
        //TODO здесь создать состояние
        for (Edge e : v.getEdges()) {
            System.out.println(e);
            if(e.getDest().getColor() == Color.WHITE)
                visit(e.getDest());
        }
        //TODO здесь время выхода
    }
}
