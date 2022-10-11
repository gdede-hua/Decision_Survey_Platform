package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * the Alternatives Factor answers for the AHP algorithm
 * it save the weight for each answer
 */
@Entity
public class AlternativesFactorAnswerAhp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesId")
	private Alternatives alternatives;

	private float weight;

	public AlternativesFactorAnswerAhp() {}
	public AlternativesFactorAnswerAhp(Factor factor, Alternatives alternatives, float weight) {
		this.factor = factor;
		this.alternatives = alternatives;
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
	public Alternatives getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(Alternatives alternatives) {
		this.alternatives = alternatives;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
