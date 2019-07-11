package graph;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

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
    public void testDFS1VisitAllVertices() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        VerticesList list = new VerticesList(model);

        for(Vertex v : randomGraph.getVertices()){
            v.setColor(Color.WHITE);
        }
        algo.DFS1_step(randomGraph, list);
        for(Vertex v : randomGraph.getVertices()){
            assertEquals(v.getColor(), Color.BLACK);
        }
    }

    @Test
    public void testDFS1Timeout() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        VerticesList list = new VerticesList(model);

        for(Vertex v : graph.getVertices()){
            v.setColor(Color.WHITE);
        }
        algo.DFS1_step(graph, list);
        List<String> expected = Arrays.asList("A", "B", "E", "C", "D", "H", "G", "F");
        Iterator it = expected.iterator();
        while(list.hasNext() && it.hasNext()){
            assertEquals(it.next(), list.getNext().getName());
        }
    }

    @Test
    public void testDFS2() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        VerticesList list = new VerticesList(model);

        ArrayList<ArrayList<Color>> states = algo.Kosaraju(graph, list);
        ArrayList<Color> lastState = states.get(states.size()-1);

        // (A, B, E) (C, D, H) (F, G)
        for (int i = 0; i != lastState.size(); ++i) {
            graph.getVertices().get(i).setColor(lastState.get(i));
        }

        Color aColor = graph.getVertex("A").getColor();
        Color bColor = graph.getVertex("B").getColor();
        Color cColor = graph.getVertex("C").getColor();
        Color dColor = graph.getVertex("D").getColor();
        Color eColor = graph.getVertex("E").getColor();
        Color fColor = graph.getVertex("F").getColor();
        Color gColor = graph.getVertex("G").getColor();
        Color hColor = graph.getVertex("H").getColor();

        // (A, B, E)
        assertEquals(aColor, bColor);
        assertEquals(bColor, eColor);

        // (C, D, H)
        assertEquals(cColor, dColor);
        assertEquals(dColor, hColor);

        // (F, G)
        assertEquals(fColor, gColor);
    }

    @Test
    public void testClear() {
        randomGraph.clear();
        assertEquals(0, randomGraph.getVertices().size());
        assertEquals(0, randomGraph.getEdges().size());
    }

    @Test
    public void testAddVertex() {
        String name = "Vertex #" + randomGraph.getVertices().size() + 1;
        assertTrue(randomGraph.createVertex(name));
        assertFalse(randomGraph.createVertex(name));
    }

    @Test
    public void testRemoveVertex() {
        Random r = new Random();
        Vertex v = randomGraph.getVertices().get(r.nextInt(randomGraph.getVertices().size()));
        assertTrue(randomGraph.removeVertex(v));
        assertFalse(randomGraph.removeVertex(v));
    }

    @Test
    public void testAddEdge() {
        Random r = new Random();
        Vertex from = randomGraph.getVertices().get(r.nextInt(randomGraph.getVertices().size()));
        Vertex to = randomGraph.getVertices().get(r.nextInt(randomGraph.getVertices().size()));
        assertTrue(randomGraph.createEdge(from, to));
        assertFalse(randomGraph.createEdge(from, to));
    }

    @Test
    public void testRemoveEdge() {
        Random r = new Random();
        Vertex from = randomGraph.getVertices().get(r.nextInt(randomGraph.getVertices().size()));
        while(from.getEdges().size() < 1){ //ищем вершину с ребром
            randomGraph.getVertices().get(r.nextInt(randomGraph.getVertices().size()));
        }
        Vertex to = from.getEdges().get(0).getDest();
        assertTrue(randomGraph.removeEdge(from, to));
        assertFalse(randomGraph.removeEdge(from, to));
    }
}