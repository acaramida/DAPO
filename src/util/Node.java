package util;

import java.util.HashSet;

public class Node implements Comparable<Node> {

	private int value;
	private HashSet<Integer> edges;
	private boolean covered;

	public Node(int value) {
		this.value = value;
		edges = new HashSet<Integer>();
		covered = false;
	}

	public int getEdgesSize() {
		return edges.size();
	}

	public int getValue() {
		return value;
	}

	public void addEdge(int follower) {
		edges.add(follower);
	}

	public HashSet<Integer> getEdges() {
		return edges;
	}

	public void setCovered(boolean covered) {
		this.covered = covered;
	}

	public boolean isCovered() {
		return covered;
	}

	public int compareTo(Node node) {
		if (this.getEdgesSize() > node.getEdgesSize()) {
			return 1;
		}
		if (this.getEdgesSize() < node.getEdgesSize()) {
			return -1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Node graphNode = (Node) o;
		return getValue() == graphNode.getValue();
	}
}
