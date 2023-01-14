package com.hua.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class for the Alternatives variables
 * @author      John Nikolaou
 */
@Entity
public class Alternatives {
	/**
	 * The id of the {@link Alternatives}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The name of the {@link Alternatives}.
	 */
	private String name;

	/**
	 * Class default constructor.
	 */
	public Alternatives() {}
	/**
	 * Class constructor.
	 * @param id
	 */
	public Alternatives(int id) {
		this.id = id;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param name
	 */
	public Alternatives(int id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * Class constructor.
	 * @param name
	 */
	public Alternatives(String name) {
		this.name = name;
	}

	/** Get the id of Alternatives.
	 * @return {@link Alternatives#id}
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
	/** Get the name of Alternatives.
	 * @return {@link Alternatives#name}
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
