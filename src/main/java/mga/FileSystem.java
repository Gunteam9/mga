package mga;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return map;
	}
}
