package com.hua.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * Class for the Authorities
 */
@Entity
public class Authorities implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String username;
	private String authority;
	
	public Authorities() {}
	public Authorities(String id, String username, String authority) {
		this.id = id;
		this.username = username;
		this.authority = authority;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
