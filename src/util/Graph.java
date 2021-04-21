package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class represents an graph
 *
 */
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

    //Order the list of nodes by biggest number of neighbours (span) in descending order
	public ArrayList<Node> orderListOfNodes() {
        sortedNodes.addAll(graph.values());
        Collections.sort(sortedNodes, Collections.reverseOrder());
        return sortedNodes;
    }
}
