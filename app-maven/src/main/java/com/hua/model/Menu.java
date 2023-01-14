package com.hua.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.gson.Gson;
/**
 * Class for the Menu variables
 * @author      John Nikolaou
 */
@Entity
public class Menu {

	/**
	 * The id of the menu.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The name of the menu option.
	 */
	private String name;
	/**
	 * The link of the menu option.
	 */
	private String link;
	/**
	 * The order number of the menu option.
	 */
	private int orderId;
	/**
	 * The tree lvl of the item.
	 * If it's zero the item is at the first lvl.
	 * Otherwise, it could take an id of another menu item and will exist under that in the menu tree
	 */
	private int parentId;
	/**
	 * The role described which type of user can see that item.
	 */
	private String role;
	/**
	 * The childMenu includes the sub-menu.
	 */
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private List<Menu> childMenu;

	/**
	 * Class default constructor.
	 */
	public Menu() {}
	/**
	 * Class constructor.
	 * @param name
	 * @param link
	 * @param orderId
	 * @param parentId
	 * @param role
	 */
	public Menu(String name, String link, int orderId, int parentId, String role) {
		this.name = name;
		this.link = link;
		this.orderId = orderId;
		this.parentId = parentId;
		this.role = role;
	}
	/**
	 * Class constructor.
	 * @param id
	 * @param name
	 * @param link
	 * @param orderId
	 * @param parentId
	 * @param role
	 */
	public Menu(int id, String name, String link, int orderId, int parentId, String role) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.orderId = orderId;
		this.parentId = parentId;
		this.role = role;
	}
	/** Get the id of the menu.
	 * @return {@link Menu#id}
	 */
	public int getId() {
	 return id;
	 }
	 /** Set the id of the menu.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the name of the menu.
	 * @return {@link Menu#name}
	 */
	public String getName() {
		return name;
	}
	/** Set the name of the menu.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** Get the link of the menu.
	 * @return {@link Menu#link}
	 */
	public String getLink() {
		return link;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public int getOrderId() {
		return orderId;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public int getParentId() {
		return parentId;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public String getRole() {
		return role;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public List<Menu> getChildMenu() {
		return childMenu;
	}
	/** Get the id of the menu.
	 * @return the id
	 */
	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
	/**
	 * @return the menu data in JSON format
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
