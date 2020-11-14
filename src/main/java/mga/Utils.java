package mga;

import java.awt.Color;

public class Utils {

	public static String getColorName(Color c) {
		if (c.equals(Color.blue))
			return "Blue";
		else if (c.equals(Color.red))
			return "Red";
		else if (c.equals(Color.green))
			return "Green";
		else if (c.equals(Color.white))
			return "White";
		else if (c.equals(Color.black))
			return "Black";
		else
			return c.toString();
	}
}
