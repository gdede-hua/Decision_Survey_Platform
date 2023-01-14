package com.hua.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * Class for the Factor variables
 * @author      John Nikolaou
 */
@Entity
public class Factor {

	/**
	 * The id of the {@link Factor}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The name of the {@link Factor}.
	 */
	private String name;

	/**
	 * Class default constructor.
	 */
	public Factor() {}
	/**
	 * Class constructor.
	 * @param id
	 */
	public Factor(int id) {
		this.id = id;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param name
	 */
	public Factor(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/** Get the id of Factor.
	 * @return {@link Factor#id}
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
	/** Get the name of Factor.
	 * @return {@link Factor#name}
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

}
