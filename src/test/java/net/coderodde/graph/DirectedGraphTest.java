package net.coderodde.graph;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class DirectedGraphTest {
    
    private final DirectedGraph graph = new DirectedGraph();
    
    @Before
    public void before() {
        graph.clear();
    }

    @Test
    public void testSize() {
        assertEquals(0, graph.size());
        assertTrue(graph.addNode(0));
        assertEquals(1, graph.size());
        assertFalse(graph.addNode(0));
        assertEquals(1, graph.size());
        assertTrue(graph.addNode(2));
        assertFalse(graph.addNode(2));
        assertEquals(2, graph.size());
        assertFalse(graph.addNode(2));
        graph.clear();
        assertEquals(0, graph.size());
    }

    @Test
    public void testGetNumberOfEdges() {
    }

    @Test
    public void testAddNode() {
    }

    @Test
    public void testHasNode() {
    }

    @Test
    public void testClearNode() {
    }

    @Test
    public void testRemoveNode() {
    }

    @Test
    public void testAddEdge() {
    }

    @Test
    public void testHasEdge() {
    }

    @Test
    public void testGetEdgeWeight() {
    }

    @Test
    public void testRemoveEdge() {
    }

    @Test
    public void testGetChildrenOf() {
    }

    @Test
    public void testGetParentsOf() {
    }

    @Test
    public void testGetAllNodes() {
    }

    @Test
    public void testClear() {
    }
    
}
