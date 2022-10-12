package com.hua.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * the Alternatives item
 */
@Entity
public class Alternatives {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	
	public Alternatives() {}
	public Alternatives(int id) {
		this.id = id;
	}
	public Alternatives(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public Alternatives(String name) {
		this.name = name;
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
	
}
