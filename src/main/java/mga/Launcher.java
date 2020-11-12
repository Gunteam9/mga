package mga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

	public static void main(String[] args) {	
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, String> coloredGraph = null;
		
		graph.put(0, new ArrayList<Integer>(Arrays.asList(3,5,8)));
		graph.put(1, new ArrayList<Integer>(Arrays.asList(6,7,2,9)));
		graph.put(2, new ArrayList<Integer>(Arrays.asList(9,1)));
		graph.put(3, new ArrayList<Integer>(Arrays.asList(0,4,6,5)));
		graph.put(4, new ArrayList<Integer>(Arrays.asList(3,7,6)));
		graph.put(5, new ArrayList<Integer>(Arrays.asList(0,3,8)));
		graph.put(6, new ArrayList<Integer>(Arrays.asList(3,4,7,1,8)));
		graph.put(7, new ArrayList<Integer>(Arrays.asList(4,1,6)));
		graph.put(8, new ArrayList<Integer>(Arrays.asList(0,5,6)));
		graph.put(9, new ArrayList<Integer>(Arrays.asList(1,2)));
				
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
