package com.hua.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.hua.util.StringConverter;
/**
 * That class has the user information
 * @author      John Nikolaou
 */
@Entity
public class Users implements Serializable{
	/**
	 * The default value for this field is {@value}.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id of the user.
	 */
	@Id
	private String id;
	/**
	 * The username of the user.
	 */
	@NotBlank(message = "User name is mandatory")
	private String username;
	/**
	 * The password of the user.
	 */
	@NotBlank(message = "Password is mandatory")
	@JsonIgnore
	private String password;
	/**
	 * The real name of the user.
	 */
	@NotBlank(message = "Real Name is mandatory")
    @Convert(converter = StringConverter.class)
	private String realName;
	/**
	 * The email of the user.
	 */
	@NotBlank(message = "Email is mandatory")
    @Convert(converter = StringConverter.class)
	private String email;
	/**
	 * The status of the user(0:disabled, 1:enabled).
	 */
	@NotNull(message = "Status is mandatory")
	@Column(columnDefinition=" INT(1) NOT NULL  default 1 COMMENT '0 Disabled, 1 Enabled'")
	private int enabled;
	/**
	 * The {@link Authorities} of the user.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Authorities authorities;
	
	@Transient
	@JsonIgnore
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * Class default constructor.
	 */
	public Users() {}
	/**
	 * Class constructor.
	 * @param id
	 */
	public Users(@NotBlank(message = "User name is mandatory") String id) {
		this(id, null, null, null, null, 0, null);
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param username
	 * @param password
	 * @param realName
	 * @param email
	 * @param enabled
	 * @param authorities
	 */
	public Users(String id,
			@NotBlank(message = "User name is mandatory") String username,
			@NotBlank(message = "Password is mandatory") String password,
			@NotBlank(message = "Real Name is mandatory") String realName,
			@NotBlank(message = "Email is mandatory") String email,
			@NotNull(message = "Status is mandatory") int enabled,
			Authorities authorities) {
		this.id = id;
		this.username = username;
		this.password = passwordEncoder.encode(password);
		this.realName = realName;
		this.email = email;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	/** Get the id of the user.
	 * @return {@link Users#id}
	 */
	public String getId() {
		return id;
	}
	/** Set the id of the user.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/** Get the username of the user.
	 * @return {@link Users#username}
	 */
	public String getUsername() {
		return username;
	}
	/** Set the username of the user.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/** Get the password of the user.
	 * @return {@link Users#password}
	 */
	public String getPassword() {
		return password;
	}
	/** Set the password of the user.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = passwordEncoder.encode(password);
	}
	/** Get the real name of the user.
	 * @return {@link Users#realName}
	 */
	public String getRealName() {
		return realName;
	}
	/** Set the real name of the user.
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/** Get the email of the user.
	 * @return {@link Users#email}
	 */
	public String getEmail() {
		return email;
	}
	/** Set the email of the user.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/** Get the status of the user.
	 * If the field has value 0 the user is disabled
	 * If the field has value 1 the user is enabled
	 * @return {@link Users#enabled}
	 */
	public int getEnabled() {
		return enabled;
	}
	/** Set the  status of the user.
	 * If the field has value 0 the user is disabled
	 * If the field has value 1 the user is enabled
	 * @param enabled
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	/** Get the authorities of the user.
	 * @return {@link Authorities}
	 */
	public Authorities getAuthorities() {
		return authorities;
	}
	/** Set the authorities of the user.
	 * @param authorities {@link Authorities}
	 */
	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}
	/**
	 * @return the user data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
}
