package mga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

	public static void main(String[] args) {	
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, String> coloredGraph = null;
		
		graph.put(0, new ArrayList<Integer>(Arrays.asList(3)));
		graph.put(1, new ArrayList<Integer>(Arrays.asList(1,2,3)));
		graph.put(2, new ArrayList<Integer>(Arrays.asList(1,3)));
		graph.put(3, new ArrayList<Integer>(Arrays.asList(0,1,2)));
				
		Algorithm a = new Algorithm();
		
		try {
			coloredGraph = a.coloringRec(graph);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(coloredGraph);
		
		for (Map.Entry<Integer, String> entry : coloredGraph.entrySet()) {
			System.out.println(entry.getKey() + " est " + entry.getValue());
		}
	}

}
