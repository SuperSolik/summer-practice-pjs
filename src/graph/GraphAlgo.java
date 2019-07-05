package graph;

import javax.swing.*;
import java.awt.*;

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
        Integer time = new Integer(0);
        for (Vertex v : graph.getVertices()) {
            if (v.getColor() == Color.WHITE) {
                visit(v, list, time);
            }
        }
    }

    private void visit(Vertex v, JTable list, Integer time) {
        for (Edge e : v.getEdges()) {
            Vertex temp = e.getDest();
            if (temp.getColor() == Color.WHITE) {
                visit(temp, list, time);
            }
        }
        v.setColor(Color.BLACK);
        //TODO записать time
    }
}
