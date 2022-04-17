package com.hua.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.google.gson.Gson;

@Entity
public class FactorAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorTopId")
	private Factor factorTop;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "factorLeftId")
	private Factor factorLeft;
	
	private float weight;

	public FactorAnswer() {}
	public FactorAnswer(int id, Criteria criteria, Factor factorTop, Factor factorLeft, float weight) {
		this.id = id;
		this.criteria = criteria;
		this.factorTop = factorTop;
		this.factorLeft = factorLeft;
		this.weight = weight;
	}
	public FactorAnswer(Criteria criteria, Factor factorTop, Factor factorLeft, float weight) {
		this.criteria = criteria;
		this.factorTop = factorTop;
		this.factorLeft = factorLeft;
		this.weight = weight;
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
	public Factor getFactorTop() {
		return factorTop;
	}
	public void setFactorTop(Factor factorTop) {
		this.factorTop = factorTop;
	}
	public Factor getFactorLeft() {
		return factorLeft;
	}
	public void setFactorLeft(Factor factorLeft) {
		this.factorLeft = factorLeft;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
