import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Graph g = new Graph();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String file = in.readLine();

		// Load the graph
		graphLoad(g, file);


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

	// Greedy Search on the passed sorted list of nodes
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

	public static void graphLoad(Graph g, String filename) {
		Set<Integer> seen = new HashSet<Integer>();
		Scanner sc;
		try {
			sc = new Scanner(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		// Iterate over the lines in the file, adding new
		// vertices as they are found and connecting them with edges.
		while (sc.hasNextInt()) {
			int v1 = sc.nextInt();
			int v2 = sc.nextInt();
			if (!seen.contains(v1)) {
				g.addVertex(v1);
				seen.add(v1);
			}
			if (!seen.contains(v2)) {
				g.addVertex(v2);
				seen.add(v2);
			}
			g.addEdge(v1, v2);
			g.addEdge(v2, v1);
		}

		sc.close();
	}
}
