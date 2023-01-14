package com.hua.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * That class has the authorities of the user
 * @author      John Nikolaou
 */
@Entity
public class Authorities implements Serializable {
	/**
	 * The default value for this field is {@value}.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id of the authorities.
	 */
	@Id
	private String id;
	/**
	 * The username of the user which has that authority.
	 */
	private String username;
	/**
	 * The authority of the user.
	 */
	private String authority;

	/**
	 * Class default constructor.
	 */
	public Authorities() {}
	/**
	 * Class constructor.
	 * @param id
	 * @param username
	 * @param authority
	 */
	public Authorities(String id, String username, String authority) {
		this.id = id;
		this.username = username;
		this.authority = authority;
	}

	/** Get the id of the authorities.
	 * @return {@link Authorities#id}
	 */
	public String getId() {
		return id;
	}
	/** Set the id of the authorities.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/** Get the username of the authorities.
	 * @return {@link Authorities#username}
	 */
	public String getUsername() {
		return username;
	}
	/** Set the username of the authorities.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/** Get the authority of the authorities.
	 * @return {@link Authorities#authority}
	 */
	public String getAuthority() {
		return authority;
	}
	/** Get the id of the authority.
	 * @param authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
