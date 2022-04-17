package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class FactorCrAhp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;

	private float cr;

	public FactorCrAhp() {}
	public FactorCrAhp(Criteria criteria, float cr) {
		this.criteria = criteria;
		this.cr = cr;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	public float getCr() {
		return cr;
	}
	public void setCr(float cr) {
		this.cr = cr;
	}
	
}
