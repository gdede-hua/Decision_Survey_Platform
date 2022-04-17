package com.hua.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ProblemUserAhp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "problemUserId")
	private ProblemUser problemUser;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<CriteriaAnswerAhp> criteriaAnswerAhp;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "criteria_cr_ahp_id", referencedColumnName = "id")
	private CriteriaCrAhp criteriaCrAhp;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<FactorAnswerAhp> factorAnswerAhp;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<FactorCrAhp> factorCrAhp;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAhp;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAhp;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAhp;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesFactorCrAhp> alternativesFactorCrAhp;

	public ProblemUserAhp() {}
	public ProblemUserAhp(ProblemUser problemUser,
			List<CriteriaAnswerAhp> criteriaAnswerAhp, List<FactorAnswerAhp> factorAnswerAhp, 
			List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAhp, 
			List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAhp) {
		this.problemUser = problemUser;
		this.criteriaAnswerAhp = criteriaAnswerAhp;
		this.factorAnswerAhp = factorAnswerAhp;
		this.alternativesCriteriaAnswerAhp = alternativesCriteriaAnswerAhp;
		this.alternativesFactorAnswerAhp = alternativesFactorAnswerAhp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProblemUser getProblemUser() {
		return problemUser;
	}
	public void setProblemUser(ProblemUser problemUser) {
		this.problemUser = problemUser;
	}
	public List<CriteriaAnswerAhp> getCriteriaAnswerAhp() {
		return criteriaAnswerAhp;
	}
	public void setCriteriaAnswerAhp(List<CriteriaAnswerAhp> criteriaAnswerAhp) {
		this.criteriaAnswerAhp = criteriaAnswerAhp;
	}
	public CriteriaCrAhp getCriteriaCrAhp() {
		return criteriaCrAhp;
	}
	public void setCriteriaCrAhp(CriteriaCrAhp criteriaCrAhp) {
		this.criteriaCrAhp = criteriaCrAhp;
	}
	public List<FactorAnswerAhp> getFactorAnswerAhp() {
		return factorAnswerAhp;
	}
	public void setFactorAnswerAhp(List<FactorAnswerAhp> factorAnswerAhp) {
		this.factorAnswerAhp = factorAnswerAhp;
	}
	public List<FactorCrAhp> getFactorCrAhp() {
		return factorCrAhp;
	}
	public void setFactorCrAhp(List<FactorCrAhp> factorCrAhp) {
		this.factorCrAhp = factorCrAhp;
	}
	public List<AlternativesCriteriaAnswerAhp> getAlternativesCriteriaAnswerAhp() {
		return alternativesCriteriaAnswerAhp;
	}
	public void setAlternativesCriteriaAnswerAhp(List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAhp) {
		this.alternativesCriteriaAnswerAhp = alternativesCriteriaAnswerAhp;
	}
	public List<AlternativesCriteriaCrAhp> getAlternativesCriteriaCrAhp() {
		return alternativesCriteriaCrAhp;
	}
	public void setAlternativesCriteriaCrAhp(List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAhp) {
		this.alternativesCriteriaCrAhp = alternativesCriteriaCrAhp;
	}
	public List<AlternativesFactorAnswerAhp> getAlternativesFactorAnswerAhp() {
		return alternativesFactorAnswerAhp;
	}
	public void setAlternativesFactorAnswerAhp(List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAhp) {
		this.alternativesFactorAnswerAhp = alternativesFactorAnswerAhp;
	}
	public List<AlternativesFactorCrAhp> getAlternativesFactorCrAhp() {
		return alternativesFactorCrAhp;
	}
	public void setAlternativesFactorCrAhp(List<AlternativesFactorCrAhp> alternativesFactorCrAhp) {
		this.alternativesFactorCrAhp = alternativesFactorCrAhp;
	}
	
}
