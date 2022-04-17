package com.hua.model;

public class AHP {
	private double[] weights;
	private double cr;
	
	
	public AHP() {}
	public AHP(double[] weights, double cr) {
		this.weights = weights;
		this.cr = cr;
	}
	public double[] getWeights() {
		return weights;
	}
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	public double getCr() {
		return cr;
	}
	public void setCr(double cr) {
		this.cr = cr;
	}
	
	
}
