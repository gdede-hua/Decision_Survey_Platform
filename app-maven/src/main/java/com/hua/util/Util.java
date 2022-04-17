package com.hua.util;

public class Util {

	public static float weightReverse(float weight) {
		float weightTmp = weight;
		String weightString = ""+weight;
		if ( weight<1 ) {
			if (weightString.equals("0.5"))
				weightTmp = 2;
			else if ( weightString.equals("0.33") )
				weightTmp = 3;
			else if (weightString.equals("0.25"))
				weightTmp = 4;
			else if (weightString.equals("0.2"))
				weightTmp = 5;
			else if (weightString.equals("0.16") )
				weightTmp = 6;
			else if (weightString.equals("0.14") )
				weightTmp = 7;
			else if (weightString.equals("0.12") )
				weightTmp = 8;
			else if (weightString.equals("0.11"))
				weightTmp = 9;
		} else
			weightTmp = 1/weight;
		return weightTmp;
	}
}
