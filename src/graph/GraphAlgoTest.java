package graph;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GraphAlgoTest extends TestCase {
    private Graph randomGraph;
    private Graph graph;
    private GraphAlgo algo;

    @Before
    public void setUp() throws Exception {
        algo = new GraphAlgo();

        // randomGraph
        randomGraph = new Graph();
        randomGraph.onModify(() -> {});
        Random r = new Random();
        int min = 5;
        int max = 100;
        int vert = min + r.nextInt(max-min+1);
        int from, to;
        for(int i = 0; i < vert; i++)
            randomGraph.createVertex(String.valueOf(i));

        for(int i = 0; i < vert; i++){
            from = r.nextInt(vert);
            to = r.nextInt(vert);
            randomGraph.createEdge(String.valueOf(from), String.valueOf(to));
        }

        // special graph
        graph = new Graph();
        graph.createVertex("A");
        graph.createVertex("B");
        graph.createVertex("C");
        graph.createVertex("D");
        graph.createVertex("E");
        graph.createVertex("F");
        graph.createVertex("G");
        graph.createVertex("H");

        graph.createEdge("A", "B");

        graph.createEdge("B", "C");
        graph.createEdge("B", "E");
        graph.createEdge("B", "F");

        graph.createEdge("C", "D");
        graph.createEdge("C", "G");

        graph.createEdge("D", "H");
        graph.createEdge("D", "C");

        graph.createEdge("E", "A");
        graph.createEdge("E", "F");

        graph.createEdge("F", "G");

        graph.createEdge("G", "F");

        graph.createEdge("H", "G");
        graph.createEdge("H", "D");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void DFS1VisitAllVertices() {
        for(Vertex v : randomGraph.getVertices()){
            v.setColor(Color.WHITE);
        }
        algo.DFS1_step(randomGraph, new VerticesList(new DefaultTableModel()));
        for(Vertex v : randomGraph.getVertices()){
            assertEquals(v.getColor(), Color.BLACK);
        }
    }

    @Test
    public void testDFS2() {

    }

    @Test
    public void testClear() {
        randomGraph.clear();
        assertEquals(0, randomGraph.getVertices().size());
        assertEquals(0, randomGraph.getEdges().size());
    }
}