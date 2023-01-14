package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * the Alternatives Factor answers for the AHP algorithm
 * it save the CR
 * @author      John Nikolaou
 */
@Entity
public class AlternativesFactorCrAhp {
	/**
	 * The id of the {@link AlternativesFactorCrAhp}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The factor item
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	/**
	 * The CR produce from AHP algorithm for the alternative of factors.
	 */
	private float cr;

	/**
	 * Class default constructor.
	 */
	public AlternativesFactorCrAhp() {}
	/**
	 * Class constructor.
	 *
	 * @param factor
	 * @param cr
	 */
	public AlternativesFactorCrAhp(Factor factor, float cr) {
		this.factor = factor;
		this.cr = cr;
	}

	/** Get the id
	 * @return {@link AlternativesFactorCrAhp#id}
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
	 * @return {@link AlternativesFactorCrAhp#factor}
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
	/** Get the cr
	 * @return {@link AlternativesFactorCrAhp#cr}
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
