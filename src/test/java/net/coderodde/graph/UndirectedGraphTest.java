package net.coderodde.graph;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UndirectedGraphTest {

    private static final double E = 0.0001;
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
    public void testClearNode() {
        assertEquals(0, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(2, 1));
        assertEquals(1.0, graph.getEdgeWeight(1, 2), E);
        assertEquals(1.0, graph.getEdgeWeight(2, 1), E);
        assertTrue(graph.addEdge(1, 5));
        assertEquals(2, graph.getNumberOfEdges());
        
        graph.clearNode(1);
        assertEquals(0, graph.getNumberOfEdges());
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
        
        assertTrue(graph.addNode(10));
        assertTrue(graph.addEdge(10, 11));
        assertEquals(2, graph.size());
        assertEquals(1, graph.getNumberOfEdges());
        assertEquals(1.0, graph.getEdgeWeight(10, 11), E);
        assertEquals(1.0, graph.getEdgeWeight(11, 10), E);
        
        assertTrue(graph.addEdge(10, 12, 2.0));
        assertEquals(2.0, graph.getEdgeWeight(10, 12), E);
        assertEquals(2.0, graph.getEdgeWeight(12, 10), E);
        assertEquals(3, graph.size());
        assertEquals(2, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(10, 13, 3.0));
        assertEquals(3.0, graph.getEdgeWeight(10, 13), E);
        assertEquals(3.0, graph.getEdgeWeight(13, 10), E);
        assertEquals(4, graph.size());
        assertEquals(3, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(12, 13));
        assertEquals(4, graph.size());
        assertEquals(4, graph.getNumberOfEdges());
        
        assertTrue(graph.removeNode(10));
        
        assertEquals(3, graph.size());
        assertEquals(1, graph.getNumberOfEdges());
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

    @Test
    public void testGetEdgeWeight() {
        assertEquals(Double.NaN, graph.getEdgeWeight(0, 1), E);
        assertEquals(Double.NaN, graph.getEdgeWeight(1, 0), E);
        
        assertTrue(graph.addEdge(0, 1, 10.0));
        assertEquals(10.0, graph.getEdgeWeight(0, 1), E);
        assertTrue(graph.addEdge(2, 3, 5.0));
        
        assertEquals(10.0, graph.getEdgeWeight(0, 1), E);
        assertEquals(10.0, graph.getEdgeWeight(1, 0), E);
        
        assertEquals(5.0, graph.getEdgeWeight(2, 3), E);
        assertEquals(5.0, graph.getEdgeWeight(3, 2), E);
        
        assertEquals(Double.NaN, graph.getEdgeWeight(1, 2), E);
    }
    
    @Test
    public void testRemoveEdge() {
        assertFalse(graph.removeEdge(0, 1));
        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(2, 1));
        
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(2, 1));
    }

    @Test
    public void testGetChildrenOf() {
        graph.addNode(0);
        assertEquals(0, graph.getChildrenOf(0).size());
        assertEquals(0, graph.getChildrenOf(1).size());
        
        assertTrue(graph.addEdge(0, 1));
        assertTrue(graph.addEdge(0, 2));
        assertTrue(graph.addEdge(0, 3, 3.0));
        
        Set<Integer> set = graph.getChildrenOf(0);
        
        assertEquals(3, set.size());
        
        set = graph.getChildrenOf(3);
        
        assertEquals(1, set.size());
    }
        
    @Test
    public void testGetParentsOf() {
        graph.addNode(0);
        assertEquals(0, graph.getParentsOf(0).size());
        assertEquals(0, graph.getParentsOf(1).size());
        
        assertTrue(graph.addEdge(0, 1));
        assertTrue(graph.addEdge(0, 2));
        assertTrue(graph.addEdge(0, 3, 3.0));
        
        Set<Integer> set = graph.getParentsOf(0);
        
        assertEquals(3, set.size());
        
        set = graph.getParentsOf(3);
        
        assertEquals(1, set.size());
    }

    @Test
    public void testGetAllNodes() {
        assertEquals(0, graph.getAllNodes().size());
        
        for (int i = 0; i < 10; ++i) {
            assertEquals(i, graph.getAllNodes().size());
            assertTrue(graph.addNode(i));
            assertEquals(i + 1, graph.getAllNodes().size());
        }
        
        Set<Integer> set = graph.getAllNodes();
        
        for (int i = 0; i < 10; ++i) {
            assertTrue(set.contains(i));
        }
        
        for (int i = -10; i < 0; ++i) {
            assertFalse(set.contains(i));
        }
        
        for (int i = 10; i < 20; ++i) {
            assertFalse(set.contains(i));
        }
    }

    @Test
    public void testClear() {
        for (int i = 0; i < 10; ++i) {
            graph.addNode(i);
        }
    
        assertEquals(10, graph.size());
        graph.clear();
        assertEquals(0, graph.size());
    }
    
    @Test
    public void testModificationCount() {
        int modCount = graph.getModificationCount();
        
        assertTrue(graph.addEdge(0, 1, 2.0));
        assertEquals(modCount += 3, graph.getModificationCount());
        
        assertTrue(graph.addEdge(0, 1, 2.1));
        assertEquals(modCount += 1, graph.getModificationCount());
        
        assertFalse(graph.addEdge(0, 1, 2.1));
        assertEquals(modCount, graph.getModificationCount());
        
        assertFalse(graph.addEdge(1, 0, 2.1));
        assertEquals(modCount, graph.getModificationCount());
        
        assertTrue(graph.addNode(3));
        assertEquals(modCount += 1, graph.getModificationCount());
        
        assertFalse(graph.addNode(3));
        assertEquals(modCount, graph.getModificationCount());
        
        assertTrue(graph.addEdge(3, 0));
        assertEquals(modCount += 1, graph.getModificationCount());
        
        assertTrue(graph.removeNode(1));
        assertEquals(modCount += 2, graph.getModificationCount());
        
        assertTrue(graph.addEdge(1, 2));
        assertEquals(modCount += 3, graph.getModificationCount());
        
        assertTrue(graph.removeEdge(2, 1));
        assertEquals(modCount += 1, graph.getModificationCount());
        
        assertFalse(graph.removeEdge(1, 2));
        assertEquals(modCount, graph.getModificationCount());
        
        graph.clear();
            
        assertEquals(modCount += 5, graph.getModificationCount());
    }
}
