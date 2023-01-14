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
/**
 * Class for the assign survey results after execute AHP algorithm
 * @author      John Nikolaou
 */
@Entity
public class ProblemUserAhp {

	/**
	 * The id of the user problem after execute AHP algorithm.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The {@link ProblemUser} which we save the AHP algorithm results.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "problemUserId")
	private ProblemUser problemUser;
	/**
	 * The {@link CriteriaAnswerAhp} of the user problem after execute AHP algorithm.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<CriteriaAnswerAhp> criteriaAnswerAhp;
	/**
	 * The {@link CriteriaCrAhp} is the value that describes the answers' accuracy.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "criteria_cr_ahp_id", referencedColumnName = "id")
	private CriteriaCrAhp criteriaCrAhp;
	/**
	 * The {@link FactorAnswerAhp} of the user problem after execute AHP algorithm.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<FactorAnswerAhp> factorAnswerAhp;
	/**
	 * The {@link FactorCrAhp} is the value that describes the answers' accuracy.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<FactorCrAhp> factorCrAhp;
	/**
	 * The {@link AlternativesCriteriaAnswerAhp} of the user problem after execute AHP algorithm.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAhp;
	/**
	 * The {@link AlternativesFactorAnswerAhp} is the value that describes the answers' accuracy.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAhp;
	/**
	 * The {@link AlternativesFactorAnswerAhp} of the user problem after execute AHP algorithm.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAhp;
	/**
	 * The {@link AlternativesFactorCrAhp} is the value that describes the answers' accuracy.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserAhpId")
	private List<AlternativesFactorCrAhp> alternativesFactorCrAhp;

	/**
	 * Class default constructor.
	 */
	public ProblemUserAhp() {}
	/**
	 * Class constructor.
	 * @param problemUser {@link ProblemUser}
	 * @param criteriaAnswerAhp {@link CriteriaAnswerAhp}
	 * @param factorAnswerAhp {@link FactorAnswerAhp}
	 * @param alternativesCriteriaAnswerAhp {@link AlternativesCriteriaAnswerAhp}
	 * @param alternativesFactorAnswerAhp {@link AlternativesFactorAnswerAhp}
	 */
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

	/** Get the id of the user problem after execute AHP algorithm.
	 * @return {@link ProblemUserAhp#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id of the user problem for the results of AHP algorithm.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the user problem.
	 * @return {@link ProblemUserAhp#problemUser}
	 */
	public ProblemUser getProblemUser() {
		return problemUser;
	}
	/** Set the user problem of the user problem for the results of AHP algorithm.
	 * @param problemUser {@link ProblemUser}
	 */
	public void setProblemUser(ProblemUser problemUser) {
		this.problemUser = problemUser;
	}
	/** Get the results of the criteria answers' after execute AHP algorithm.
	 * @return {@link ProblemUserAhp#criteriaAnswerAhp}
	 */
	public List<CriteriaAnswerAhp> getCriteriaAnswerAhp() {
		return criteriaAnswerAhp;
	}
	/** Set the criteria answer of the user problem for the results of AHP algorithm.
	 * @param criteriaAnswerAhp {@link CriteriaAnswerAhp}
	 */
	public void setCriteriaAnswerAhp(List<CriteriaAnswerAhp> criteriaAnswerAhp) {
		this.criteriaAnswerAhp = criteriaAnswerAhp;
	}
	/** Get the accuracy of the criteria answers.
	 * @return {@link ProblemUserAhp#criteriaCrAhp}
	 */
	public CriteriaCrAhp getCriteriaCrAhp() {
		return criteriaCrAhp;
	}
	/** Set the accuracy of the criteria answer of the user problem.
	 * @param criteriaCrAhp {@link CriteriaCrAhp}
	 */
	public void setCriteriaCrAhp(CriteriaCrAhp criteriaCrAhp) {
		this.criteriaCrAhp = criteriaCrAhp;
	}
	/** Get the results of the factor answers' after execute AHP algorithm.
	 * @return {@link ProblemUserAhp#factorAnswerAhp}
	 */
	public List<FactorAnswerAhp> getFactorAnswerAhp() {
		return factorAnswerAhp;
	}
	/** Set the factor answer of the user problem for the results of AHP algorithm.
	 * @param factorAnswerAhp {@link FactorAnswerAhp}
	 */
	public void setFactorAnswerAhp(List<FactorAnswerAhp> factorAnswerAhp) {
		this.factorAnswerAhp = factorAnswerAhp;
	}
	/** Get the accuracy of the factor answers.
	 * @return {@link ProblemUserAhp#factorAnswerAhp}
	 */
	public List<FactorCrAhp> getFactorCrAhp() {
		return factorCrAhp;
	}
	/** Set the accuracy of the factor answer of the user problem.
	 * @param factorCrAhp {@link FactorCrAhp}
	 */
	public void setFactorCrAhp(List<FactorCrAhp> factorCrAhp) {
		this.factorCrAhp = factorCrAhp;
	}
	/** Get the results of the alternatives criteria answers' after execute AHP algorithm.
	 * @return {@link ProblemUserAhp#alternativesCriteriaAnswerAhp}
	 */
	public List<AlternativesCriteriaAnswerAhp> getAlternativesCriteriaAnswerAhp() {
		return alternativesCriteriaAnswerAhp;
	}
	/** Set the alternatives criteria answer of the user problem for the results of AHP algorithm.
	 * @param alternativesCriteriaAnswerAhp {@link AlternativesCriteriaAnswerAhp}
	 */
	public void setAlternativesCriteriaAnswerAhp(List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAhp) {
		this.alternativesCriteriaAnswerAhp = alternativesCriteriaAnswerAhp;
	}
	/** Get the accuracy of the alternatives criteria answers.
	 * @return {@link ProblemUserAhp#alternativesCriteriaCrAhp}
	 */
	public List<AlternativesCriteriaCrAhp> getAlternativesCriteriaCrAhp() {
		return alternativesCriteriaCrAhp;
	}
	/** Set the accuracy of the alternatives criteria answer of the user problem.
	 * @param alternativesCriteriaCrAhp {@link AlternativesCriteriaAnswerAhp}
	 */
	public void setAlternativesCriteriaCrAhp(List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAhp) {
		this.alternativesCriteriaCrAhp = alternativesCriteriaCrAhp;
	}
	/** Get the results of the alternatives factor answers' after execute AHP algorithm.
	 * @return {@link ProblemUserAhp#alternativesFactorAnswerAhp}
	 */
	public List<AlternativesFactorAnswerAhp> getAlternativesFactorAnswerAhp() {
		return alternativesFactorAnswerAhp;
	}
	/** Set the alternatives factor answer of the user problem for the results of AHP algorithm.
	 * @param alternativesFactorAnswerAhp {@link AlternativesFactorAnswerAhp}
	 */
	public void setAlternativesFactorAnswerAhp(List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAhp) {
		this.alternativesFactorAnswerAhp = alternativesFactorAnswerAhp;
	}
	/** Get the accuracy of the alternatives factor answers.
	 * @return {@link ProblemUserAhp#id}
	 */
	public List<AlternativesFactorCrAhp> getAlternativesFactorCrAhp() {
		return alternativesFactorCrAhp;
	}
	/** Set the accuracy of the alternatives factor answer of the user problem.
	 * @param alternativesFactorCrAhp {@link AlternativesFactorAnswerAhp}
	 */
	public void setAlternativesFactorCrAhp(List<AlternativesFactorCrAhp> alternativesFactorCrAhp) {
		this.alternativesFactorCrAhp = alternativesFactorCrAhp;
	}
	
}
