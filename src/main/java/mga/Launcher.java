package mga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

	public static void main(String[] args) {	
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, String> coloredGraph = null;
		
		graph.put(0, new ArrayList<Integer>(Arrays.asList(1,5,7,11)));
		graph.put(1, new ArrayList<Integer>(Arrays.asList(8,4,5,0)));
		graph.put(2, new ArrayList<Integer>(Arrays.asList(4,9)));
		graph.put(3, new ArrayList<Integer>(Arrays.asList(7,4,9,6,11)));
		graph.put(4, new ArrayList<Integer>(Arrays.asList(5,1,8,2,3,7)));
		graph.put(5, new ArrayList<Integer>(Arrays.asList(0,1,4,7)));
		graph.put(6, new ArrayList<Integer>(Arrays.asList(11,3,9)));
		graph.put(7, new ArrayList<Integer>(Arrays.asList(0,5,4,3,11)));
		graph.put(8, new ArrayList<Integer>(Arrays.asList(10,9,4,1)));
		graph.put(9, new ArrayList<Integer>(Arrays.asList(6,3,2,8,10)));
		graph.put(10, new ArrayList<Integer>(Arrays.asList(9,8)));
		graph.put(11, new ArrayList<Integer>(Arrays.asList(0,7,3,6)));
				
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
