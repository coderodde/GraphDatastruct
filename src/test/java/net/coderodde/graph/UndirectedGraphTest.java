package net.coderodde.graph;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UndirectedGraphTest {

    private final UndirectedGraph graph = new UndirectedGraph();
    
    @Before
    public void before() {
        graph.clear();
    }
    
    @Test
    public void testSize() {
        assertEquals(0, graph.size());
        assertTrue(graph.addNode(0));
        assertEquals(1, graph.size());
        assertTrue(graph.addEdge(1, 2));
        assertEquals(3, graph.size());
    }

    @Test
    public void testGetNumberOfEdges() {
        assertEquals(0, graph.getNumberOfEdges());
        assertTrue(graph.addNode(0));
        assertEquals(0, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(1, 2));
        assertEquals(1, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(0, 2));
        assertEquals(2, graph.getNumberOfEdges());
    }

    @Test
    public void testAddNode() {
        for (int i = 0; i < 10; ++i) {
            assertEquals(i, graph.size());
            assertTrue(graph.addNode(i));
            assertEquals(i + 1, graph.size());
        }
        
        assertEquals(10, graph.size());
        
        for (int i = 0; i < 10; ++i) {
            assertEquals(10, graph.size());
            assertFalse(graph.addNode(i));
            assertEquals(10, graph.size());
        }
    }

    @Test
    public void testHasNode() {
        for (int i = 0; i < 10; ++i) {
            assertFalse(graph.hasNode(i));
            assertTrue(graph.addNode(i));
            assertTrue(graph.hasNode(i));
        }
        
        assertFalse(graph.hasNode(-1));
        assertFalse(graph.hasNode(10));
    }

    @Test
    public void testRemoveNode() {
        for (int i = 0; i < 10; ++i) {
            assertFalse(graph.removeNode(i));
        }
        
        for (int i = 0; i < 10; ++i) {
            assertTrue(graph.addNode(i));
        }
        
        assertEquals(10, graph.size());
        
        for (int i = 0; i < 10; ++i) {
            assertEquals(10 - i, graph.size());
            assertTrue(graph.removeNode(i));
            assertEquals(10 - 1 - i, graph.size());
            assertFalse(graph.removeNode(i));
        }
        
        assertEquals(0, graph.size());
    }

    @Test
    public void testAddEdge() {
        assertEquals(0, graph.size());
        assertEquals(0, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(0, 1, 2.0));
        
        assertEquals(graph.getEdgeWeight(0, 1), 2.0, 0.0001);
        assertEquals(2, graph.size());
        assertEquals(1, graph.getNumberOfEdges());
        
        assertTrue(graph.addNode(2));
        assertEquals(3, graph.size());
        
        assertTrue(graph.addEdge(1, 2, 5.0));
        assertEquals(3, graph.size());
        assertEquals(2, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(0, 2));
        assertEquals(3, graph.size());
        assertEquals(3, graph.getNumberOfEdges());
        
        assertFalse(graph.addNode(1));
        
        assertTrue(graph.addEdge(10, 11, 10.0));
        assertFalse(graph.addEdge(11, 10, 10.0));
    }

    @Test
    public void testHasEdge() {
        assertFalse(graph.hasEdge(0, 1));
        assertEquals(0, graph.size());
        assertEquals(0, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(1, 2));
        
        assertFalse(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 1));
        
        assertEquals(2, graph.size());
        assertEquals(1, graph.getNumberOfEdges());
    }

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
