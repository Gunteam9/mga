package mga;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.javatuples.Quartet;

@SuppressWarnings("serial")
public class Graphic extends JPanel{
	
	public int POINT_SIZE = 3;
	public int POINT_POS_MULT = 4;

	private HashMap<Integer, Integer[]> pointPosition;
	private HashMap<Integer, ArrayList<Integer>> graph;
	private HashMap<Integer, Color> coloredGraph;
	
	//Label, Pos x, Pos y, Color
	private ArrayList<Quartet<String, Integer, Integer, Color>> pointsToDraw = new ArrayList<>();
	
	//Pos x 1, Pos y 1, Pos x 2, Pos y 2
	private ArrayList<Quartet<Integer, Integer, Integer, Integer>> linesToDraw = new ArrayList<>();
	
	/**
	 * Create and open a JFrame with the given graph
	 * @param pointPosition
	 * @param graph
	 * @param coloredGraph
	 */
	public Graphic(HashMap<Integer, Integer[]> pointPosition, HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer, Color> coloredGraph) {
		JFrame frame = new JFrame("Graphe");
		
		this.pointPosition = pointPosition;
		this.graph = graph;
		this.coloredGraph = coloredGraph;
		
		//TODO Change the factor the upgrade or downgrade the graph size
		POINT_SIZE *= graph.size() * 0.02;
		POINT_POS_MULT *= graph.size() * 0.02;
        
        frame.setSize(1600, 900);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setContentPane(this);
		frame.setVisible(true);

		
		getData();
		
		transformData();
		
		repaint();
	}

	public void paintComponent(Graphics g) {
		//Points
		for (Quartet<String, Integer, Integer, Color> item : pointsToDraw) {
			g.setColor(Color.DARK_GRAY);
			g.drawString(item.getValue0(), item.getValue1(), item.getValue2());
			g.setColor(item.getValue3());
			g.fillOval(item.getValue1(), item.getValue2(), POINT_SIZE, POINT_SIZE);
		}
		
		//Lines
		for (Quartet<Integer, Integer, Integer, Integer> item : linesToDraw) {
			g.setColor(Color.black);
			g.drawLine(item.getValue0(), item.getValue1(), item.getValue2(), item.getValue3());
		}
	}
	
	private void getData() {

		//Points
		for (int i = 0; i < pointPosition.size(); i++) {
			pointsToDraw.add(new Quartet<String, Integer, Integer, Color>(String.valueOf(i), 
					pointPosition.get(i)[0], pointPosition.get(i)[1], coloredGraph.get(i)));
		}
		
		//Lines
		for (Map.Entry<Integer, ArrayList<Integer>> entry : graph.entrySet()) {
			for (Integer item : entry.getValue()) {
				linesToDraw.add(new Quartet<Integer, Integer, Integer, Integer>(
						pointPosition.get(entry.getKey())[0], pointPosition.get(entry.getKey())[1],
						pointPosition.get(item)[0], pointPosition.get(item)[1]));
			}
		}
	}
	
	
	private void transformData() {		
		for (int i = 0; i < pointsToDraw.size(); i++) {
			Quartet<String, Integer, Integer, Color> item = pointsToDraw.get(i); 
			
			item = item.setAt1((item.getValue1() * POINT_POS_MULT) - POINT_SIZE / 2);
			item = item.setAt2((item.getValue2() * POINT_POS_MULT) - POINT_SIZE / 2);
			
			pointsToDraw.set(i, item);
		}
		
		for (int i = 0; i < linesToDraw.size(); i++) {
			
			Quartet<Integer, Integer, Integer, Integer> item = linesToDraw.get(i);
			
			item = item.setAt0(item.getValue0() * POINT_POS_MULT);
			item = item.setAt1(item.getValue1() * POINT_POS_MULT);
			item = item.setAt2(item.getValue2() * POINT_POS_MULT);
			item = item.setAt3(item.getValue3() * POINT_POS_MULT);
			
			linesToDraw.set(i, item);
		}
	}
}
