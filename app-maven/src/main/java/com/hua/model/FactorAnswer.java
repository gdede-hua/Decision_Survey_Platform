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
 * the Factor answers
 * @author      John Nikolaou
 */
@Entity
public class FactorAnswer {

	/**
	 * The id of the {@link FactorAnswer}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The criterion which have that factors.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;
	/**
	 * The top compared factor.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorTopId")
	private Factor factorTop;
	/**
	 * The left compared factor.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorLeftId")
	private Factor factorLeft;
	/**
	 * the weight of the answer of the comparison.
	 */
	private float weight;

	/**
	 * Class default constructor.
	 */
	public FactorAnswer() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param factorTop
	 * @param factorLeft
	 * @param weight
	 */
	public FactorAnswer(Criteria criteria, Factor factorTop, Factor factorLeft, float weight) {
		this.criteria = criteria;
		this.factorTop = factorTop;
		this.factorLeft = factorLeft;
		this.weight = weight;
	}

	/** Get the id of factor answers.
	 * @return {@link FactorAnswer#id}
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
	/** Get the criterion of the factors.
	 * @return {@link FactorAnswer#criteria}
	 */
	public Criteria getCriteria() {
		return criteria;
	}
	/** Set the criterion of the factors.
	 * @param criteria {@link Criteria}
	 */
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	/** Get the top factor of the UI table.
	 * @return {@link FactorAnswer#factorTop}
	 */
	public Factor getFactorTop() {
		return factorTop;
	}
	/** Get the left factor of the UI table.
	 * @return {@link FactorAnswer#factorLeft}
	 */
	public Factor getFactorLeft() {
		return factorLeft;
	}
	/** Get the weight of factor.
	 * @return {@link FactorAnswer#weight}
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
	 * @return the factor answer data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
