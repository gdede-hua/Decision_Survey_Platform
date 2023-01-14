package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.google.gson.Gson;
/**
 * the Alternatives Factor answers
 * @author      John Nikolaou
 */
@Entity
public class AlternativesFactorAnswer {

	/**
	 * The id of the {@link AlternativesFactorAnswer}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The factor which we compare that alternatives.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	/**
	 * The top compared alternative.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesTopId")
	private Alternatives alternativesTop;
	/**
	 * The left compared alternative.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesLeftId")
	private Alternatives alternativesLeft;
	/**
	 * the weight of the answer of the comparison.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public AlternativesFactorAnswer() {}
	/**
	 * Class constructor.
	 *
	 * @param factor
	 * @param alternativesTop
	 * @param alternativesLeft
	 * @param weight
	 */
	public AlternativesFactorAnswer(Factor factor, Alternatives alternativesTop, Alternatives alternativesLeft,
			float weight) {
		this.factor = factor;
		this.alternativesTop = alternativesTop;
		this.alternativesLeft = alternativesLeft;
		this.weight = weight;
	}

	/** Get the id of alternatives factors answers.
	 * @return {@link AlternativesFactorAnswer#id}
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
	/** Get the factor of the alternatives.
	 * @return {@link AlternativesFactorAnswer#factor}
	 */
	public Factor getFactor() {
		return factor;
	}
	/** Set the factor of the alternatives.
	 * @param factor {@link Factor}
	 */
	public void setFactor(Factor factor) {
		this.factor = factor;
	}
	/** Get the top alternative of the UI table.
	 * @return {@link AlternativesFactorAnswer#alternativesTop}
	 */
	public Alternatives getAlternativesTop() {
		return alternativesTop;
	}
	/** Get the left alternative of the UI table.
	 * @return {@link AlternativesFactorAnswer#alternativesLeft}
	 */
	public Alternatives getAlternativesLeft() {
		return alternativesLeft;
	}
	/** Get the weight of alternative.
	 * @return {@link AlternativesFactorAnswer#weight}
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
	/**
	 * @return the alternatives factor answer data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
