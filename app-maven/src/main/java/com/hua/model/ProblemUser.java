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

import com.google.gson.Gson;
/**
 * Class for the assign survey to users
 * @author      John Nikolaou
 */
@Entity
public class ProblemUser {

	/**
	 * The id of the user problem.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The {@link Users} which the {@link Problem} had assign.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	/**
	 * The status of the user problem.
	 */
	private int status;
	/**
	 * The {@link Problem} which user call to solve.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "problemId")
	private Problem problem;
	/**
	 * The {@link CriteriaAnswer} of the user for the problem.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserId")
	private List<CriteriaAnswer> criteriaAnswer;
	/**
	 * The {@link FactorAnswer} of the user for the problem.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemUserId")
	private List<FactorAnswer> factorAnswer;
	/**
	 * The {@link AlternativesCriteriaAnswer} of the user for the problem.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemUserId")
	private List<AlternativesCriteriaAnswer> alternativesCriteriaAnswer;
	/**
	 * The {@link AlternativesFactorAnswer} of the user for the problem.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemUserId")
	private List<AlternativesFactorAnswer> alternativesFactorAnswer;

	/**
	 * Class default constructor.
	 */
	public ProblemUser() {}
	/**
	 * Class constructor.
	 * @param id
	 */
	public ProblemUser(int id) {
		this.id = id;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param user {@link Users}
	 */
	public ProblemUser(int id, Users user) {
		this.id = id;
		this.user = user;
	}
	/**
	 * Class constructor.
	 * @param user {@link Users}
	 * @param status
	 * @param problem {@link Problem}
	 */
	public ProblemUser(Users user, int status, Problem problem) {
		this.user = user;
		this.status = status;
		this.problem = problem;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param user {@link Users}
	 * @param status
	 * @param problem {@link Problem}
	 * @param criteriaAnswer {@link CriteriaAnswer}
	 * @param factorAnswer {@link FactorAnswer}
	 * @param alternativesCriteriaAnswer {@link AlternativesCriteriaAnswer}
	 * @param alternativesFactorAnswer {@link AlternativesFactorAnswer}
	 */
	public ProblemUser(int id, Users user, int status, Problem problem, 
			List<CriteriaAnswer> criteriaAnswer, 
			List<FactorAnswer> factorAnswer,
			List<AlternativesCriteriaAnswer> alternativesCriteriaAnswer,
			List<AlternativesFactorAnswer> alternativesFactorAnswer
		) {
		this.id = id;
		this.user = user;
		this.status = status;
		this.problem = problem;
		this.criteriaAnswer = criteriaAnswer;
		this.factorAnswer = factorAnswer;
		this.alternativesCriteriaAnswer = alternativesCriteriaAnswer;
		this.alternativesFactorAnswer = alternativesFactorAnswer;
	}

	/** Get the id of the user problem.
	 * @return {@link ProblemUser#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id of the user problem.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the assign user of the user problem.
	 * @return {@link ProblemUser#user}
	 */
	public Users getUser() {
		return user;
	}
	/** Set the user of the user problem.
	 * @param user {@link Users}
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	/** Get the status of the user problem.
	 * @return {@link ProblemUser#status}
	 */
	public int getStatus() {
		return status;
	}
	/** Set the status of the user problem.
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/** Get the problem of the user problem.
	 * @return {@link ProblemUser#problem}
	 */
	public Problem getProblem() {
		return problem;
	}
	/** Set the problem of the user problem.
	 * @param problem {@link Problem}
	 */
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	/** Get the criteria answers of the user problem.
	 * @return {@link ProblemUser#criteriaAnswer}
	 */
	public List<CriteriaAnswer> getCriteriaAnswer() {
		return criteriaAnswer;
	}
	/** Set the criteria answers of the user problem.
	 * @param criteriaAnswer {@link CriteriaAnswer}
	 */
	public void setCriteriaAnswer(List<CriteriaAnswer> criteriaAnswer) {
		this.criteriaAnswer = criteriaAnswer;
	}
	/** Get the factor answers of the user problem.
	 * @return {@link ProblemUser#factorAnswer}
	 */
	public List<FactorAnswer> getFactorAnswer() {
		return factorAnswer;
	}
	/** Set the factor answer of the user problem.
	 * @param factorAnswer {@link FactorAnswer}
	 */
	public void setFactorAnswer(List<FactorAnswer> factorAnswer) {
		this.factorAnswer = factorAnswer;
	}
	/** Get the alternatives criteria answers of the user problem.
	 * @return {@link ProblemUser#alternativesCriteriaAnswer}
	 */
	public List<AlternativesCriteriaAnswer> getAlternativesCriteriaAnswer() {
		return alternativesCriteriaAnswer;
	}
	/** Set the alternatives criteria answer of the user problem.
	 * @param alternativesCriteriaAnswer {@link AlternativesCriteriaAnswer}
	 */
	public void setAlternativesCriteriaAnswer(List<AlternativesCriteriaAnswer> alternativesCriteriaAnswer) {
		this.alternativesCriteriaAnswer = alternativesCriteriaAnswer;
	}
	/** Get the alternatives factor answers of the user problem.
	 * @return {@link ProblemUser#alternativesFactorAnswer}
	 */
	public List<AlternativesFactorAnswer> getAlternativesFactorAnswer() {
		return alternativesFactorAnswer;
	}
	/** Set the Alternatives factor answer of the user problem.
	 * @param alternativesFactorAnswer {@link AlternativesFactorAnswer}
	 */
	public void setAlternativesFactorAnswer(List<AlternativesFactorAnswer> alternativesFactorAnswer) {
		this.alternativesFactorAnswer = alternativesFactorAnswer;
	}
	/**
	 * @return the user problem data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
