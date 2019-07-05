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
        int time = 0;
        for (Vertex v : graph.getVertices()) {
            if (v.getColor() == Color.WHITE) {
                visit(v, list, time);
            }
        }
        for(Vertex v : graph.getVertices()){
            System.out.println(v.getColor() == Color.BLACK);
        }
    }

    private void visit(Vertex v, JTable list, int time) {
        Stack<Vertex> stack = new Stack<>();
        Vertex current;
        stack.push(v);
        while(!stack.empty()) {
            current = stack.pop();
            if(current.getColor() == Color.WHITE){
                current.setColor(Color.BLACK);
            }

            for (Edge e : v.getEdges()) {
                Vertex temp = e.getDest();
                if (temp.getColor() == Color.WHITE) {
                    stack.push(temp);
                }
            }
        }
    }
}
