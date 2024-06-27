import java.util.List;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        if (args.length < 1)
            return;

        if (args[0].equalsIgnoreCase("demo")) {
            TarjanDemo.run();
            return;
        }

        DirectedGraph graph = DirectedGraph.parseFromDot(String.join(" ", args).trim());
        DotSCCGenerator generator = new DotSCCGenerator(graph);
        String filename = generator.generateDotFile();
        generator.generateGraph(filename);
    }
}

final class TarjanDemo {

    public static void run() {
        System.out.println("============== SAMPLE 1 ==============");
        prettyPrintSCC(SampleGraphs.sample1().getSCC());

        System.out.println();
        System.out.println("============== SAMPLE 2 ==============");
        prettyPrintSCC(SampleGraphs.sample2().getSCC());

        System.out.println();
        System.out.println("============== SAMPLE 3 ==============");
        prettyPrintSCC(SampleGraphs.sample3().getSCC());
    }

    private static void prettyPrintSCC(List<Set<Integer>> scc) {
        System.out.println(String.format("Strongly Connected Components = %d", scc.size()));
		for (Set<Integer> component : scc)
			System.out.println(component);
    }

}
