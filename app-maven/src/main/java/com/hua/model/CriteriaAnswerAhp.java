package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * the Criteria answer for the AHP algorithm
 * it save the weight for each answer
 * @author      John Nikolaou
 */
@Entity
public class CriteriaAnswerAhp {

	/**
	 * The id of the {@link CriteriaAnswerAhp}.
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
	 * The weight produce from AHP algorithm for that specific criteria.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public CriteriaAnswerAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param weight
	 */
	public CriteriaAnswerAhp(Criteria criteria, float weight) {
		this.criteria = criteria;
		this.weight = weight;
	}

	/** Get the id
	 * @return {@link CriteriaAnswerAhp#id}
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
	/** Get the id
	 * @return {@link CriteriaAnswerAhp#criteria}
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
	/** Get the id
	 * @return {@link CriteriaAnswerAhp#weight}
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
