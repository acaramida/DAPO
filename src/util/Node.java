package util;

import java.util.HashSet;

/**
 * This class represents an vertex of the graph
 *
 */
public class Node implements Comparable<Node> {

	private int value;
	private HashSet<Integer> edges;

	public Node(int value) {
		this.value = value;
		edges = new HashSet<Integer>();
	}

	public int getEdgesSize() {
		return edges.size();
	}

	public int getValue() {
		return value;
	}

	public void addEdge(int neighbour) {
		if(!edges.contains(neighbour))
			edges.add(neighbour);
	}

	public HashSet<Integer> getEdges() {
		return edges;
	}

	// Used for sorting the nodes by biggest span in descending order
	public int compareTo(Node node) {
		if (this.getEdgesSize() > node.getEdgesSize()) {
			return 1;
		}
		if (this.getEdgesSize() < node.getEdgesSize()) {
			return -1;
		}
		return 0;
	}

}
