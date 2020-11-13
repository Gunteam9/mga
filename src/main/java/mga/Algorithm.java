package mga;

import java.awt.Color;
import java.util.*;

public class Algorithm {
	
	private final Random rand = new Random();
	private final ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.blue, Color.red, Color.green, Color.gray, Color.magenta));
	private HashMap<Integer, Color> coloredGraph = new HashMap<Integer, Color>();
		
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

			//Brique 4
			if (graph.get(x).size() <= 4) {
				for (int vertex : graph.get(x)) {
					for (Color color : colors) {
						if (coloredGraph.get(vertex) == null || !coloredGraph.get(vertex).equals(color)) {
							coloredGraph.put(x, getUnusedColorInNeighbors(graph, coloredGraph, x).get(0));
							break;
						}
					}
				}	
			}
						
			if (graph.get(x).size() == 5) {
				ArrayList<Color> unusedColors = getUnusedColorInNeighbors(graph, coloredGraph, x);
				
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
				ArrayList<Color> unusedColors = getUnusedColorInNeighbors(graph, coloredGraph, x);
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

	public boolean checkColoring(HashMap<Integer, ArrayList<Integer>> graph,HashMap<Integer, Color> coloredGraph){
		for (Map.Entry mapentry : graph.entrySet()) {
			for(Integer voisin : (Integer[]) mapentry.getValue()){
				if(coloredGraph.get(mapentry.getKey()).equals(coloredGraph.get(voisin))){
					return false;
				}
			}
		}
		return true;
	}
	
	private HashMap<Integer, Color> brique6(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, Color> coloredGraph, int x) {
		PriorityQueue<Integer> voisinsAtteint = new PriorityQueue<>();
		ArrayList<Integer> voisinsConnus = new ArrayList<Integer>();
		System.out.println(graph.get(x));
		for (Integer voisinDeX : graph.get(x)) {
			System.out.println(graph.get(voisinDeX));
			for (Integer voisinDeVoisinDeX : graph.get(voisinDeX)) {
				if (!voisinsConnus.contains(voisinDeVoisinDeX)) {
					voisinsConnus.add(voisinDeVoisinDeX);
					voisinsAtteint.add(voisinDeVoisinDeX);
				}
			}
			
			while(!voisinsAtteint.isEmpty()) {
				int element = voisinsAtteint.poll();
				for (Integer obj : graph.get(element)) {
					if (!voisinsConnus.contains(obj)) {
						voisinsConnus.add(obj);
						voisinsAtteint.add(obj);
					}
				}
			}
			
			for (Integer encoreLesVoisinsDeX : graph.get(x)) {
				if (!voisinsConnus.contains(encoreLesVoisinsDeX)) {
					coloredGraph = echangerCouleur(graph, coloredGraph, x, encoreLesVoisinsDeX);
					coloredGraph = echangerCouleur(graph, coloredGraph, encoreLesVoisinsDeX, voisinDeX);
					coloredGraph = brique6(graph, coloredGraph, x);
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
