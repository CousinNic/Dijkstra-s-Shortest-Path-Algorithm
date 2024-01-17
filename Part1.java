import java.util.*;

/**
 * A program to test Djkstra's shortest path algorithm
 * 
 * @author Nic Drummey
 * Date: 2-15-2022
 */
public class Part1 {

	private PriorityQueue<Node> queue;
	private int cost[];
	private Set<Integer> HS;
	static List<List<Node>> adjacentNodes;

	/**
	 * 
	 * @param totalNodes
	 */
	public Part1(int totalNodes) {
		queue = new PriorityQueue<Node>(totalNodes, new Node());
		cost = new int[totalNodes];
		HS = new HashSet<Integer>();

	}

	/**
	 * main method to set the graph
	 * 
	 * @param arg
	 */
	public static void main(String arg[]) {

		List<List<Node>> conn = new ArrayList<List<Node>>();

		for (int i = 0; i < 6; i++) {
			List<Node> T = new ArrayList<Node>();
			conn.add(T);
		}
		// fills graph
		conn.get(0).add(new Node(1, 2));
		conn.get(0).add(new Node(2, 4));

		conn.get(1).add(new Node(2, 1));
		conn.get(1).add(new Node(3, 7));

		conn.get(2).add(new Node(4, 3));

		conn.get(3).add(new Node(5, 1));

		conn.get(4).add(new Node(5, 5));
		conn.get(4).add(new Node(3, 2));

		int target = 0;
		Part1 dpq = new Part1(conn.size());
		dpq.dijkstraAlgorithm(conn, target);

		// prints origional and sort
		printGraph();
		printShortestDistances(dpq, target);
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
	private void neighbours(int N) {

		int edgeCost;
		int newCost;

		for (int i = 0; i < adjacentNodes.get(N).size(); i++) {
			Node t = adjacentNodes.get(N).get(i);
			// makes sure node is not in HashSet and sets values
			if (!HS.contains(t.node)) {
				edgeCost = t.cost;
				newCost = cost[N] + edgeCost;

				// checks that the cost of the node is greater than the cost
				// of the total calculated distance from the start node, if yes updated values
				if (newCost < cost[t.node])
					cost[t.node] = newCost;

				queue.add(new Node(t.node, cost[t.node]));
			}
		}
	}

	/**
	 * method to print the the shortest calculated path of a the graph given the
	 * target to reach
	 * 
	 * @param I
	 * @param target
	 */
	public static void printShortestDistances(Part1 I, int target) {
		System.out.println("shortest paths from node " + target + ":");
		for (int i = 0; i < I.cost.length; i++)
			System.out.println("Shortest path distance to Node " + i + " is " + I.cost[i]);
	}

	/**
	 * method to print out the origional graph
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
 * class to represent each node, with the distance to reach it
 * 
 * @author Nic Drummey
 *
 */
class Node implements Comparator<Node> {

	public int node;
	public int cost;

	/**
	 * constructor to initialize 
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
	 * simple compare function
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
