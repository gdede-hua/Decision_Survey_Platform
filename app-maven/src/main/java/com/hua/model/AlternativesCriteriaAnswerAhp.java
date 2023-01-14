package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * the Alternatives Criteria answers for the AHP algorithm
 * it save the weight for each answer
 * @author      John Nikolaou
 */
@Entity
public class AlternativesCriteriaAnswerAhp {

	/**
	 * The id of the {@link AlternativesCriteriaAnswerAhp}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The criteria item
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;
	/**
	 * The alternative item
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesId")
	private Alternatives alternatives;
	/**
	 * The weight produce from AHP algorithm for that specific alternative of criteria.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public AlternativesCriteriaAnswerAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param alternatives
	 * @param weight
	 */
	public AlternativesCriteriaAnswerAhp(Criteria criteria, Alternatives alternatives, float weight ) {
		this.criteria = criteria;
		this.alternatives = alternatives;
		this.weight = weight;
	}

	/** Get the id
	 * @return {@link AlternativesCriteriaAnswerAhp#id}
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
	/** Get the criteria
	 * @return {@link AlternativesCriteriaAnswerAhp#criteria}
	 */
	public Criteria getCriteria() {
		return criteria;
	}
	/** Set the criteria.
	 * @param criteria
	 */
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	/** Get the alternatives
	 * @return {@link AlternativesCriteriaAnswerAhp#alternatives}
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
	 * @return {@link AlternativesCriteriaAnswerAhp#weight}
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
