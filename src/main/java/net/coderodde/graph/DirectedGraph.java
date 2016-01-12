package net.coderodde.graph;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements a directed graph.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 11, 2016)
 */
public class DirectedGraph extends AbstractGraph {

    private final Map<Integer, 
                      Map<Integer, 
                          Double>> parentMap = new LinkedHashMap<>();
    
    private final Map<Integer, 
                      Map<Integer, 
                          Double>> childMap = new LinkedHashMap<>();
    
    @Override
    public int size() {
        return parentMap.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edges;
    }

    @Override
    public boolean addNode(int nodeId) {
        if (parentMap.containsKey(nodeId)) {
            return false;
        }
        
        parentMap.put(nodeId, new LinkedHashMap<Integer, Double>());
        childMap.put(nodeId, new LinkedHashMap<Integer, Double>());
        return true;
    }

    @Override
    public boolean hasNode(int nodeId) {
        return parentMap.containsKey(nodeId);
    }

    @Override
    public boolean clearNode(int nodeId) {
        Map<Integer, Double> parents = parentMap.get(nodeId);
        Map<Integer, Double> children = childMap.get(nodeId);
        
        if (parents.isEmpty() && children.isEmpty()) {
            return false;
        }
        
        for (Integer childId : parents.keySet()) {
            parentMap.get(childId).remove(nodeId);
        }
        
        for (Integer parentId : children.keySet()) {
            childMap.get(parentId).remove(nodeId);
        }
        
        edges -= children.size() + parents.size();
        return true;
    }

    @Override
    public boolean removeNode(int nodeId) {
        if (!hasNode(nodeId)) {
            return false;
        }
        
        clearNode(nodeId);
        parentMap.remove(nodeId);
        childMap.remove(nodeId);
        return true;
    }

    @Override
    public boolean addEdge(int tailNodeId, int headNodeId, double weight) {
        addNode(tailNodeId);
        addNode(headNodeId);
        
        if (childMap.get(tailNodeId).containsKey(headNodeId)) {
            double oldWeight = childMap.get(tailNodeId).get(headNodeId);
            childMap.get(tailNodeId).put(headNodeId, weight);
            parentMap.get(headNodeId).put(tailNodeId, weight);
            return oldWeight != weight;
        } else {
            childMap.get(tailNodeId).put(headNodeId, weight);
            parentMap.get(headNodeId).put(tailNodeId, weight);
            ++edges;
            return true;
        }
    }

    @Override
    public boolean hasEdge(int tailNodeId, int headNodeId) {
        if (!childMap.containsKey(tailNodeId)) {
            return false;
        }
        
        return childMap.get(tailNodeId).containsKey(headNodeId);
    }

    @Override
    public double getEdgeWeight(int tailNodeId, int headNodeId) {
        if (!hasEdge(tailNodeId, headNodeId)) {
            return Double.NaN;
        }
        
        return childMap.get(tailNodeId).get(headNodeId);
    }

    @Override
    public boolean removeEdge(int tailNodeId, int headNodeId) {
        if (!childMap.containsKey(tailNodeId)) {
            return false;
        }
        
        if (!childMap.get(tailNodeId).containsKey(headNodeId)) {
            return false;
        }
        
        childMap.get(tailNodeId).remove(headNodeId);
        parentMap.get(headNodeId).remove(tailNodeId);
        --edges;
        return true;
    }

    @Override
    public Set<Integer> getChildrenOf(int nodeId) {
        if (!childMap.containsKey(nodeId)) {
            return Collections.<Integer>emptySet();
        }
        
        return Collections.
                <Integer>unmodifiableSet(childMap.get(nodeId).keySet());
    }

    @Override
    public Set<Integer> getParentsOf(int nodeId) {
        if (!parentMap.containsKey(nodeId)) {
            return Collections.<Integer>emptySet();
        }
        
        return Collections.
                <Integer>unmodifiableSet(parentMap.get(nodeId).keySet());
    }

    @Override
    public Set<Integer> getAllNodes() {
        return Collections.<Integer>unmodifiableSet(childMap.keySet());
    }

    @Override
    public void clear() {
        childMap.clear();
        parentMap.clear();
        edges = 0;
    }
}
