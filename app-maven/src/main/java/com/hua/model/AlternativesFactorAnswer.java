package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.google.gson.Gson;

@Entity
public class AlternativesFactorAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesTopId")
	private Alternatives alternativesTop;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesLeftId")
	private Alternatives alternativesLeft;
	
	
	private float weight;

	public AlternativesFactorAnswer() {}
	public AlternativesFactorAnswer(int id, Factor factor, Alternatives alternativesTop, Alternatives alternativesLeft,
			float weight) {
		this.id = id;
		this.factor = factor;
		this.alternativesTop = alternativesTop;
		this.alternativesLeft = alternativesLeft;
		this.weight = weight;
	}
	public AlternativesFactorAnswer(Factor factor, Alternatives alternativesTop, Alternatives alternativesLeft,
			float weight) {
		this.factor = factor;
		this.alternativesTop = alternativesTop;
		this.alternativesLeft = alternativesLeft;
		this.weight = weight;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Factor getFactor() {
		return factor;
	}
	public void setFactor(Factor factor) {
		this.factor = factor;
	}
	public Alternatives getAlternativesTop() {
		return alternativesTop;
	}
	public void setAlternativesTop(Alternatives alternativesTop) {
		this.alternativesTop = alternativesTop;
	}
	public Alternatives getAlternativesLeft() {
		return alternativesLeft;
	}
	public void setAlternativesLeft(Alternatives alternativesLeft) {
		this.alternativesLeft = alternativesLeft;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
