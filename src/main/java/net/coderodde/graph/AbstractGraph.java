package net.coderodde.graph;

import java.util.Set;

/**
 * This class defines the API for graph data structures. The actual nodes are 
 * represented as integers. The client programmer should always be able to map
 * his domain specific nodes to the integers. 
 * <p>
 * Not only the query methods return a boolean value, but any other method
 * returns a boolean value indicating whether the structure of the graph has 
 * changed.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 10, 2016)
 */
public abstract class AbstractGraph {

    /**
     * This field caches the amount of changes made to this graph. This is used
     * for keeping track whether the structure of the graph has changed since 
     * the previous check of the modification count. Adding or removing a node
     * contributes one unit to the counter, and adding or removing or updating 
     * an edge contributes one unit as well. Note, that if we remove a node that
     * has incident edges to it, the counter will reflect the removal of the 
     * edges as well.
     */
    protected int modificationCount;
    
    /**
     * Caches the number of edges in this graph.
     */
    protected int edges;

    /**
     * Returns the number of nodes in this graph.
     * 
     * @return the size of this graph. 
     */
    public abstract int size();

    /**
     * Returns the number of edges in this graph.
     * 
     * @return the number of edges. 
     */
    public abstract int getNumberOfEdges();

    /**
     * Adds the node with ID {@code nodeId} to this graph.
     * 
     * @param nodeId the ID of the node to add.
     * @return {@code true} if the structure of this graph has changed, which is
     *         the same as that the added node was not present in the graph.
     */
    public abstract boolean addNode(int nodeId);

    /**
     * Checks whether the given node is present in this graph.
     * 
     * @param nodeId the query node.
     * @return {@code true} if the query node is in this graph. {@code false} 
     *         otherwise.
     */
    public abstract boolean hasNode(int nodeId);

    /**
     * If {@code nodeId} is present in this graph, removes all edges incident on
     * {@code nodeId}.
     * 
     * @param nodeId the node to clear.
     * @return {@code true} if the node {@code nodeId} had at least one incident
     *         edge and, thus, the structure of the graph changed.
     */
    public abstract boolean clearNode(int nodeId);

    /**
     * Removes the argument node from this graph.
     * 
     * @param nodeId the node to remove.
     * @return {@code true} only if the node was present in the graph which 
     *         means that the structure of the graph has changed.
     */
    public abstract boolean removeNode(int nodeId);

    /**
     * Creates an edge between {@code tailNodeId} and {@code headNodeId} with 
     * weight {@code weight}. It depends on the concrete implementation of this
     * abstract class what an edge {@code (tailNodeId, headNodeId)}. Two
     * possible cases are an undirected edge and a directed edge. Refer to the 
     * documentation of the implementing graph type.
     * <p>
     * If some of the input nodes are not present in this graph, it will be 
     * created silently.
     * 
     * @param tailNodeId the tail node of the edge.
     * @param headNodeId the head node of the edge.
     * @param weight the weight of the edge.
     * @return {@code true} only if the edge was not present in the graph, or
     *         the weight of the edge has changed.
     */
    public abstract boolean addEdge(int tailNodeId, 
                                    int headNodeId, 
                                    double weight);

    /**
     * Creates an edge between {@code tailNodeId} and {@code headNodeId} with
     * the default weight of 1.0. This method is a shortcut for constructing
     * (virtually) unweighted graphs.
     * 
     * @param tailNodeId the tail node of the edge.
     * @param headNodeId the head node of the edge.
     * @return {@code true}  only if the edge was not present in the graph, or
     *         the weight of the edge has changed.
     */
    public boolean addEdge(int tailNodeId, int headNodeId) {
        return addEdge(tailNodeId, headNodeId, 1.0);
    }

    /**
     * Returns a boolean value indicating whether this graph contains an edge
     * from {@code tailNodeId} to {@code headNodeId}. 
     * 
     * @param tailNodeId the tail node of the query edge.
     * @param headNodeId the head node of the query edge.
     * @return {@code true} only if the query edge is in this graph.
     */
    public abstract boolean hasEdge(int tailNodeId, int headNodeId);

    /**
     * Returns the weight of the edge {@code (tailNodeId, headNodeId)}. If the
     * query edge does not exist, returns {@link java.lang.Double#NaN}.
     * 
     * @param tailNodeId the tail node of the query edge.
     * @param headNodeId the head node of the query edge.
     * @return the weight of the edge.
     */
    public abstract double getEdgeWeight(int tailNodeId, int headNodeId);

    /**
     * Removes the edge from {@code tailNodeId} and {@code headNodeId}.
     * 
     * @param tailNodeId the tail node of the edge to remove.
     * @param headNodeId the head node of the edge to remove.
     * @return {@code true} if the target edge was in this graph, and thus is
     *         removed.
     */
    public abstract boolean removeEdge(int tailNodeId, int headNodeId);

    /**
     * Returns the set of all nodes that are children of the node 
     * {@code nodeId}. It depends on the actual graph implementation what is 
     * understood by the termin <i>child</i>. In unweighted graphs, every child 
     * is also a parent of a node, which is not necessarily true in directed 
     * graphs.
     * 
     * @param nodeId the query node.
     * @return set of nodes that are children on the argument node.
     */
    public abstract Set<Integer> getChildrenOf(int nodeId);

    /**
     * Returns the set of all nodes that are parents of the node {@code nodeId}.
     * 
     * @see #getChildrenOf(int) 
     * @param nodeId the query node.
     * @return set of nodes that are parent of the arugment node.
     */
    public abstract Set<Integer> getParentsOf(int nodeId);

    /**
     * Returns the set of all nodes stored in this graph.
     * 
     * @return the set of all nodes.
     */
    public abstract Set<Integer> getAllNodes();

    /**
     * Removes all nodes and edges from this graph.
     */
    public abstract void clear();
    
    /**
     * Returns the modification count of this graph.
     * 
     * @return the modification count of this graph. 
     */
    public int getModificationCount() {
        return modificationCount;
    }
}
