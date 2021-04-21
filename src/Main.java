import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Graph g = new Graph();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String path = in.readLine();

		// Load the graph
		File file = new File(path);
		createGraphFromDimacsFormat(g, file);

		double startTime = System.nanoTime();
		// Order the list of nodes by n of edges
		g.orderListOfNodes();

		// Greedy Search
		HashSet<Node> setMDS = greedySearchMDS(g.getSortedNodes(), g.getGraph());
		double endTime = System.nanoTime();
		double performTime = (endTime - startTime) / 1e9;
		System.out.println("Greedy search performed in " + performTime + " seconds");
		System.out.println("Minimum dominating set consists of " + setMDS.size() + " nodes");

	}

	/**
	 * This method implements the greedy algorithm. *
	 * 
	 * @param file a Dimacs file format.
	 * @return the mds of the graph
	 */
	public static HashSet<Node> greedySearchMDS(ArrayList<Node> sortedList, HashMap<Integer, Node> graph) {
		// Get all the nodes as uncovered
		HashMap<Integer, Node> uncoveredNodes = new HashMap<Integer, Node>(graph);
		// Set for dominating set (MDS)
		HashSet<Node> setMDS = new HashSet<Node>();

		// Initialise nodes in sortedList as uncovered
		for (Node g : sortedList) {
			g.setCovered(false);
		}

		for (int i = 0; i < sortedList.size(); i++) {
			// When there is no uncovered nodes - break
			if (uncoveredNodes.isEmpty()) {
				break;
			}
			// Take a node from the list
			Node currNode = sortedList.get(i);
			// Check if the node is covered
			// If node is uncovered add it to the MDS list and set it covered
			// Otherwise skip this node
			if (!currNode.isCovered()) {
				setMDS.add(currNode);
				currNode.setCovered(true);
			} else {
				continue;
			}
			// Remove node from the uncovered set
			uncoveredNodes.remove(currNode.getValue());
			// Check the neighbours of the node
			// If are uncovered - set them to covered and remove from
			// uncovered set
			for (int node : currNode.getEdges()) {
				if (uncoveredNodes.containsKey(node)) {
					uncoveredNodes.get(node).setCovered(true);
					uncoveredNodes.remove(node);
				}
			}
		}

		return setMDS;
	}

	/**
	 * This method creates a graph data structure from a Dimacs file format. *
	 * 
	 * @author https://github.com/JavaZakariae/MinDominatingSet
	 * @param file a Dimacs file format.
	 * @return an undirected graph
	 */
	public static Graph createGraphFromDimacsFormat(Graph g, File file) {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			do {
				line = reader.readLine();
			} while (line.charAt(0) != 'p');
			while ((line = reader.readLine()) != null) {
				String[] edge = line.split(" ");
				addEdge(g, edge);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}

	/**
	 * add an edge to Graph g, modified for an undirected graph
	 * the edge is added for both nodes.
	 * 
	 * @author https://github.com/JavaZakariae/MinDominatingSet
	 * @param g    the given graph
	 * @param line the line containing the linking information.
	 */
	public static void addEdge(Graph g, String[] line) {
		int u = Integer.parseInt(line[1]);
		int v = Integer.parseInt(line[2]);
		g.addNode(u);
		g.addNode(v);
		g.addEdge(u, v);
		g.addEdge(v, u);
	}

}
