package com.hua.util;

import Jama.EigenvalueDecomposition;

/**
 * Used to centralize some generic functions
 * @author      John Nikolaou
 */
public class Util {
	/**
	 * Used to find the opposite value of each answer of the user
	 *
	 * @param weight the weight of the answer
	 */
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

	/**
	 * From the numbers of the row dimension we get the ri value
	 *
	 * @param e the eigenvalue decomposition
	 * @return the ri value
	 */
	public static double getRi(EigenvalueDecomposition e) {
		double ri = 0;
		if (e.getV().getRowDimension() == 1 || e.getV().getRowDimension() == 2) {
			ri = 0;
		} else if (e.getV().getRowDimension() == 3) {
			ri = 0.52;
		} else if (e.getV().getRowDimension() == 4) {
			ri = 0.89;
		} else if (e.getV().getRowDimension() == 5) {
			ri = 1.11;
		} else if (e.getV().getRowDimension() == 6) {
			ri = 1.25;
		} else if (e.getV().getRowDimension() == 7) {
			ri = 1.35;
		} else if (e.getV().getRowDimension() == 8) {
			ri = 1.4;
		} else if (e.getV().getRowDimension() == 9) {
			ri = 1.45;
		} else if (e.getV().getRowDimension() == 10) {
			ri = 1.49;
		}
		return ri;
	}
}
