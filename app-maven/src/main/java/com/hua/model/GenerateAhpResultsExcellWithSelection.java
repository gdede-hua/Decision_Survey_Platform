package com.hua.model;

public class GenerateAhpResultsExcellWithSelection {
	private Users user;
	private boolean status;
	
	public GenerateAhpResultsExcellWithSelection() {}
	public GenerateAhpResultsExcellWithSelection(Users user, boolean status) {
		this.user = user;
		this.status = status;
	}
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
