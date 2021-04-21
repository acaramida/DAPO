import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.*;

/**
 * The input loading and tests are based from
 * https://github.com/JavaZakariae/MinDominatingSet the Graph, Node objects and
 * implementation of the greedy algorithm are based from
 * https://github.com/stitch80/UCSD-CapstoneProject and
 * http://ac.informatik.uni-freiburg.de/teaching/ss_12/netalg/lectures/chapter7.pdf
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("run.txt"));

		Graph graph = new Graph();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] line = in.readLine().split(" ");
		int nTests = Integer.parseInt(line[0]);
		
		// Load the graph
		File file = new File(line[1]);
		createGraphFromDimacsFormat(graph, file);

		double startTime, endTime, performTime = 0;
		HashSet<Node> setMDS = null;
		
		for (int i = 0; i < nTests; i++) {
			startTime = System.nanoTime();
			// Greedy Search
			setMDS = greedySearchMDS(graph);
			endTime = System.nanoTime();
			performTime += (endTime - startTime) / 1e9;
			
		}
		
		System.out.println("Test for: " + file.getName());
		System.out.println("Greedy search average time in " + nTests + " runs: " + performTime/nTests + " seconds");
		System.out.println("Minimum dominating set consists of " + setMDS.size() + " nodes");

	}

	/**
	 * This method implements the greedy algorithm. *
	 * 
	 * @param file a Dimacs file format.
	 * @return the mds of the graph
	 */
	public static HashSet<Node> greedySearchMDS(Graph graph) {
		// Initialise the map of uncovered with all nodes
		HashMap<Integer, Node> uncoveredNodes = new HashMap<Integer, Node>(graph.getGraph());
		// Get list of nodes ordered by span size in descending order
		ArrayList<Node> sortedList = graph.orderListOfNodes();
		// Set for dominating set (MDS)
		HashSet<Node> setMDS = new HashSet<Node>();

		for (int i = 0; i < sortedList.size(); i++) {
			// When there is no uncovered nodes - break
			if (uncoveredNodes.isEmpty()) {
				break;
			}
			// Take a node from the list
			Node currNode = sortedList.get(i);

			// Check if the node is uncovered
			// If node is uncovered add it to the MDS list and remove from uncoveredNodes
			// Otherwise skip this node
			if (uncoveredNodes.containsKey(currNode.getValue())) {
				setMDS.add(currNode);
				uncoveredNodes.remove(currNode.getValue());
			} else {
				continue;
			}

			// Add the nodes in the span as covered
			for (int node : currNode.getEdges()) {
				if (uncoveredNodes.containsKey(node)) {
					uncoveredNodes.remove(node);
				}
			}
		}

		return setMDS;
	}

	/**
	 * This method creates a graph data structure from a Dimacs file format. *
	 * 
	 * @param a file with Dimacs file format.
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
	 * add an edge to Graph g, modified for an undirected graph the edge is added
	 * for both nodes.
	 * 
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
