package com.hua.model;

/**
 * Class for the selection of the user which we want to include when we export the data
 * of the AHP algorithm
 * @author      John Nikolaou
 */
public class GenerateAhpResultsExcelWithSelection {
	/**
	 * The {@link Users} we want to export.
	 */
	private Users user;
	/**
	 * The status of the user we want to export.
	 * If it is true we export his data.
	 */
	private boolean status;

	/**
	 * Class default constructor.
	 */
	public GenerateAhpResultsExcelWithSelection() {}
	/**
	 * Class constructor.
	 * @param user {@link Users}
	 * @param status
	 */
	public GenerateAhpResultsExcelWithSelection(Users user, boolean status) {
		this.user = user;
		this.status = status;
	}
	/** Get information of the user we want to export.
	 * @return {@link GenerateAhpResultsExcelWithSelection#user}
	 */
	public Users getUser() {
		return user;
	}
	/** Set the user.
	 * @param user {@link Users}
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	/** Get the status of the user we want to export
	 * @return {@link GenerateAhpResultsExcelWithSelection#status}
	 */
	public boolean getStatus() {
		return status;
	}
	/** Set the status.
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}
