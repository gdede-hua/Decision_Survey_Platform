package com.hua.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * the Criteria answer for the AHP algorithm
 * it save the CR
 * @author      John Nikolaou
 */
@Entity
public class CriteriaCrAhp {
	/**
	 * The id of the {@link CriteriaCrAhp}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The CR produce from AHP algorithm for the criteria.
	 */
	private float cr;

	/**
	 * Class default constructor.
	 */
	public CriteriaCrAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param cr
	 */
	public CriteriaCrAhp(float cr) {
		this.cr = cr;
	}

	/** Get the id
	 * @return {@link CriteriaCrAhp#id}
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
	/** Get the cr
	 * @return {@link CriteriaCrAhp#cr}
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
