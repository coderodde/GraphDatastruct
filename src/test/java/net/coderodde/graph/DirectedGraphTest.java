package net.coderodde.graph;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class DirectedGraphTest {
    
    private static final double E = 0.001;
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
        assertEquals(0, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(0, 1));
        assertEquals(1, graph.getNumberOfEdges());
        assertFalse(graph.addEdge(0, 1));
        assertEquals(1, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(0, 1, 2.0));
        assertEquals(1, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(1, 2, 3.0));
        assertEquals(2, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(2, 1, 4.0));
        assertEquals(3, graph.getNumberOfEdges());
        
        assertTrue(graph.addEdge(0, 2));
        assertEquals(4, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(2, 0, 10.0));
        assertEquals(5, graph.getNumberOfEdges());
        
        assertTrue(graph.clearNode(1));
        assertEquals(2, graph.getNumberOfEdges());
        
        assertTrue(graph.removeEdge(2, 0));
        assertEquals(1, graph.getNumberOfEdges());
        assertTrue(graph.removeNode(0));
        assertTrue(graph.removeNode(2));
        
        assertEquals(0, graph.getNumberOfEdges());
    }

    @Test
    public void testAddNode() {
        for (int i = 10; i < 20; ++i) {
            assertEquals(i - 10, graph.size());
            assertFalse(graph.hasNode(i));
            assertTrue(graph.addNode(i));
            assertTrue(graph.hasNode(i));
            assertFalse(graph.addNode(i));
            assertEquals(i - 9, graph.size());
        }
        
        for (int i = 0; i < 10; ++i) {
            assertFalse(graph.hasNode(i));
        }
        
        for (int i = 20; i < 30; ++i) {
            assertFalse(graph.hasNode(i));
        }
    }

    @Test
    public void testHasNode() {
        assertFalse(graph.hasNode(2));
        assertFalse(graph.hasNode(3));
        
        graph.addNode(2);
        
        assertTrue(graph.hasNode(2));
        assertFalse(graph.hasNode(3));
        
        graph.addNode(3);
        
        assertTrue(graph.hasNode(2));
        assertTrue(graph.hasNode(3));
    }

    @Test
    public void testClearNode() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(2, 0);
        
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        
        assertEquals(4, graph.size());
        
        assertEquals(7, graph.getNumberOfEdges());
        
        assertTrue(graph.clearNode(0));
        assertFalse(graph.clearNode(0));
        
        assertEquals(3, graph.getNumberOfEdges());
        
        assertTrue(graph.clearNode(2));
        assertFalse(graph.clearNode(2));
        
        assertEquals(1, graph.getNumberOfEdges());
        assertEquals(4, graph.size());
    }

    @Test
    public void testRemoveNode() {
        assertFalse(graph.removeNode(0));
        assertTrue(graph.addNode(0));
        assertTrue(graph.removeNode(0));
        assertFalse(graph.removeNode(0));
        
        assertTrue(graph.addEdge(0, 1, 3.0));
        assertTrue(graph.addEdge(1, 2, 4.0));
        assertTrue(graph.addEdge(2, 0));
        
        assertEquals(3, graph.getNumberOfEdges());
        assertTrue(graph.removeNode(2));
        assertFalse(graph.removeNode(2));
        
        assertFalse(graph.hasNode(2));
        assertEquals(1, graph.getNumberOfEdges());
        assertEquals(2, graph.size());
    }

    @Test
    public void testAddEdge() {
        assertTrue(graph.addEdge(1, 2));
        assertFalse(graph.addEdge(1, 2));
        assertTrue(graph.hasEdge(1, 2));
        assertEquals(1.0, graph.getEdgeWeight(1, 2), E);
        assertEquals(Double.NaN, graph.getEdgeWeight(2, 1), E);
       
        assertTrue(graph.addEdge(1, 2, 10.0));
        assertFalse(graph.addEdge(1, 2, 10.0));
        
        assertEquals(10.0, graph.getEdgeWeight(1, 2), E);
    }

    @Test
    public void testHasEdge() {
        assertFalse(graph.hasEdge(1, 2));
        assertTrue(graph.addEdge(1, 2));
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.removeEdge(2, 1));
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.hasEdge(1, 2));
    }

    @Test
    public void testGetEdgeWeight() {
        assertEquals(Double.NaN, graph.getEdgeWeight(1, 2), E);
        assertTrue(graph.addEdge(2, 1, 2.5));
        assertEquals(Double.NaN, graph.getEdgeWeight(1, 2), E);
        assertTrue(graph.addEdge(1, 2));
        assertEquals(1.0, graph.getEdgeWeight(1, 2), E);
        assertTrue(graph.addEdge(1, 2, 5.5));
        assertEquals(5.5, graph.getEdgeWeight(1, 2), E);
        assertFalse(graph.addEdge(1, 2, 5.5));
        assertEquals(5.5, graph.getEdgeWeight(1, 2), E);
        
        assertTrue(graph.addEdge(2, 3));
        assertTrue(graph.addEdge(0, 1, 3.0));
        
        assertEquals(5.5, graph.getEdgeWeight(1, 2), E);
    }

    @Test
    public void testRemoveEdge() {
        assertFalse(graph.removeEdge(10, 12));
        assertFalse(graph.hasEdge(10, 12));
        graph.addEdge(10, 12);
        assertTrue(graph.hasEdge(10, 12));
        assertTrue(graph.removeEdge(10, 12));
        assertFalse(graph.hasEdge(10, 12));
        assertFalse(graph.hasEdge(10, 16));
        graph.addEdge(10, 16, 10.0);
        assertTrue(graph.hasEdge(10, 16));
        assertTrue(graph.removeEdge(10, 16));
        assertFalse(graph.hasEdge(10, 16));
        assertFalse(graph.removeEdge(10, 16));
        assertFalse(graph.hasEdge(10, 16));
    }

    @Test
    public void testGetChildrenOf() {
        assertEquals(0, graph.getChildrenOf(0).size());
        assertTrue(graph.addNode(0));
        assertEquals(0, graph.getChildrenOf(0).size());
        
        assertTrue(graph.addEdge(0, 1));
        assertTrue(graph.addEdge(0, 2, 2.0));
        assertTrue(graph.addEdge(0, 3, 3.0));
        
        assertTrue(graph.addEdge(4, 0));
        
        Set<Integer> children = graph.getChildrenOf(0);
        
        assertEquals(3, children.size());
        
        assertTrue(children.contains(1));
        assertTrue(children.contains(2));
        assertTrue(children.contains(3));
        
        assertFalse(children.contains(4));
        
        assertTrue(graph.removeNode(0));
        assertFalse(graph.removeNode(0));
        
        children = graph.getChildrenOf(0);
        
        assertTrue(children.isEmpty());
    }

    @Test
    public void testGetParentsOf() {
        assertEquals(0, graph.getParentsOf(0).size());
        assertTrue(graph.addNode(0));
        assertEquals(0, graph.getParentsOf(0).size());
        
        assertTrue(graph.addEdge(0, 1));
        assertTrue(graph.addEdge(0, 2, 2.0));
        assertTrue(graph.addEdge(0, 3, 3.0));
        
        assertTrue(graph.addEdge(4, 0));
        assertTrue(graph.addEdge(5, 0));
        
        Set<Integer> parents = graph.getParentsOf(0);
        
        assertEquals(2, parents.size());
        
        assertTrue(parents.contains(4));
        assertTrue(parents.contains(5));
        
        assertTrue(graph.removeNode(0));
        assertFalse(graph.removeNode(0));
        
        parents = graph.getParentsOf(0);
        
        assertTrue(parents.isEmpty());
    }

    @Test
    public void testGetAllNodes() {
        Set<Integer> nodeSet = graph.getAllNodes();
        
        assertEquals(0, nodeSet.size());
        
        assertTrue(graph.addEdge(0, 1));
        
        assertTrue(nodeSet.contains(0));
        assertTrue(nodeSet.contains(1));
        assertFalse(nodeSet.contains(2));
        
        assertTrue(graph.addNode(2));
        
        assertTrue(nodeSet.contains(0));
        assertTrue(nodeSet.contains(1));
        assertTrue(nodeSet.contains(2));
        
        assertEquals(3, graph.size());
        assertEquals(3, nodeSet.size());
    }
    
    @Test
    public void testClear() {
        assertEquals(0, graph.size());
        
        for (int i = 0; i < 20; ++i) {
            assertTrue(graph.addNode(i));
        }
        
        assertTrue(graph.addEdge(5, 6));
        assertEquals(20, graph.size());
        assertEquals(1, graph.getNumberOfEdges());
        
        graph.clear();
        
        assertEquals(0, graph.size());
        assertEquals(0, graph.getNumberOfEdges());
    }
    
    @Test
    public void testModificationCount() {
        int modCount = graph.getModificationCount();
        
        graph.addEdge(0, 1, 2.0);
        assertEquals(modCount += 3, graph.getModificationCount());
        
        graph.addEdge(0, 1, 3.0);
        assertEquals(modCount += 1, graph.getModificationCount());
        
        graph.addEdge(0, 1, 3.0);
        assertEquals(modCount, graph.getModificationCount());
        
        graph.addNode(2);
        assertEquals(modCount += 1, graph.getModificationCount());
        
        graph.addEdge(0, 2);
        assertEquals(modCount += 1, graph.getModificationCount());
        
        graph.addEdge(2, 0);
        assertEquals(modCount += 1, graph.getModificationCount());
        
        graph.addEdge(2, 0);
        assertEquals(modCount, graph.getModificationCount());
        
        graph.addEdge(0, 2);
        assertEquals(modCount, graph.getModificationCount());
        
        graph.removeNode(2);
        assertEquals(modCount += 3, graph.getModificationCount());
        
        graph.clear();
        assertEquals(modCount += 3, graph.getModificationCount());
    }
}
