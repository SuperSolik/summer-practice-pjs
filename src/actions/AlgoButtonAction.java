package actions;

import graph.Graph;
import graph.GraphAlgo;
import graph.VerticesList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AlgoButtonAction extends AbstractAction {
    private Graph graph;
    private GraphAlgo algo;
    private VerticesList list;
    private ArrayList<ArrayList<Color>> stages;

    public AlgoButtonAction(Graph graph, GraphAlgo algo,
                            ArrayList<ArrayList<Color>> stages,
                            VerticesList list) {
        this.graph = graph;
        this.algo = algo;
        this.stages = stages;
        this.list = list;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        stages.clear();
        stages.addAll(algo.Kosaraju(graph, list));
    }
}
