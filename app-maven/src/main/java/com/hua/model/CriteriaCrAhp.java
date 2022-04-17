package com.hua.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CriteriaCrAhp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private float cr;
	
	public CriteriaCrAhp() {}
	public CriteriaCrAhp(float cr) {
		this.cr = cr;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getCr() {
		return cr;
	}
	public void setCr(float cr) {
		this.cr = cr;
	}
	
}
