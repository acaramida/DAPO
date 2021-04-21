package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Graph {

	//Graph
    private HashMap<Integer, Node> graph;
    //List for ordered nodes
    private ArrayList<Node> sortedNodes;

    public Graph() {
        graph = new HashMap<Integer, Node>();
        sortedNodes = new ArrayList<Node>();
    }

    public ArrayList<Node> getSortedNodes() {
        return sortedNodes;
    }


    public void addNode(int num) {
        if (!graph.containsKey(num)) {
            graph.put(num, new Node(num));
        }
    }

    public void addEdge(int from, int to) {
        graph.get(from).addEdge(to);
    }

    public HashMap<Integer, Node> getGraph() {
        return graph;
    }

    //Set the list of nodes from the nodes with the biggest number of neighbours in descending order
	public void orderListOfNodes() {
        sortedNodes.addAll(graph.values());
        Collections.sort(sortedNodes, Collections.reverseOrder());
    }
}
