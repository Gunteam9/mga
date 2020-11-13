package mga;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileSystem {

	public HashMap<Integer, ArrayList<Integer>> readFile(File file) {
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		
		try {
			BufferedReader b = new BufferedReader(new FileReader(file));
			
			String readLine = "";
			int line = 0;
			
			System.out.println("Reading file using Buffered Reader");
			
			while ((readLine = b.readLine()) != null) {
				if (line > 0) {
					int vertex = Integer.valueOf(readLine.substring(0, readLine.indexOf(":")));
					String listNeighborsString = readLine.substring(readLine.indexOf(":") + 3, readLine.length() - 1);
					
					ArrayList<Integer> listNeighbors = new ArrayList<>();
					for (String element : listNeighborsString.split(",")) {
						listNeighbors.add(Integer.valueOf(element.trim()));
					}
					
					map.put(vertex, listNeighbors);
				}
				
				line++;
			}
			
			b.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	public HashMap<Integer, Integer[]> readGraphicFile(File file) {
		HashMap<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
		
		try {
	        BufferedReader b = new BufferedReader(new FileReader(file));
	
	        String readLine = "";
	        int line = 0;
	
	        System.out.println("Reading graphic file using Buffered Reader");
	
	        while ((readLine = b.readLine()) != null) {
	        	if (line > 0) {
	        		int vertex = Integer.valueOf(readLine.substring(0, readLine.indexOf(":")));
	        		String listNeighborsString = readLine.substring(readLine.indexOf(":") + 3, readLine.length() - 1);
	        		
	        		Integer[] position = new Integer[2];
	        		String[] split = listNeighborsString.split(",");
					for (int i = 0; i < split.length; i++) {
						position[i] = Integer.valueOf(split[i].trim());
					}
	        							
	        		map.put(vertex, position);
	        	}
	        	
	        	line++;
	        }
	        
	        b.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return map;
	}
	
	
	
	
	public File writeFile(HashMap<Integer, Color> coloredGraph) throws IOException {
		File file = new File("resources/coloredGraph.colors");
		file.createNewFile();
		
		FileOutputStream outputStream = new FileOutputStream(file);
			
		outputStream.write((String.valueOf(coloredGraph.size()) + "\n").getBytes());
		
		for (Map.Entry<Integer, Color> entry : coloredGraph.entrySet()) {
			StringBuilder lineBuilder = new StringBuilder();
			lineBuilder.append(entry.getKey());
			lineBuilder.append(": ");
			lineBuilder.append(entry.getValue());
			lineBuilder.append("\n");
			
			outputStream.write(lineBuilder.toString().getBytes());
		}
		
		outputStream.close();
		
		return file;
	}
}
