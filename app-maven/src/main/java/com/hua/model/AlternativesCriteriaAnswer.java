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
 * the Alternatives Criteria answers
 * @author      John Nikolaou
 */
@Entity
public class AlternativesCriteriaAnswer {

	/**
	 * The id of the {@link AlternativesCriteriaAnswer}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The criterion which we compare that alternatives.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;
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
	public AlternativesCriteriaAnswer() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param alternativesTop
	 * @param alternativesLeft
	 * @param weight
	 */
	public AlternativesCriteriaAnswer(Criteria criteria, Alternatives alternativesTop,
			Alternatives alternativesLeft, float weight) {
		this.criteria = criteria;
		this.alternativesTop = alternativesTop;
		this.alternativesLeft = alternativesLeft;
		this.weight = weight;
	}

	/** Get the id of alternatives criteria answers.
	 * @return {@link AlternativesCriteriaAnswer#id}
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
	/** Get the criterion of the alternatives.
	 * @return {@link AlternativesCriteriaAnswer#criteria}
	 */
	public Criteria getCriteria() {
		return criteria;
	}
	/** Set the criterion of the alternatives.
	 * @param criteria {@link Criteria}
	 */
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	/** Get the top alternative of the UI table.
	 * @return {@link AlternativesCriteriaAnswer#alternativesTop}
	 */
	public Alternatives getAlternativesTop() {
		return alternativesTop;
	}
	/** Get the left alternative of the UI table.
	 * @return {@link AlternativesCriteriaAnswer#alternativesLeft}
	 */
	public Alternatives getAlternativesLeft() {
		return alternativesLeft;
	}
	/** Get the weight of alternative.
	 * @return {@link AlternativesCriteriaAnswer#weight}
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
	 * @return the alternatives criteria answer data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
