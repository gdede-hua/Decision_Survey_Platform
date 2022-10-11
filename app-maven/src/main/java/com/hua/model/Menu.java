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
 */
@Entity
public class Menu {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String link;
	private int orderId;
	private int parentId;
	private String role;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private List<Menu> childMenu;
	
	public Menu() {}
	public Menu(String name, String link, int orderId, int parentId, String role) {
		this.name = name;
		this.link = link;
		this.orderId = orderId;
		this.parentId = parentId;
	}
	public Menu(int id, String name, String link, int orderId, int parentId, String role) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.orderId = orderId;
		this.parentId = parentId;
		this.role = role;
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Menu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	
}
