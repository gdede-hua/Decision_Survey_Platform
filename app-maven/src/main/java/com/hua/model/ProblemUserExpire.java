package com.hua.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ProblemUserExpire {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problemId")
	private Problem problem;
	
	private Date expireDate;

	public ProblemUserExpire() {}
	public ProblemUserExpire(int id, Problem problem, Date expireDate) {
		this.id = id;
		this.problem = problem;
		this.expireDate = expireDate;
	}
	public ProblemUserExpire( Problem problem, Date expireDate) {
		this.problem = problem;
		this.expireDate = expireDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
