package mga;

import java.awt.Color;
import java.util.*;

public class Algorithm {
	
	private final Random rand = new Random();
	private final ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.blue, Color.red, Color.green, Color.white, Color.black));
	private HashMap<Integer, Color> coloredGraph = new HashMap<Integer, Color>();
		
	/**
	 * Return a colored graph
	 * @param graph
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Integer, Color> coloringRec(HashMap<Integer, ArrayList<Integer>> graph) throws Exception {	
		//On effectue les vérifications
		//Si le graphe n'est pas vide
		if (!graph.isEmpty()) {

			int x = getRandomSommet(graph);
			if (graph.size() > 1) {
				HashMap<Integer, ArrayList<Integer>> graphWithoutX = (HashMap<Integer, ArrayList<Integer>>) graph.clone();
				graphWithoutX.remove(x);
				coloredGraph = coloringRec(graphWithoutX);
			}

			ArrayList<Color> unusedColors = getUnusedColorInNeighbors(graph, coloredGraph, x);
			
			//Brique 4
			if (graph.get(x).size() <= 4) {
				for (int vertex : graph.get(x)) {
					for (Color color : colors) {
						if (coloredGraph.get(vertex) == null || !coloredGraph.get(vertex).equals(color)) {
							coloredGraph.put(x, unusedColors.get(0));
							break;
						}
					}
				}	
			}
						
			if (graph.get(x).size() == 5) {
				
				//Brique 5
				if (unusedColors.size() >= 1) {
					coloredGraph.put(x, unusedColors.get(0));
				}
				//Brique 6
				else {	
					coloredGraph = brique6(graph, coloredGraph, x);
				}
			}
			//Cas voisins > 5
			else{
				if (unusedColors.size() >= 1) {
					coloredGraph.put(x, unusedColors.get(0));
				}
				//Brique 6
				else {
					coloredGraph = brique6(graph, coloredGraph, x);
				}
			}
		}
		
		return coloredGraph;
	}

	/**
	 * Return true if the graph is 5-coloring
	 * @param graph
	 * @param coloredGraph
	 * @return
	 */
	public boolean checkColoring(HashMap<Integer, ArrayList<Integer>> graph,HashMap<Integer, Color> coloredGraph){
		ArrayList<Integer> checked = new ArrayList<>();

		for (Map.Entry<Integer, ArrayList<Integer>> mapentry : graph.entrySet()) {
			for(Integer voisin : mapentry.getValue()) {
				if (!checked.contains(voisin)) {
					if (coloredGraph.get(mapentry.getKey()).equals(coloredGraph.get(voisin))) {
						return false;
					}
				}
			}
			checked.add(mapentry.getKey());
		}
		return true;
	}
	
	private HashMap<Integer, Color> brique6(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, Color> coloredGraph, int x) {
		PriorityQueue<Integer> voisinsAtteint = new PriorityQueue<>();
		ArrayList<Integer> voisinsConnus = new ArrayList<Integer>();

		for (Integer voisinDeX : graph.get(x)) {
			for (Integer voisinDeVoisinDeX : graph.get(voisinDeX)) {
				if (!voisinsConnus.contains(voisinDeVoisinDeX)) {
					voisinsConnus.add(voisinDeVoisinDeX);
					voisinsAtteint.add(voisinDeVoisinDeX);
				}
			}
			
			while(!voisinsAtteint.isEmpty()) {
				int element = voisinsAtteint.poll();
				if(graph.containsKey(element)) {
					for (Integer obj : graph.get(element)) {
						if (!voisinsConnus.contains(obj)) {
							voisinsConnus.add(obj);
							voisinsAtteint.add(obj);
						}
					}
				}
			}
			
			for (Integer encoreLesVoisinsDeX : graph.get(x)) {
				if (!voisinsConnus.contains(encoreLesVoisinsDeX)) {
					coloredGraph = echangerCouleur(graph, coloredGraph, x, encoreLesVoisinsDeX);
					coloredGraph = echangerCouleur(graph, coloredGraph, encoreLesVoisinsDeX, voisinDeX);
					break;
				}
			}
		}
		
		return coloredGraph;
	}

	private HashMap<Integer, Color> echangerCouleur(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, Color> coloredGraph, int sommet1, int sommet2) {
		Color color1 = coloredGraph.get(sommet1);
		
		coloredGraph.replace(sommet1, coloredGraph.get(sommet2));
		coloredGraph.replace(sommet2, color1);
		
		return coloredGraph;
		
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Color> getUnusedColorInNeighbors(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, Color> coloredGraph, int element) {
		ArrayList<Color> usedColors = new ArrayList<Color>();
		for (int voisinDeX : graph.get(element)) {
			if (coloredGraph.get(voisinDeX) != null)
				usedColors.add(coloredGraph.get(voisinDeX));
		}
		
		ArrayList<Color> unusedColors = (ArrayList<Color>) colors.clone();
		unusedColors.removeAll(usedColors);
		return unusedColors;
	}

	private int getRandomSommet(HashMap<Integer, ArrayList<Integer>> graph) {
		int graphLength = graph.keySet().size();
		int randomNumber = rand.nextInt(graphLength);
				
		return (Integer) graph.keySet().toArray()[randomNumber];
	}
}
