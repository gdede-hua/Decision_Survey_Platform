package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class AlternativesFactorCrAhp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorId")
	private Factor factor;
	
	private float cr;

	public AlternativesFactorCrAhp() {
	}
	public AlternativesFactorCrAhp(Factor factor, float cr) {
		this.factor = factor;
		this.cr = cr;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Factor getFactor() {
		return factor;
	}
	public void setFactor(Factor factor) {
		this.factor = factor;
	}
	public float getCr() {
		return cr;
	}
	public void setCr(float cr) {
		this.cr = cr;
	}
}
