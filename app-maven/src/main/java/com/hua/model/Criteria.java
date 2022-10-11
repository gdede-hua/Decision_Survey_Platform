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
/**
 * Class for the Criteria variables
 */
@Entity
public class Criteria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "criteriaId")
	private List<Factor> factor;

	public Criteria() {}
	public Criteria(int id) {
		this.id = id;
	}
	public Criteria(int id, String name, List<Factor> factor) {
		this.id = id;
		this.name = name;
		this.factor = factor;
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
	public List<Factor> getFactor() {
		return factor;
	}
	public void setFactor(List<Factor> factor) {
		this.factor = factor;
	}
	
}
