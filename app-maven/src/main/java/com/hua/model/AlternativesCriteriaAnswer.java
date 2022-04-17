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
public class AlternativesCriteriaAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesTopId")
	private Alternatives alternativesTop;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesLeftId")
	private Alternatives alternativesLeft;
	
	
	private float weight;

	public AlternativesCriteriaAnswer() {}
	public AlternativesCriteriaAnswer(int id, Criteria criteria, Alternatives alternativesTop,
			Alternatives alternativesLeft, float weight) {
		this.id = id;
		this.criteria = criteria;
		this.alternativesTop = alternativesTop;
		this.alternativesLeft = alternativesLeft;
		this.weight = weight;
	}
	public AlternativesCriteriaAnswer(Criteria criteria, Alternatives alternativesTop,
			Alternatives alternativesLeft, float weight) {
		this.criteria = criteria;
		this.alternativesTop = alternativesTop;
		this.alternativesLeft = alternativesLeft;
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
	public Alternatives getAlternativesTop() {
		return alternativesTop;
	}
	public void setAlternativesTop(Alternatives alternativesTop) {
		this.alternativesTop = alternativesTop;
	}
	public Alternatives getAlternativesLeft() {
		return alternativesLeft;
	}
	public void setAlternativesLeft(Alternatives alternativesLeft) {
		this.alternativesLeft = alternativesLeft;
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
