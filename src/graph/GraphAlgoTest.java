package graph;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class GraphAlgoTest extends TestCase {
    private Graph g;
    private GraphAlgo algo;

    @Before
    public void setUp() throws Exception {
        g = new Graph();
        algo = new GraphAlgo();
        g.onModify(() -> {});
        Random r = new Random();
        int min = 5;
        int max = 100;
        int vert = min + r.nextInt(max-min+1);
        int from, to;
        for(int i = 0; i < vert; i++)
            g.createVertex(String.valueOf(i));

        for(int i = 0; i < vert; i++){
            from = r.nextInt(vert);
            to = r.nextInt(vert);
            g.createEdge(String.valueOf(from), String.valueOf(to));
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDFS1() {
        for(Vertex v : g.getVertices()){
            v.setColor(Color.WHITE);
        }
        algo.DFS1_step(g, new VerticesList(new DefaultTableModel()));
        for(Vertex v : g.getVertices()){
            assertEquals(v.getColor(), Color.BLACK);
        }
    }

    @Test
    public void testClear() {
        g.clear();
        assertEquals(0, g.getVertices().size());
        assertEquals(0, g.getEdges().size());
    }
}