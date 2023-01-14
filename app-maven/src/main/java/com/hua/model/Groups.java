package com.hua.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
/**
 * Class for the categorized user to the user groups
 * @author      John Nikolaou
 */
@Entity
public class Groups {

	/**
	 * The id of the user group.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The name of the user group.
	 */
	private String name;
	/**
	 * The description of the user group.
	 */
	private String description;
	/**
	 * The owner {@link Users} of the user group.
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	/**
	 * The list {@link Users} of the user group.
	 */
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name="groupId")
	private List<Users> users;

	/**
	 * Class default constructor.
	 */
	public Groups() {}
	/**
	 * Class constructor.
	 * @param id
	 * @param name
	 * @param description
	 * @param user {@link Users}
	 * @param users list of {@link Users}
	 */
	public Groups(int id, String name, String description, Users user, List<Users> users) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.user = user;
		this.users = users;
	}
	/**
	 * Class constructor.
	 * @param name
	 * @param description
	 * @param user {@link Users}
	 * @param users list of {@link Users}
	 */
	public Groups(String name, String description, Users user, List<Users> users) {
		this.name = name;
		this.description = description;
		this.user = user;
		this.users = users;
	}

	/** Get the id of the user group.
	 * @return {@link Groups#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id of the user group.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the name of the user group.
	 * @return {@link Groups#name}
	 */
	public String getName() {
		return name;
	}
	/** Set the name of the user group.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** Get the description of the user group.
	 * @return {@link Groups#description}
	 */
	public String getDescription() {
		return description;
	}
	/** Set the description of the user group.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/** Get the owner of the user group.
	 * @return {@link Groups#user}
	 */
	public Users getUser() {
		return user;
	}
	/** Set a user as owner of the user group.
	 * @param user {@link Users}
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	/** Get the assign users to the user group.
	 * @return {@link Groups#user}
	 */
	public List<Users> getUsers() {
		return users;
	}
	/** Set list of users of the user group.
	 * @param users {@link Users}
	 */
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
}
