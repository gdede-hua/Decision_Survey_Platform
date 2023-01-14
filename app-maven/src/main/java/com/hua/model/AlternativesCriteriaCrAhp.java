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
 * it save the CR
 * @author      John Nikolaou
 */
@Entity
public class AlternativesCriteriaCrAhp {
	/**
	 * The id of the {@link AlternativesCriteriaCrAhp}.
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
	 * The CR produce from AHP algorithm for the alternative of criteria.
	 */
	private float cr;

	/**
	 * Class default constructor.
	 */
	public AlternativesCriteriaCrAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param criteria
	 * @param cr
	 */
	public AlternativesCriteriaCrAhp(Criteria criteria, float cr) {
		super();
		this.criteria = criteria;
		this.cr = cr;
	}

	/** Get the id
	 * @return {@link AlternativesCriteriaCrAhp#id}
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
	 * @return {@link AlternativesCriteriaCrAhp#criteria}
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
	 * @return {@link AlternativesCriteriaCrAhp#cr}
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
