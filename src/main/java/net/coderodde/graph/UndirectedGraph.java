package net.coderodde.graph;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements an undirected graph.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 10, 2016)
 */
public class UndirectedGraph extends AbstractGraph {

    private final Map<Integer, Map<Integer, Double>> map = 
            new LinkedHashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edges;
    }

    @Override
    public boolean addNode(int nodeId) {
        if (map.containsKey(nodeId)) {
            return false;
        }

        map.put(nodeId, new LinkedHashMap<>());
        modificationCount++;
        return true;
    }

    @Override
    public boolean hasNode(int nodeId) {
        return map.containsKey(nodeId);
    }

    @Override
    public boolean clearNode(int nodeId) {
        if (!hasNode(nodeId)) {
            return false;
        }

        Map<Integer, Double> neighbors = map.get(nodeId);

        if (neighbors.isEmpty()) {
            return false;
        }

        for (Integer neighborId : neighbors.keySet()) {
            map.get(neighborId).remove(nodeId);
        }

        edges -= neighbors.size();
        modificationCount += neighbors.size();
        neighbors.clear();
        return true;
    }

    @Override
    public boolean removeNode(int nodeId) {
        if (!hasNode(nodeId)) {
            return false;
        }

        clearNode(nodeId);
        map.remove(nodeId);
        modificationCount++;
        return true;
    }

    @Override
    public boolean addEdge(int tailNodeId, int headNodeId, double weight) {
        if (tailNodeId == headNodeId) {
            // Undirected graph are not allowed to contain self-loops.
            return false;
        }

        addNode(tailNodeId);
        addNode(headNodeId);

        if (!map.get(tailNodeId).containsKey(headNodeId)) {
            map.get(tailNodeId).put(headNodeId, weight);
            map.get(headNodeId).put(tailNodeId, weight);
            modificationCount++;
            edges++;
            return true;
        } else {
            double oldWeight = map.get(tailNodeId).get(headNodeId);
            map.get(tailNodeId).put(headNodeId, weight);
            map.get(headNodeId).put(tailNodeId, weight);
            
            if (oldWeight != weight) {
                modificationCount++;
                return true;
            }
            
            return false;
        }
    }

    @Override
    public boolean hasEdge(int tailNodeId, int headNodeId) {
        if (!map.containsKey(tailNodeId)) {
            return false;
        }

        return map.get(tailNodeId).containsKey(headNodeId);
    }

    @Override
    public double getEdgeWeight(int tailNodeId, int headNodeId) {
        if (!hasEdge(tailNodeId, headNodeId)) {
            return Double.NaN;
        } 

        return map.get(tailNodeId).get(headNodeId);
    }

    @Override
    public boolean removeEdge(int tailNodeId, int headNodeId) {
        if (!map.containsKey(tailNodeId)) {
            return false;
        }

        if (!map.get(tailNodeId).containsKey(headNodeId)) {
            return false;
        }

        map.get(tailNodeId).remove(headNodeId);
        map.get(headNodeId).remove(tailNodeId);
        modificationCount++;
        edges--;
        return true;
    }

    @Override
    public Set<Integer> getChildrenOf(int nodeId) {
        if (!map.containsKey(nodeId)) {
            return Collections.<Integer>emptySet();
        }

        return Collections.<Integer>unmodifiableSet(map.get(nodeId).keySet());
    }

    @Override
    public Set<Integer> getParentsOf(int nodeId) {
        if (!map.containsKey(nodeId)) {
            return Collections.<Integer>emptySet();
        }

        return Collections.<Integer>unmodifiableSet(map.get(nodeId).keySet());    
    }

    @Override
    public Set<Integer> getAllNodes() {
        return Collections.<Integer>unmodifiableSet(map.keySet());
    }

    @Override
    public void clear() {
        modificationCount += map.size();
        map.clear();
        edges = 0;
    }
}
