import java.util.*;

/**
 * A program to test Djkstra's shortest path algorithm
 * 
 * @author Nic Drummey Date: 2-15-2022
 */
public class ShortestPath {

	private PriorityQueue<Node> queue;
	private int cost[];
	private Set<Integer> HS;
	static List<List<Node>> adjacentNodes;

	/**
	 * 
	 * @param totalNodes
	 */
	public ShortestPath(int totalNodes) {
		queue = new PriorityQueue<Node>(totalNodes, new Node());
		cost = new int[totalNodes];
		HS = new HashSet<Integer>();

	}

	/**
	 * main method to build a graph node relations can be changed freely
	 * 
	 * @param arg
	 */
	public static void main(String arg[]) {

		List<List<Node>> graph = new ArrayList<List<Node>>();

		// values can be changed
		// freely********************************************************************
		int targetNodeToSearch = 0; // alters the target node to perform the algorithm
		int totalNodes = 5; // changes the amount of nodes, (don't forget to add relations between nodes.)
		// ************************************************************************************************

		// checks for valid target node
		if (targetNodeToSearch > totalNodes) {
			System.out.printf("Node must exist! keep in mind nodes range from 0 to %d\n", graph.size() - 1);
			return;
		}

		for (int i = 0; i <= totalNodes; i++) {
			List<Node> T = new ArrayList<Node>();
			graph.add(T);
		}

		// fills graph (nodes and relations can be changed and added
		// freely)****************************
		graph.get(0).add(new Node(1, 2));
		graph.get(0).add(new Node(2, 4));

		graph.get(1).add(new Node(2, 1));
		graph.get(1).add(new Node(3, 7));

		graph.get(2).add(new Node(4, 3));
		graph.get(2).add(new Node(0, 4));

		graph.get(3).add(new Node(5, 1));

		graph.get(4).add(new Node(5, 5));
		graph.get(4).add(new Node(3, 2));
		// ***********************************************************************************************
		// note these paths are one way, so just because 1 can reach 2, does not mean 2

		ShortestPath dpq = new ShortestPath(graph.size());
		dpq.dijkstraAlgorithm(graph, targetNodeToSearch);

		// prints origional and sort
		printGraph();
		printShortestDistances(dpq, targetNodeToSearch);
	}

	/**
	 * method to calculate Dijkstra's Algorithm
	 * 
	 * @param adjacentNodes
	 * @param node
	 */
	public void dijkstraAlgorithm(List<List<Node>> adjacentNodes, int node) {
		this.adjacentNodes = adjacentNodes;

		for (int i = 0; i < 6; i++) {
			cost[i] = Integer.MAX_VALUE;
		}
		cost[node] = 0;
		queue.add(new Node(node, 0));

		while (HS.size() != 6) {
			// to remove smallest node if queue isn't empty
			if (!queue.isEmpty()) {
				int removedNode = queue.remove().node;
				if (HS.contains(removedNode)) {
				} else {
					// adds node if its hasn't been added
					HS.add(removedNode);
					neighbours(removedNode);
				}
			} else {
				// exits method if queue is empty
				return;
			}
		}
	}

	/**
	 * method to check go through neighbors of node u
	 * 
	 * @param u
	 */
	private void neighbours(int n) {

		int edgeCost;
		int newCost;

		for (int i = 0; i < adjacentNodes.get(n).size(); i++) {
			Node t = adjacentNodes.get(n).get(i);
			// makes sure node is not in HashSet and sets values
			if (!HS.contains(t.node)) {
				edgeCost = t.cost;
				newCost = cost[n] + edgeCost;

				// checks that the cost of the node is greater than the cost
				// of the total calculated distance from the start node, if yes updated values
				if (newCost < cost[t.node])
					cost[t.node] = newCost;

				queue.add(new Node(t.node, cost[t.node]));
			}
		}
	}

	/**
	 * method to print the the shortest calculated path to each node the in the
	 * graph given the target node
	 * 
	 * @param I
	 * @param target
	 */
	public static void printShortestDistances(ShortestPath I, int target) {
		System.out.println("shortest paths from node " + target + ":");
		int cost;
		// cycles through nodes
		for (int i = 0; i < I.cost.length; i++) {
			cost = I.cost[i];
			// checks if a path was found, then prints accordingly
			if (cost != 2147483647) {
				System.out.println("Shortest path distance to Node " + i + " is " + cost);
			} else {
				System.out.println("There is not path to Node " + i);
			}

		}
	}

	/**
	 * method to print original graph
	 */
	public static void printGraph() {
		for (int i = 0; i < adjacentNodes.size(); i++) {
			System.out.printf("Node: %d ", i);
			for (int g = 0; g < adjacentNodes.get(i).size(); g++) {
				if (g > 0) {
					System.out.print(", and ");
				}
				System.out.printf("distance to node %d is %d", adjacentNodes.get(i).get(g).node,
						adjacentNodes.get(i).get(g).cost);

			}
			System.out.println();
		}
	}

}

/**
 * class representing each node, including the distance to reach it
 * 
 * @author Nic Drummey Date: 2-15-2022
 */
class Node implements Comparator<Node> {

	public int node;
	public int cost;

	/**
	 * constructor to initialize node
	 */
	public Node() {
	}

	/**
	 * constructor to assign node and distance
	 */
	public Node(int node, int distance) {
		this.node = node;
		this.cost = distance;
	}

	/**
	 * simple compare method
	 */
	@Override
	public int compare(Node n1, Node n2) {

		if (n1.cost > n2.cost) {
			return 1;
		} else if (n1.cost < n2.cost) {
			return -1;
		} else {
			return 0;
		}
	}
}
