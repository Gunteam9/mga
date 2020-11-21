package mga;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

	public static final float graphSizeMult = 1f;
	private static final File graphFile = new File("resources/JoliGraphe10.graphe");
	private static final File graphPointPosition = new File("resources/JoliGraphe10.coords");
	
	private static FileSystem fileSystem = new FileSystem();
	private static Algorithm algo = new Algorithm();
	
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		HashMap<Integer, ArrayList<Integer>> graph = fileSystem.readFile(graphFile);
		HashMap<Integer, Color> coloredGraph = null;
				
		try {
			coloredGraph = algo.coloringRec(graph);
			if(algo.checkColoring(graph,coloredGraph)){
				fileSystem.writeFile(coloredGraph);
			}
			else
				System.err.println("Le graphe généré n'est pas 5-coloration");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
		for (Map.Entry<Integer, Color> entry : coloredGraph.entrySet()) {
			System.out.println(entry.getKey() + " est " + Utils.getColorName(entry.getValue()));
		}
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		double totalTimeInSecond = (double) totalTime /1_000_000_000;
		System.out.println("Temps d'exécution (sans le graphique):" + totalTimeInSecond + " secondes" );
		
		new Graphic(fileSystem.readGraphicFile(graphPointPosition), graph, coloredGraph);
		
		long endTimeWithGraphic   = System.nanoTime();
		long totalTimeWithGraphic = endTimeWithGraphic - startTime;
		double totalTimeInSecondWithGraphic = (double) totalTimeWithGraphic /1_000_000_000;
		System.out.println("Temps d'exécution (avec le graphique):" + totalTimeInSecondWithGraphic + " secondes" );
	}

}
