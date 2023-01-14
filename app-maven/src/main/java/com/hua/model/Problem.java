package com.hua.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * Class for the Research information
 * @author      John Nikolaou
 */
@Entity
public class Problem implements Serializable {

	/**
	 * The id of the Problem.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The name of the Problem.
	 */
	private String name;
	/**
	 * The description of the Problem.
	 */
	private String description;
	/**
	 * The {@link Users} who create the Problem.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	/**
	 * The status of the Problem.
	 */
	@Column(columnDefinition=" INT(1) default 1 COMMENT '0 Disabled, 1 Enabled, 2 Running, 3 Completed'")
	private int status;
	/**
	 * The {@link Criteria} of the Problem.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemId")
	private List<Criteria> criteria;
	/**
	 * The {@link Alternatives} of the Problem.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemId")
	private List<Alternatives> alternatives;

	/**
	 * Class default constructor.
	 */
	public Problem() {}
	/**
	 * Class constructor.
	 * @param id
	 */
	public Problem(int id) {
		this.id = id;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param name
	 * @param description
	 * @param user {@link Users}
	 * @param criteria {@link Criteria}
	 * @param alternatives {@link Alternatives}
	 */
	public Problem(int id, String name, String description, Users user, List<Criteria> criteria, List<Alternatives> alternatives) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.user = user;
		this.criteria = criteria;
		this.alternatives = alternatives;
	}
	/**
	 * Class constructor.
	 * @param name
	 * @param description
	 * @param user {@link Users}
	 * @param criteria {@link Criteria}
	 * @param alternatives {@link Alternatives}
	 */
	public Problem(String name, String description, Users user, List<Criteria> criteria, List<Alternatives> alternatives) {
		this.name = name;
		this.description = description;
		this.user = user;
		this.criteria = criteria;
		this.alternatives = alternatives;
	}

	/** Get the id of the problem.
	 * @return {@link Problem#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id of the problem.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the name of the problem.
	 * @return {@link Problem#name}
	 */
	public String getName() {
		return name;
	}
	/** Set the authorities of the problem.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** Get the description of the problem.
	 * @return {@link Problem#description}
	 */
	public String getDescription() {
		return description;
	}
	/** Set the authorities of the problem.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/** Get the user of the problem.
	 * @return {@link Problem#user}
	 */
	public Users getUser() {
		return user;
	}
	/** Set the authorities of the problem.
	 * @param user {@link Users}
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	/** Get the status of the problem.
	 * @return {@link Problem#status}
	 */
	public int getStatus() {
		return status;
	}
	/** Set the authorities of the problem.
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/** Get the criteria list of the problem.
	 * @return {@link Problem#criteria}
	 */
	public List<Criteria> getCriteria() {
		return criteria;
	}
	/** Set the authorities of the problem.
	 * @param criteria {@link Criteria}
	 */
	public void setCriteria(List<Criteria> criteria) {
		this.criteria = criteria;
	}
	/** Get the alternatives list of the problem.
	 * @return {@link Problem#alternatives}
	 */
	public List<Alternatives> getAlternatives() {
		return alternatives;
	}
	/** Set the authorities of the problem.
	 * @param alternatives {@link Alternatives}
	 */
	public void setAlternatives(List<Alternatives> alternatives) {
		this.alternatives = alternatives;
	}
	/**
	 * @return the problem data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
