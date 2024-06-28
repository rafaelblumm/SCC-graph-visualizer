import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public final class DirectedGraph {

	private List<Integer> vertices = new ArrayList<Integer>();
	private List<Set<Integer>> adjacencyLists;
	private int index = 0;
	private Stack<Integer> stack = new Stack<Integer>();
	private Map<Integer, Integer> visitTimes = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> lowlinks = new HashMap<Integer, Integer>();
	private List<Set<Integer>> stronglyConnectedComponents = new ArrayList<Set<Integer>>();

	public DirectedGraph(int size) {
		adjacencyLists = new ArrayList<Set<Integer>>(size);
		for (int i = 0; i < size; i++) {
			vertices.add(i);
			adjacencyLists.add(new HashSet<Integer>());
		}
	}

	public List<Integer> getVertices() {
		return this.vertices;
	}

	public List<Set<Integer>> getAdjacencyLists() {
		return this.adjacencyLists;
	}

	public static DirectedGraph parseFromDot(String dotGraph) {
        ArrayList<Integer[]> elements = new ArrayList<>();
        for (String line : dotGraph.split(";")) {
            String[] splittedLine = line.split("->");
            String key = splittedLine[0].trim();
			if (splittedLine.length == 1) {
				elements.add(new Integer[]{Integer.parseInt(key), null});
				continue;
			}

            String values = splittedLine[1].replace("[", "")
                                           .replace("]", "")
                                           .replace("\"", "")
                                           .trim();

            for (String value : values.split(" ")) {
                Integer[] connection = {Integer.parseInt(key), Integer.parseInt(value)};
                elements.add(connection);
            }
        }

        HashSet<Integer> all_nodes = new HashSet<>();
        for (Integer[] connection : elements)
            for (Integer node : connection)
				if (node != null)
                	all_nodes.add(node);

        DirectedGraph graph = new DirectedGraph(all_nodes.size());
        for (Integer[] connection : elements)
			if (connection[1] != null)
				graph.addEdge(connection[0], connection[1]);
        
        return graph;
	}

	public void addEdge(int from, int to) {
		adjacencyLists.get(from).add(to);
	}

	public List<Set<Integer>> getSCC() {
		for (int vertex : vertices)
			if (!visitTimes.containsKey(vertex))
				stronglyConnect(vertex);

		return stronglyConnectedComponents;
	}

	private void stronglyConnect(int vertex) {
		visitTimes.put(vertex, index);
		lowlinks.put(vertex, index);
		index += 1;
		stack.push(vertex);

		for (int adjacent : adjacencyLists.get(vertex)) {
			if (!visitTimes.containsKey(adjacent)) {
				stronglyConnect(adjacent);
				lowlinks.put(vertex, Math.min(lowlinks.get(vertex), lowlinks.get(adjacent)));
			} else if (stack.contains(adjacent))
				lowlinks.put(vertex, Math.min(lowlinks.get(vertex), visitTimes.get(adjacent)));
		}

		if (lowlinks.get(vertex) == visitTimes.get(vertex)) {
			Set<Integer> stonglyConnectedComponent = new HashSet<Integer>();
			int top;
			do {
				top = stack.pop();
				stonglyConnectedComponent.add(top);
			} while (top != vertex);

			stronglyConnectedComponents.add(stonglyConnectedComponent);
		}
	}
}
