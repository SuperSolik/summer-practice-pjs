package graph;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphAlgoTest extends TestCase {
    private Graph g;
    private GraphAlgo algo;

    @Before
    public void setUp() throws Exception {
        g = new Graph();
        algo = new GraphAlgo();
    }

    @After
    public void tearDown() throws Exception {
        g = null;
        algo = null;
    }

    @Test
    public void testKosaraju() {
        assertEquals(g.getVertices().size(), 0);
    }
}