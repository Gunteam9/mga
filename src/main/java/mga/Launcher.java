package mga;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

	private static FileSystem fileSystem = new FileSystem();
	private static File file = new File("resources/JoliGraphe10.graphe");
	
	public static void main(String[] args) {	
		HashMap<Integer, ArrayList<Integer>> graph = fileSystem.readFile(file);
		HashMap<Integer, String> coloredGraph = null;
		
		Algorithm a = new Algorithm();
		
		try {
			coloredGraph = a.coloringRec(graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		for (Map.Entry<Integer, String> entry : coloredGraph.entrySet()) {
			System.out.println(entry.getKey() + " est " + entry.getValue());
		}
	}

}
