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
 * @author      John Nikolaou
 */
@Entity
public class Criteria {

	/**
	 * The id of the {@link Criteria}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The name of the {@link Criteria}.
	 */
	private String name;
	/**
	 * The list of {@link Factor} for each  {@link Criteria}.
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "criteriaId")
	private List<Factor> factor;

	/**
	 * Class default constructor.
	 */
	public Criteria() {}
	/**
	 * Class constructor.
	 * @param id
	 */
	public Criteria(int id) {
		this.id = id;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param name
	 * @param factor {@link Factor}
	 */
	public Criteria(int id, String name, List<Factor> factor) {
		this.id = id;
		this.name = name;
		this.factor = factor;
	}

	/** Get the id of criteria.
	 * @return {@link Criteria#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the name of criteria.
	 * @return {@link Criteria#name}
	 */
	public String getName() {
		return name;
	}
	/** Set the name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** Get the factors of the criteria.
	 * @return {@link Criteria#factor}
	 */
	public List<Factor> getFactor() {
		return factor;
	}
	/** Set the factors of the criteria.
	 * @param factor {@link Factor}
	 */
	public void setFactor(List<Factor> factor) {
		this.factor = factor;
	}
	
}
