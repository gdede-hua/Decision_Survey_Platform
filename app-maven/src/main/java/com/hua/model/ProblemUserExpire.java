package com.hua.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * Class for the assign survey expiration
 * @author      John Nikolaou
 */
@Entity
public class ProblemUserExpire {

	/**
	 * The id of the expiration of the user problem.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The problem would expire.
	 */
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problemId")
	private Problem problem;
	/**
	 * The expiration date of the problem.
	 */
	private Date expireDate;

	/**
	 * Class default constructor.
	 */
	public ProblemUserExpire() {}
	/**
	 * Class constructor.
	 * @param id
	 * @param problem {@link Problem}
	 * @param expireDate {@link Date}
	 */
	public ProblemUserExpire(int id, Problem problem, Date expireDate) {
		this.id = id;
		this.problem = problem;
		this.expireDate = expireDate;
	}
	/**
	 * Class constructor.
	 * @param problem {@link Problem}
	 * @param expireDate {@link Date}
	 */
	public ProblemUserExpire( Problem problem, Date expireDate) {
		this.problem = problem;
		this.expireDate = expireDate;
	}

	/** Get the id of the expiration of the user problem.
	 * @return {@link ProblemUserExpire#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id of the expiration of the user problem.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the problem of the expiration of the user problem.
	 * @return {@link ProblemUserExpire#problem}
	 */
	public Problem getProblem() {
		return problem;
	}
	/** Set the problem of the expiration of the user problem.
	 * @param problem {@link Problem}
	 */
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	/** Get the expiration date of the user problem.
	 * @return {@link ProblemUserExpire#expireDate}
	 */
	public Date getExpireDate() {
		return expireDate;
	}
	/** Set the expiration date of the user problem.
	 * @param expireDate
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
