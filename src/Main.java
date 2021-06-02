import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import algo.Greedy;
import algo.LPAlgorithm;
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
			String algo = "LP";
			for (int i = 0; i < nTests; i++) {
				// start timer
				startTime = System.nanoTime();
				int size = 0;
				
				// Greedy Search
				if(algo == "Greedy") {
					Set<Integer> mdsSet = new Greedy().run(graph);
					size = mdsSet.size();
				}else {
					//LP search
					size = LPAlgorithm.run(graph);
				}
				
				/*String set = "----MDS: [";

				Object[] aux = mdsSet.toArray();
				for (int j = 0; j < size; j++)
					if(j != size - 1)
						set = set + (aux[j].toString()) + ",";
					else set = set + (aux[j].toString());
				System.out.println(set + "]");*/
				
				// end timer
				endTime = System.nanoTime();
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
			System.out.println(algo + " " + file.getName() + " " + nTests + " runs.");
			System.out.println("Mean time (s): " + String.format("%.5f", meanTime/1e9));
			System.out.println("Standard deviation (s): " + String.format("%.5f", (Math.sqrt(sdTime / nTests)/1e9)));
			System.out.println("Min MDS: " + minSet);
			System.out.println("Max MDS: " + maxSet);
			System.out.println("Mean MDS: " + String.format("%.2f", meanSize));
			System.out.println("Standard deviation MDS: " + String.format("%.2f", Math.sqrt(sdSize / nTests)));
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
			
			String[] aux = line.split(" ");
			int points = Integer.parseInt(aux[2]);
			for(int i = 1; i <= points; i++)
				g.addNode(i);
					
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
		g.addEdge(u, v);
	}

}
