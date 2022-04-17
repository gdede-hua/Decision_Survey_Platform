package com.hua.model;

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

@Entity
public class Problem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String description;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	
	@Column(columnDefinition=" INT(1) default 1 COMMENT '0 Disabled, 1 Enabled, 2 Running, 3 Completed'")
	private int status;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="problemId")
	private List<Criteria> criteria;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "problemId")
	private List<Alternatives> alternatives;

	public Problem() {}
	public Problem(int id) {
		super();
		this.id = id;
	}
	public Problem(int id, String name, String description, Users user, List<Criteria> criteria, List<Alternatives> alternatives) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.user = user;
		this.criteria = criteria;
		this.alternatives = alternatives;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public List<Criteria> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<Criteria> criteria) {
		this.criteria = criteria;
	}
	public List<Alternatives> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(List<Alternatives> alternatives) {
		this.alternatives = alternatives;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
