package util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 * This class represents an graph
 * 
 * Removed unused methods, original on git link below
 * 
 * @author https://github.com/JavaZakariae/MinDominatingSet
 *
 */
public class Graph {

	// Graph
	private HashMap<Integer, Set<Integer>> graph;
	
	public Graph() {
		graph = new HashMap<>();
	}

	public void addNode(int node) {
		graph.putIfAbsent(node, new HashSet<>());
	}

	/**
	 * Add an edge between u and v.
	 *
	 * Adding an edge between two vertices requires that the two vertices exists
	 * already in the graph. If one of the two vertices dos not exist in the graph,
	 * this method will return without completing.
	 *
	 *
	 * @param u the first vertex of the edge
	 * @param v the second vertex of the edge
	 */
	public void addEdge(int u, int v) {
		if (this.getGraph().get(u) == null || this.getGraph().get(v) == null)
			return;
		this.getGraph().get(u).add(v);
		this.getGraph().get(v).add(u);

	}

	/**
	 *
	 * @param v the vertex
	 * @return the degree of the vertex {@code v}
	 */
	public int getDegreeOf(int v) {
		if (!getGraphVertices().contains(v))
			throw new NoSuchElementException();
		return this.getGraph().get(v).size();
	}

	/**
	 *
	 * @return the vertex with the maximum degree of this graph
	 */
	public int getMaxDegree() {
		return Collections.max(this.graph.values(), Comparator.comparingInt(Set::size)).size();

	}

	public Set<Integer> getGraphVertices() {
		return this.graph.keySet();
	}

	public Map<Integer, Set<Integer>> getGraph() {
		return this.graph;
	}

}
