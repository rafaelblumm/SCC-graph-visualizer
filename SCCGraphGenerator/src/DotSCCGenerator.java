import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DotSCCGenerator {
    
    public static final String TAB = " ".repeat(4);
    private DirectedGraph graph;
    private List<Set<Integer>> scc;

    public DotSCCGenerator(DirectedGraph graph) {
        this.graph = graph;
        this.scc = this.graph.getSCC();
    }

    public String generateDotFile() {
        String content = generateDotContent();
        File dotFile = new File("/app/tmp", "generated_graph.dot");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dotFile));
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            System.out.println("Erro ao gravar arquivo");
        }
        return dotFile.getAbsolutePath();
    }

    public void generateGraph(String dotFile) {
        ProcessBuilder builder = new ProcessBuilder();
        String cmd = "neato -Tsvg " + dotFile + " -o /app/tmp/generated_graph.svg";
        builder.command("sh", "-c", cmd);        
        try {
            Process process = builder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateDotContent() {
        StringBuilder sb = new StringBuilder();

        sb.append("digraph {\n");
        sb.append(getGraphConfig());
        sb.append(generateAdjencyMatrix());
        sb.append("}");

        return sb.toString();
    }

    private String getGraphConfig() {
        StringBuilder sb = new StringBuilder();

        sb.append(TAB).append("label=\"SCC:\\n");
        for (Set<Integer> scc_component : this.scc)
            sb.append(scc_component)
              .append("\\n");

        sb.append("\"\n")
          .append(TAB).append("node [\n")
          .append(TAB.repeat(2)).append("shape=circle,\n")
          .append(TAB.repeat(2)).append("style=filled,\n")
          .append(TAB.repeat(2)).append("fontcolor=white,\n")
          .append(TAB.repeat(2)).append("penwidth=0.5,\n")
          .append(TAB.repeat(2)).append("pencolor=black\n")
          .append(TAB).append("]\n\n");

        return sb.toString();
    }

    private String generateAdjencyMatrix() {
        HashMap<Integer, String> colorMap = generateColorMap();
        StringBuilder sbColorConfig = new StringBuilder();
        StringBuilder sbAdjencyList = new StringBuilder();

        for (Integer vertex : this.graph.getVertices()) {
            sbColorConfig.append(TAB)
                         .append(vertex)
                         .append("[fillcolor=" + colorMap.get(vertex) +"]\n");

            Set<Integer> adjencyList = this.graph.getAdjacencyLists().get(vertex);
            if (adjencyList.size() == 1 && adjencyList.contains(null)) {
                sbAdjencyList.append(TAB)
                             .append(vertex);
                continue;
            }

            sbAdjencyList.append(TAB)
                         .append(vertex)
                         .append(" -> ")
                         .append(formatAdjencyList(adjencyList))
                         .append("\n");
        }
        
        return sbColorConfig.append(sbAdjencyList).toString();
    }

    private String formatAdjencyList(Set<Integer> adjacencyList) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        
        for (Integer vertex : adjacencyList)
            sb.append(vertex)
              .append(" ");

        sb.append("}");
        return sb.toString();
    }

    private HashMap<Integer, String> generateColorMap() {
        HashMap<Integer, String> colorMap = new HashMap<>();
        ArrayDeque<String> colors = getAvailableColors();
        String currentColor;

        for (Set<Integer> adjencyList : this.scc) {
            currentColor = colors.pop();
            for (Integer vertex : adjencyList)
                colorMap.put(vertex, currentColor);
        }

        return colorMap;
    }

    private ArrayDeque<String> getAvailableColors() {
        ArrayDeque<String> colors = new ArrayDeque<String>();

        colors.add("cyan2");
        colors.add("chartreuse4");
        colors.add("darkorchid1");
        colors.add("darkorange2");
        colors.add("crimson");
        colors.add("gold2");
        colors.add("darkslateblue");
        colors.add("darkolivegreen");
        colors.add("darkcyan");
        colors.add("chartreuse");
        colors.add("cadetblue");
        colors.add("burlywood4");
        colors.add("brown4");

        return colors;
    }

}
