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

@Entity
public class ProblemUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	private int status;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "problemId")
	private Problem problem;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemUserId")
	private List<CriteriaAnswer> criteriaAnswer;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemUserId")
	private List<FactorAnswer> factorAnswer;

	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemUserId")
	private List<AlternativesCriteriaAnswer> alternativesCriteriaAnswer;

	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemUserId")
	private List<AlternativesFactorAnswer> alternativesFactorAnswer;
	
	public ProblemUser() {}
	public ProblemUser(int id) {
		this.id = id;
	}
	public ProblemUser(int id, Users user) {
		this.id = id;
		this.user = user;
	}
	public ProblemUser(Users user, int status, Problem problem) {
		this.user = user;
		this.status = status;
		this.problem = problem;
	}
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public List<CriteriaAnswer> getCriteriaAnswer() {
		return criteriaAnswer;
	}
	public void setCriteriaAnswer(List<CriteriaAnswer> criteriaAnswer) {
		this.criteriaAnswer = criteriaAnswer;
	}
	public List<FactorAnswer> getFactorAnswer() {
		return factorAnswer;
	}
	public void setFactorAnswer(List<FactorAnswer> factorAnswer) {
		this.factorAnswer = factorAnswer;
	}
	public List<AlternativesCriteriaAnswer> getAlternativesCriteriaAnswer() {
		return alternativesCriteriaAnswer;
	}
	public void setAlternativesCriteriaAnswer(List<AlternativesCriteriaAnswer> alternativesCriteriaAnswer) {
		this.alternativesCriteriaAnswer = alternativesCriteriaAnswer;
	}
	public List<AlternativesFactorAnswer> getAlternativesFactorAnswer() {
		return alternativesFactorAnswer;
	}
	public void setAlternativesFactorAnswer(List<AlternativesFactorAnswer> alternativesFactorAnswer) {
		this.alternativesFactorAnswer = alternativesFactorAnswer;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
