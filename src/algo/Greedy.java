package algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import util.Graph;

public class Greedy {

	/**
	 * Each vertex in the graph has an integer value that represents its weight, and
	 * a boolean value that indicate if it is already covered.
	 */
	HashMap<Integer, Integer> weights;
	private HashMap<Integer, Boolean> covered;

	/**
	 * Class constructor
	 */
	public Greedy() {
		weights = new HashMap<>();
		covered = new HashMap<>();
	}

	/**
	 * @return the the maximum weight of the graph this value indicate which vertex
	 *         can cover the maximum number of vertices.
	 */
	private int getMax() {
		return this.weights.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue))
				.map(Map.Entry::getValue).get();
	}

	/**
	 *
	 * chooseVertex implementation of the greedy algorithm
	 *
	 * @return the vertex that will be added to the dominating set
	 *
	 *         this vertex should have the maximum weight.
	 */
	int chooseVertex() {
		int M = getMax();
		if (M == 0)
			return -1;
		List<Integer> set = this.weights.entrySet().stream().filter(entry -> entry.getValue() == M)
				.map(Map.Entry::getKey).collect(Collectors.toCollection(ArrayList::new));
		return set.get(new Random().nextInt(set.size()));
	}

	/**
	 * adjustWeights implementation After adding a vertex
	 * to the dominating Set, an adjustment of weight should be done on the
	 * vertices.
	 * 
	 * @param g
	 * @param v
	 */
	private void adjustWeights(Graph g, int v) {
		weights.replace(v, 0);
		for (Integer vj : g.getGraph().get(v)) {
			if (weights.get(vj) > 0) {
				if (!covered.get(v)) {
					int oldValue = weights.get(vj);
					weights.replace(vj, oldValue - 1);
				}
				if (!covered.get(vj)) {
					covered.replace(vj, true);
					int oldValue = weights.get(vj);
					weights.replace(vj, oldValue - 1);
					for (Integer vk : g.getGraph().get(vj)) {
						if (weights.get(vk) > 0) {
							oldValue = weights.get(vk);
							weights.replace(vk, oldValue - 1);
						}
					}
				}
			}
		}
		covered.replace(v, true);
	}

	/**
	 *
	 * @param graph the graph instance
	 *
	 * @return the mds of the graph instance
	 */
	public Set<Integer> run(Graph graph) {
		Set<Integer> mds = new HashSet<>();
		// initialisation
		for (Integer key : graph.getGraph().keySet()) {
			weights.put(key, 1 + graph.getDegreeOf(key));
			covered.put(key, false);
		}
		int v = chooseVertex();
		while (v != -1) {
			mds.add(v);
			adjustWeights(graph, v);
			v = chooseVertex();
		}
		return mds;
	}

}
