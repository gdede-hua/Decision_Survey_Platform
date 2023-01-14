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
 * it save the CR
 * @author      John Nikolaou
 */
@Entity
public class FactorCrAhp {
	/**
	 * The id of the {@link FactorCrAhp}.
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
	 * The CR produce from AHP algorithm for the factors of criteria.
	 */
	private float cr;

	/**
	 * Class default constructor.
	 */
	public FactorCrAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param cr
	 */
	public FactorCrAhp(Criteria criteria, float cr) {
		this.criteria = criteria;
		this.cr = cr;
	}

	/** Get the id
	 * @return {@link FactorCrAhp#id}
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
	 * @return {@link FactorCrAhp#criteria}
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
	/** Get the cr
	 * @return {@link FactorCrAhp#cr}
	 */
	public float getCr() {
		return cr;
	}
	/** Set the cr.
	 * @param cr
	 */
	public void setCr(float cr) {
		this.cr = cr;
	}

}
