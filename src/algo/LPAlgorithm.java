package algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javafx.util.Pair;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;
import util.Graph;

public class LPAlgorithm {
	
	
	private static int rounding(int maxdegree,double[] sol) {
		double cutoff = 1.0/(maxdegree+1);
		int optimalValue = 0;
		for(double var: sol) {
			if(var >= cutoff) {
				optimalValue++;
			}
		}
		return optimalValue;
	}
	

	public static int run(Graph g) {
		Set<Integer> vertices = g.getGraphVertices();
		Map<Integer, Set<Integer>> graph = g.getGraph();
		double[] objFunction = new double[vertices.size()];
		Arrays.fill(objFunction, 1);
		
		LinearProgram lp = new LinearProgram(objFunction);
		int i = 1;
		for(Integer vertice: vertices) {
			double[] constraintArray = new double[vertices.size()];
			constraintArray[vertice-1] = 1.0;
			Set<Integer> adjancents = graph.get(vertice);
			for(Integer adj: adjancents) 
				constraintArray[adj-1] = 1.0;
			
			lp.addConstraint(new LinearBiggerThanEqualsConstraint(constraintArray,1.0,
					String.format("c%d",i++)));
		}
		lp.setMinProblem(true);
		lp.setLowerbound(new double[vertices.size()]);
		LinearProgramSolver solver  = SolverFactory.newDefault();
		double[] sol = solver.solve(lp);
		int maxdegreeVertex = g.getMaxDegree();
		return rounding(graph.get(maxdegreeVertex).size(),sol);
	}
}
