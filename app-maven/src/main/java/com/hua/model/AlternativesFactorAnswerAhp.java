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
 * @author      John Nikolaou
 */
@Entity
public class AlternativesFactorAnswerAhp {

	/**
	 * The id of the {@link AlternativesFactorAnswerAhp}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The factor item
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	/**
	 * The alternative item
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesId")
	private Alternatives alternatives;
	/**
	 * The weight produce from AHP algorithm for that specific alternative of factor.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public AlternativesFactorAnswerAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param factor
	 * @param alternatives
	 * @param weight
	 */
	public AlternativesFactorAnswerAhp(Factor factor, Alternatives alternatives, float weight) {
		this.factor = factor;
		this.alternatives = alternatives;
		this.weight = weight;
	}

	/** Get the id
	 * @return {@link AlternativesFactorAnswerAhp#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the factor
	 * @return {@link AlternativesFactorAnswerAhp#factor}
	 */
	public Factor getFactor() {
		return factor;
	}
	/** Set the factor.
	 * @param factor
	 */
	public void setFactor(Factor factor) {
		this.factor = factor;
	}
	/** Get the alternatives
	 * @return {@link AlternativesFactorAnswerAhp#alternatives}
	 */
	public Alternatives getAlternatives() {
		return alternatives;
	}
	/** Set the alternatives.
	 * @param alternatives
	 */
	public void setAlternatives(Alternatives alternatives) {
		this.alternatives = alternatives;
	}
	/** Get the weight
	 * @return {@link AlternativesFactorAnswerAhp#weight}
	 */
	public float getWeight() {
		return weight;
	}
	/** Set the weight.
	 * @param weight
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
