package com.hua.model;

import java.util.ArrayList;

/**
 * That clas used to collect from the front end the list o user and the id of the problem
 * we want to export the results od the AHP algorithm
 * @author      John Nikolaou
 */
public class GenerateAhpResultsExcel {
	/**
	 * The list {@link GenerateAhpResultsExcelWithSelection}.
	 */
	private ArrayList<GenerateAhpResultsExcelWithSelection> generateAhpResultsExcelWithSelection;
	/**
	 * The id of the problem.
	 */
	private int id;

	/** Get the list of user with their status we want to export.
	 * @return {@link GenerateAhpResultsExcel#generateAhpResultsExcelWithSelection}
	 */
	public ArrayList<GenerateAhpResultsExcelWithSelection> getGenerateAhpResultsExcelWithSelection() {
		return generateAhpResultsExcelWithSelection;
	}
	/** Get the id.
	 * @return {@link GenerateAhpResultsExcel#id}
	 */
	public int getId() {
		return id;
	}
	/** Set the id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
