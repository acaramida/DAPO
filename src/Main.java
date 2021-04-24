import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import algo.Greedy;
import util.*;

/**
 * https://github.com/JavaZakariae/MinDominatingSet
 * https://arxiv.org/abs/1705.00318
 * tests - https://davidchalupa.github.io/research/data/social.html
 * tests - https://mat.gsia.cmu.edu/COLOR/instances.html
 */
public class Main {

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("run.txt"));

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = in.readLine();
		int nTests = Integer.parseInt(line);
		line = in.readLine();

		while (line != null) {

			double startTime, endTime, totalTime = 0;
			double[] times = new double[nTests];
			int[] sets = new int[nTests];
			int totalSets = 0;
			int minSet = Integer.MAX_VALUE;
			int maxSet = 0;

			File file = new File(line);
			Graph graph = new Graph();
			// Load the graph
			createGraph(graph, file);

			for (int i = 0; i < nTests; i++) {
				// start timer
				startTime = System.currentTimeMillis();
				// Greedy Search
				int size = new Greedy().run(graph);
				// end timer
				endTime = System.currentTimeMillis();
				// save and calc data for prints
				times[i] = (endTime - startTime);
				if (size < minSet)
					minSet = size;
				if (size > maxSet)
					maxSet = size;
				sets[i] = size;
				totalSets += size;
				totalTime += (endTime - startTime);
			}

			// Calculate mean and standard deviation
			double meanTime = totalTime / nTests;
			double meanSize = (double) totalSets / nTests;
			double sdTime = 0;
			double sdSize = 0;
			for (int i = 0; i < nTests; i++) {

				sdTime += Math.pow((times[i] - meanTime), 2);
				sdSize += Math.pow((sets[i] - meanSize), 2);

			}

			System.out.println();
			System.out.println("Greedy " + file.getName() + " " + nTests + " runs.");
			System.out.println("Mean time (ms): " + String.format("%.3f", meanTime));
			System.out.println("Standard deviation (ms): " + String.format("%.3f", (Math.sqrt(sdTime / nTests))));
			System.out.println("Min MDS: " + minSet);
			System.out.println("Max MDS: " + maxSet);
			System.out.println("Mean MDS: " + String.format("%.3f", meanSize));
			System.out.println("Standard deviation MDS: " + String.format("%.3f", Math.sqrt(sdSize / nTests)));
			line = in.readLine();
		}
		System.out.println("\nEnd");

	}

	/**
	 * This method creates a graph data structure from a Dimacs file format.
	 * 
	 * @author https://github.com/JavaZakariae/MinDominatingSet
	 * 
	 * @param a file with Dimacs file format.
	 * @return an undirected graph
	 */
	public static Graph createGraph(Graph g, File file) {
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
	 * Add an edge to Graph g, undirected graph the edge is added for both nodes.
	 * 
	 * @author https://github.com/JavaZakariae/MinDominatingSet
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
	}

}
