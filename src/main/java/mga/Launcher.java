package mga;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

	private static FileSystem fileSystem = new FileSystem();
	private static File graphFile = new File("resources/JoliGraphe10.graphe");
	private static File graphPointPosition = new File("resources/JoliGraphe10.coords");
	private static Algorithm algo = new Algorithm();
	
	
	public static void main(String[] args) {	
		HashMap<Integer, ArrayList<Integer>> graph = fileSystem.readFile(graphFile);
		HashMap<Integer, Color> coloredGraph = null;
		
				
		try {
			coloredGraph = algo.coloringRec(graph);
			fileSystem.writeFile(coloredGraph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
		for (Map.Entry<Integer, Color> entry : coloredGraph.entrySet()) {
			System.out.println(entry.getKey() + " est " + entry.getValue());
		}
		
		Graphic graphic = new Graphic(fileSystem.readGraphicFile(graphPointPosition), graph, coloredGraph);
	}

}
