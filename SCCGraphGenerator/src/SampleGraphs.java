public class SampleGraphs {
    /**
     * Cria grafo de exemplo:
     * 
     * <code>
     * 0 -> [ 1 ]
     * 1 -> [ 2 7 ]
     * 2 -> [ 3 6 ]
     * 3 -> [ 4 ]
     * 4 -> [ 2 5 ]
     * 6 -> [ 3 5 ]
     * 7 -> [ 0 6 ]
     * </code>
     * 
     * @return Grafo
     */
    public static DirectedGraph sample1() {
		DirectedGraph graph = new DirectedGraph(8);
		
		graph.addEdge(0, 1);

		graph.addEdge(1, 2);
        graph.addEdge(1, 7);

		graph.addEdge(2, 3);
        graph.addEdge(2, 6);

		graph.addEdge(3, 4);

		graph.addEdge(4, 2);
        graph.addEdge(4, 5);

		graph.addEdge(6, 3);
        graph.addEdge(6, 5);
        
		graph.addEdge(7, 0);
        graph.addEdge(7, 6);

        return graph;
    }

    /**
     * Cria grafo de exemplo:
     * 
     * <code>
     * 0 -> [ 1 5 ]
     * 2 -> [ 0 3 ]
     * 3 -> [ 2 5 ]
     * 4 -> [ 2 3 ]
     * 5 -> [ 4 ]
     * 6 -> [ 0 4 9 ]
     * 7 -> [ 6 8 ]
     * 8 -> [ 7 9 ]
     * 9 -> [ 10 11 ]
     * 10 -> [ 12 ]
     * 11 -> [ 4 12 ]
     * 12 -> [ 9 ]
     * </code>
     * 
     * @return Grafo
     */
    public static DirectedGraph sample2() {
		DirectedGraph graph = new DirectedGraph(13);
		
        graph.addEdge(0, 1);
        graph.addEdge(0, 5);

        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        
        graph.addEdge(3, 2);
        graph.addEdge(3, 5);

        graph.addEdge(4, 2);
        graph.addEdge(4, 3);

        graph.addEdge(5, 4);

        graph.addEdge(6, 0);
        graph.addEdge(6, 4);
        graph.addEdge(6, 9);

        graph.addEdge(7, 6);
        graph.addEdge(7, 8);

        graph.addEdge(8, 7);
        graph.addEdge(8, 9);

        graph.addEdge(9, 10);
        graph.addEdge(9, 11);

        graph.addEdge(10, 12);

        graph.addEdge(11, 4);
        graph.addEdge(11, 12);
        graph.addEdge(12, 9);

        return graph;
    }

    /**
     * Cria grafo de exemplo:
     * 
     * <code>
     * 0 -> [ 1 ]
     * 1 -> [ 2 ]
     * 2 -> [ 3 4 ]
     * 3 -> [ 0 ]
     * 4 -> [ 2 ]
     * </code>
     * 
     * @return Grafo
     */
    public static DirectedGraph sample3() {
		DirectedGraph graph = new DirectedGraph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 0);
        graph.addEdge(4, 2);

        return graph;
    }

    public static DirectedGraph sample4() {
        DirectedGraph graph = new DirectedGraph(8);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        graph.addEdge(3, 1);
        graph.addEdge(3, 2);
        graph.addEdge(3, 5);

        graph.addEdge(4, 2);
        graph.addEdge(4, 6);

        graph.addEdge(5, 3);
        graph.addEdge(5, 4);

        graph.addEdge(6, 4);
        
        graph.addEdge(7, 5);
        graph.addEdge(7, 6);
        graph.addEdge(7, 7);

        return graph;
    }
}
