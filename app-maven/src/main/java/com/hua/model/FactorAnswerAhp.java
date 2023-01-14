package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * the Factor answers for the AHP algorithm
 * it save the weight for each answer
 * @author      John Nikolaou
 */
@Entity
public class FactorAnswerAhp {

	/**
	 * The id of the {@link FactorAnswerAhp}.
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
	 * The factor item
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	/**
	 * The weight produce from AHP algorithm for that specific factor of criteria.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public FactorAnswerAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param factor
	 * @param weight
	 */
	public FactorAnswerAhp(Criteria criteria, Factor factor, float weight) {
		this.factor = factor;
		this.criteria = criteria;
		this.weight = weight;
	}

	/** Get the id
	 * @return {@link FactorAnswerAhp#id}
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
	 * @return {@link FactorAnswerAhp#factor}
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
	/** Get the criteria
	 * @return {@link FactorAnswerAhp#criteria}
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
	/** Get the weight
	 * @return {@link FactorAnswerAhp#weight}
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
