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
 * it save the weight for each answer
 */
@Entity
public class AlternativesCriteriaAnswerAhp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "criteriaId")
	private Criteria criteria;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "alternativesId")
	private Alternatives alternatives;
	
	private float weight;

	public AlternativesCriteriaAnswerAhp() {}
	public AlternativesCriteriaAnswerAhp(Criteria criteria, Alternatives alternatives, float weight ) {
		this.criteria = criteria;
		this.alternatives = alternatives;
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
	public Alternatives getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(Alternatives alternatives) {
		this.alternatives = alternatives;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
