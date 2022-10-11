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
 * Class for the users variables
 */
@Entity
public class Users implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@NotBlank(message = "User name is mandatory")
	private String username;
	
	@NotBlank(message = "Password is mandatory")
	@JsonIgnore
	private String password;
	
	@NotBlank(message = "Real Name is mandatory")
    @Convert(converter = StringConverter.class)
	private String realName;
	
	@NotBlank(message = "Email is mandatory")
    @Convert(converter = StringConverter.class)
	private String email;
	
	@NotNull(message = "Status is mandatory")
	@Column(columnDefinition=" INT(1) NOT NULL  default 1 COMMENT '0 Disabled, 1 Enabled'")
	private int enabled;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Authorities authorities;
	
	@Transient
	@JsonIgnore
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Users() {}
	public Users(@NotBlank(message = "User name is mandatory") String id) {
		this.id = id;
	}
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
	
	public Users(@NotBlank(message = "User name is mandatory") String username,
			@NotBlank(message = "Password is mandatory") String password,
			@NotBlank(message = "Real Name is mandatory") String realName,
			@NotBlank(message = "Email is mandatory") String email,
			@NotNull(message = "Status is mandatory") int enabled,
			Authorities authorities) {
		this.username = username;
		this.password = passwordEncoder.encode(password);
		this.realName = realName;
		this.email = email;
		this.enabled = enabled;
		this.authorities = authorities;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = passwordEncoder.encode(password);
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public Authorities getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
}
