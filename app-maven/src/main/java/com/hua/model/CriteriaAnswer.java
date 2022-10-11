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
 * the Criteria answer
 */
@Entity
public class CriteriaAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaTopId")
	private Criteria criteriaTop;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaLeftId")
	private Criteria criteriaLeft;
	
	
	private float weight;

	public CriteriaAnswer() {}
	public CriteriaAnswer(Criteria criteriaTop, Criteria criteriaLeft, float weight) {
		this.criteriaTop = criteriaTop;
		this.criteriaLeft = criteriaLeft;
		this.weight = weight;
	}
	public CriteriaAnswer(int id, Criteria criteriaTop, Criteria criteriaLeft, float weight) {
		this.id = id;
		this.criteriaTop = criteriaTop;
		this.criteriaLeft = criteriaLeft;
		this.weight = weight;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Criteria getCriteriaTop() {
		return criteriaTop;
	}
	public void setCriteriaTop(Criteria criteriaTop) {
		this.criteriaTop = criteriaTop;
	}
	public Criteria getCriteriaLeft() {
		return criteriaLeft;
	}
	public void setCriteriaLeft(Criteria criteriaLeft) {
		this.criteriaLeft = criteriaLeft;
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
