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
 * the Criteria answer
 * @author      John Nikolaou
 */
@Entity
public class CriteriaAnswer {

	/**
	 * The id of the {@link CriteriaAnswer}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The top compared criterion.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaTopId")
	private Criteria criteriaTop;
	/**
	 * The left compared criterion.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaLeftId")
	private Criteria criteriaLeft;
	/**
	 * the weight of the answer of the comparison.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public CriteriaAnswer() {}
	/**
	 * Class constructor.
	 *
	 * @param criteriaTop
	 * @param criteriaLeft
	 * @param weight
	 */
	public CriteriaAnswer(Criteria criteriaTop, Criteria criteriaLeft, float weight) {
		this.criteriaTop = criteriaTop;
		this.criteriaLeft = criteriaLeft;
		this.weight = weight;
	}

	/** Get the id of criteria answers.
	 * @return {@link CriteriaAnswer#id}
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
	/** Get the top criterion of the UI table.
	 * @return {@link CriteriaAnswer#criteriaTop}
	 */
	public Criteria getCriteriaTop() {
		return criteriaTop;
	}
	/** Get the left criterion of the UI table.
	 * @return {@link CriteriaAnswer#criteriaLeft}
	 */
	public Criteria getCriteriaLeft() {
		return criteriaLeft;
	}
	/** Get the weight of criteria.
	 * @return {@link CriteriaAnswer#weight}
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
	 * @return the criteria answer data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
