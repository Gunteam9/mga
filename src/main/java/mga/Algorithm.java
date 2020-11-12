package mga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class Algorithm {
	
	private final Random rand = new Random();
	private final ArrayList<String> colors = new ArrayList<String>(Arrays.asList("Bleu", "Rouge", "Vert", "Jaune", "Violet"));
		
	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> coloringRec(HashMap<Integer, ArrayList<Integer>> graph) throws Exception {	
		HashMap<Integer, String> coloredGraph = new HashMap<Integer, String>();
	
		//On effectue les vérifications
		//Si le graphe n'est pas vide
		if (!graph.isEmpty()) {

			//S'il existe un sommet avec un degré supérieur à 5
			for (Map.Entry<Integer, ArrayList<Integer>> entry : graph.entrySet()) {
				if (entry.getValue().size() > 5)
					throw new Exception();
			}
						
			int x = getRandomSommet(graph);
			if (graph.size() > 1) {
				HashMap<Integer, ArrayList<Integer>> graphWithoutX = (HashMap<Integer, ArrayList<Integer>>) graph.clone();
				graphWithoutX.remove(x);
				coloredGraph = coloringRec(graphWithoutX);
			}
			System.out.println("Après " + graph.size());
			
			//Brique 4
			if (graph.get(x).size() <= 4) {
				for (int vertex : graph.get(x)) {
					for (String color : colors) {
						if (coloredGraph.get(vertex) == null || !coloredGraph.get(vertex).contains(color)) {
							coloredGraph.put(x, getUnusedColorInNeighbors(graph, coloredGraph, x).get(0));
							break;
						}
					}
				}	
			}
						
			if (graph.get(x).size() == 5) {
				ArrayList<String> usedColor = new ArrayList<String>();
				for (int neighbor : graph.get(x)) {
					usedColor.add(coloredGraph.get(neighbor));
				}
				
				//Brique 5
				if (usedColor.size() != 5) {
					if (isAllElementDifferent(usedColor))
						coloredGraph.put(x, colors.get(colors.size() - 1));
				}
				//Brique 6
				else {	
					coloredGraph = brique6(graph, coloredGraph, x);
				}
			}
		}
		
		return coloredGraph;
	}
	
	private HashMap<Integer, String> brique6(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, String> coloredGraph, int x) {
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

	private HashMap<Integer, String> echangerCouleur(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, String> coloredGraph, int sommet1, int sommet2) {
		String color1 = coloredGraph.get(sommet1);
		
		coloredGraph.replace(sommet1, coloredGraph.get(sommet2));
		coloredGraph.replace(sommet2, color1);
		
		return coloredGraph;
		
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<String> getUnusedColorInNeighbors(HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, String> coloredGraph, int element) {
		ArrayList<String> usedColors = new ArrayList<String>();
		for (int voisinDeX : graph.get(element)) {
			if (coloredGraph.get(voisinDeX) != null)
				usedColors.add(coloredGraph.get(voisinDeX));
		}
		
		ArrayList<String> unusedColors = (ArrayList<String>) colors.clone();
		unusedColors.removeAll(usedColors);
		return unusedColors;
	}

	private int getRandomSommet(HashMap<Integer, ArrayList<Integer>> graph) {
		int graphLength = graph.keySet().size();
		int randomNumber = rand.nextInt(graphLength);
				
		return (Integer) graph.keySet().toArray()[randomNumber];
	}
	
	private <T> boolean isAllElementDifferent(ArrayList<T> array) {
		for (T element : array) {
			ArrayList<T> arrayWihoutElement = array;
			arrayWihoutElement.remove(element);
			if (arrayWihoutElement.contains(element))
				return false;
		}
		
		return true;
	}
}
