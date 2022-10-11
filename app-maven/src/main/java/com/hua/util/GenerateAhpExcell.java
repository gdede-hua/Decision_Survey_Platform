package com.hua.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.hua.model.Alternatives;
import com.hua.model.AlternativesCriteriaAnswerAhp;
import com.hua.model.AlternativesFactorAnswerAhp;
import com.hua.model.CriteriaAnswerAhp;
import com.hua.model.FactorAnswerAhp;
import com.hua.model.GenerateAhpResultsExcel;
import com.hua.model.GenerateAhpResultsExcelWithSelection;
import com.hua.model.ProblemUserAhp;
import com.hua.repository.ProblemsUserAhpRepository;

public class GenerateAhpExcell {
	public ResponseEntity<InputStreamResource> makeExceel(int id, ProblemsUserAhpRepository problemsUserAhpRepository, 
			GenerateAhpResultsExcel generateAhpResultsExcell){
		Optional<List<ProblemUserAhp>> problemUserAhpListOpt = problemsUserAhpRepository.findAllByProblemUserProblemId(id);
		
		if ( problemUserAhpListOpt.isPresent() ) {
			List<ProblemUserAhp> problemUserAhpList = problemUserAhpListOpt.get();
			Workbook workbook = new XSSFWorkbook();
			workbook.createSheet("Persons");
			for ( ProblemUserAhp problemUserAhp : problemUserAhpList ) {
				
				GenerateAhpResultsExcelWithSelection generateAhpExcell = generateAhpResultsExcell.getGenerateAhpResultsExcelWithSelection().stream()
						  .filter(generateAhpResultsExcellWithSelectionTmp -> generateAhpResultsExcellWithSelectionTmp.getUser().getUsername().equals(problemUserAhp.getProblemUser().getUser().getUsername()))
						  .findFirst()
						  .orElse(null);
				
				if ( generateAhpExcell!=null && generateAhpExcell.getStatus()==true ) {
					Sheet sheet = workbook.createSheet(problemUserAhp.getProblemUser().getUser().getUsername());
					makeExceelPerUser(problemUserAhp, sheet);
				}
			}
			makeExceelSum(workbook, problemUserAhpList, generateAhpResultsExcell);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				workbook.write(out);
				
				String filename = problemUserAhpList.get(0).getProblemUser().getProblem().getName().replace(" ", "_")+".xlsx";
			    InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

			    return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
			        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			        .body(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}
	public void makeExceelSum(Workbook workbook, List<ProblemUserAhp> problemUserAhpList, GenerateAhpResultsExcel generateAhpResultsExcell) {
		Sheet sheet = workbook.getSheet("Persons");
		int countOfSheets=problemUserAhpList.size();
		
		Sheet sheetUser = null;
		for ( ProblemUserAhp problemUserAhp : problemUserAhpList ) {
			GenerateAhpResultsExcelWithSelection generateAhpExcell = generateAhpResultsExcell.getGenerateAhpResultsExcelWithSelection().stream()
					  .filter(generateAhpResultsExcellWithSelectionTmp -> generateAhpResultsExcellWithSelectionTmp.getUser().getUsername().equals(problemUserAhp.getProblemUser().getUser().getUsername()))
					  .findFirst()
					  .orElse(null);
			if ( generateAhpExcell!=null && generateAhpExcell.getStatus()==true ) {
				sheetUser = workbook.getSheet(problemUserAhp.getProblemUser().getUser().getUsername());
				break;
			}
		}
		
		
		int rowCounter=100, cellCounter=20;
		for(int i=0; i<rowCounter; i++) {
			for(int j=0; j<cellCounter; j++) {
				Row headerUser = sheetUser.getRow(i);
				if ( headerUser!=null ) {
					Cell headerCellUser = headerUser.getCell(j);
					if ( headerCellUser!=null ) {
						Row header = sheet.getRow(i)!=null ? sheet.getRow(i) : sheet.createRow(i);
						Cell headerCell = header.createCell(j);
						if ( headerCellUser.getCellType().toString().equals("STRING") ) {
							headerCell.setCellValue(headerCellUser.getStringCellValue());
						}
						if ( headerCellUser.getCellType().toString().equals("NUMERIC") ) {
							String formula = "(";
							for ( ProblemUserAhp problemUserAhp : problemUserAhpList ) {
								GenerateAhpResultsExcelWithSelection generateAhpExcell = generateAhpResultsExcell.getGenerateAhpResultsExcelWithSelection().stream()
										  .filter(generateAhpResultsExcellWithSelectionTmp -> generateAhpResultsExcellWithSelectionTmp.getUser().getUsername().equals(problemUserAhp.getProblemUser().getUser().getUsername()))
										  .findFirst()
										  .orElse(null);
								if ( generateAhpExcell!=null && generateAhpExcell.getStatus()==true ) {
									formula +="+"+problemUserAhp.getProblemUser().getUser().getUsername()+"!"+headerCellUser.getAddress();
								}
							}
							formula +=")/"+countOfSheets;
							headerCell.setCellFormula(formula);
//							System.out.println(headerCellUser.toString() +" - "+ headerCellUser.getCellType() +" - "+headerCellUser.getAddress() );
						}
					}
				}
			}
		}
		
	}
	public void makeExceelPerUser(ProblemUserAhp problemUserAhp, Sheet sheet) {
		makeExceelPerUserAlternativesRanking(problemUserAhp, sheet);
		makeExceelPerUserWeightsCriteria(problemUserAhp, sheet);
		makeExceelPerUserWeightsFactor(problemUserAhp, sheet);
		makeExceelPerUserWeightsCriteriaAlternatives(problemUserAhp, sheet);
		makeExceelPerUserWeightsFactorAlternatives(problemUserAhp, sheet);

	}
	public void makeExceelPerUserAlternativesRanking(ProblemUserAhp problemUserAhp, Sheet sheet) {
		
		int rowCounter=0, cellCounter=0;
		sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, cellCounter, cellCounter+1));
		Row header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		Cell headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Alternatives Ranking");
		
		header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Name");
		headerCell = header.createCell(cellCounter+1);
		headerCell.setCellValue("Ranking");
		
		for (Alternatives alternatives : problemUserAhp.getProblemUser().getProblem().getAlternatives()) {
			header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			rowCounter++;
			headerCell = header.createCell(cellCounter);
			headerCell.setCellValue(alternatives.getName());
			headerCell = header.createCell(cellCounter+1);
			float sum = 0;
			for ( CriteriaAnswerAhp criteriaAnswerAhp : problemUserAhp.getCriteriaAnswerAhp() ) {
				for ( AlternativesCriteriaAnswerAhp alternativesCriteriaAnswerAhp : problemUserAhp.getAlternativesCriteriaAnswerAhp() ) {
					if ( alternativesCriteriaAnswerAhp.getCriteria().getId() == criteriaAnswerAhp.getCriteria().getId() && 
							alternatives.getId() == alternativesCriteriaAnswerAhp.getAlternatives().getId() ) {
						sum = sum + (alternativesCriteriaAnswerAhp.getWeight() * criteriaAnswerAhp.getWeight());
					}
				}
				for ( FactorAnswerAhp factorAnswerAhp : problemUserAhp.getFactorAnswerAhp() ) {
					if ( factorAnswerAhp.getCriteria().getId()==criteriaAnswerAhp.getCriteria().getId() ) {
						for ( AlternativesFactorAnswerAhp alternativesFactorAnswerAhp : problemUserAhp.getAlternativesFactorAnswerAhp() ) {
							if ( alternativesFactorAnswerAhp.getFactor().getId() == factorAnswerAhp.getFactor().getId() && 
									alternatives.getId() == alternativesFactorAnswerAhp.getAlternatives().getId() ) {
								sum = sum + (alternativesFactorAnswerAhp.getWeight() * criteriaAnswerAhp.getWeight() * criteriaAnswerAhp.getWeight());
							}
						}
					}
				}
			}
			headerCell.setCellValue(sum);
		}
	}
	public void makeExceelPerUserWeightsCriteria(ProblemUserAhp problemUserAhp, Sheet sheet) {
		// Criteria Weights
		int rowCounter=0, cellCounter=4;
		sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, cellCounter, cellCounter + 1));
		Row header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		Cell headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Weights of Criteria");

		header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Criterion");
		headerCell = header.createCell(cellCounter + 1);
		headerCell.setCellValue("Weight");

		for (CriteriaAnswerAhp criteriaAnswerAhp : problemUserAhp.getCriteriaAnswerAhp()) {
			header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			rowCounter++;
			headerCell = header.createCell(cellCounter);
			headerCell.setCellValue(criteriaAnswerAhp.getCriteria().getName());
			headerCell = header.createCell(cellCounter + 1);
			headerCell.setCellValue(criteriaAnswerAhp.getWeight());
		}
	}
	public void makeExceelPerUserWeightsFactor(ProblemUserAhp problemUserAhp, Sheet sheet) {
		// Criteria Weights
		int rowCounter=0, cellCounter=8;
		sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, cellCounter, cellCounter + 1));
		Row header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		Cell headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Factor of Criteria");

		header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Criterion");
		headerCell = header.createCell(cellCounter + 1);
		headerCell.setCellValue("Factor");
		headerCell = header.createCell(cellCounter + 2);
		headerCell.setCellValue("Weight");

		for (FactorAnswerAhp factorAnswerAhp : problemUserAhp.getFactorAnswerAhp()) {
			header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			rowCounter++;
			headerCell = header.createCell(cellCounter);
			headerCell.setCellValue(factorAnswerAhp.getCriteria().getName());
			headerCell = header.createCell(cellCounter +1 );
			headerCell.setCellValue(factorAnswerAhp.getFactor().getName());
			headerCell = header.createCell(cellCounter + 2);
			headerCell.setCellValue(factorAnswerAhp.getWeight());
		}
	}
	public void makeExceelPerUserWeightsCriteriaAlternatives(ProblemUserAhp problemUserAhp, Sheet sheet) {
		// Criteria Weights
		int rowCounter=0, cellCounter=12;
		sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, cellCounter, cellCounter + 1));
		Row header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		Cell headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Average Weights of Alternatives per Criteria");

		header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Criterion");
		headerCell = header.createCell(cellCounter + 1);
		headerCell.setCellValue("Alternative");
		headerCell = header.createCell(cellCounter + 2);
		headerCell.setCellValue("Weight");

		for (AlternativesCriteriaAnswerAhp alternativesCriteriaAnswerAhp : problemUserAhp.getAlternativesCriteriaAnswerAhp()) {
			header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			rowCounter++;
			headerCell = header.createCell(cellCounter);
			headerCell.setCellValue(alternativesCriteriaAnswerAhp.getCriteria().getName());
			headerCell = header.createCell(cellCounter +1 );
			headerCell.setCellValue(alternativesCriteriaAnswerAhp.getAlternatives().getName());
			headerCell = header.createCell(cellCounter + 2);
			headerCell.setCellValue(alternativesCriteriaAnswerAhp.getWeight());
		}
	}
	public void makeExceelPerUserWeightsFactorAlternatives(ProblemUserAhp problemUserAhp, Sheet sheet) {
		// Criteria Weights
		int rowCounter=0, cellCounter=16;
		sheet.addMergedRegion(new CellRangeAddress(rowCounter, rowCounter, cellCounter, cellCounter + 1));
		Row header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		Cell headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Average Weights of Alternatives per Factor");

		header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
		rowCounter++;
		headerCell = header.createCell(cellCounter);
		headerCell.setCellValue("Factor");
		headerCell = header.createCell(cellCounter + 1);
		headerCell.setCellValue("Alternative");
		headerCell = header.createCell(cellCounter + 2);
		headerCell.setCellValue("Weight");

		for (AlternativesFactorAnswerAhp alternativesFactorAnswerAhp : problemUserAhp.getAlternativesFactorAnswerAhp()) {
			header = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			rowCounter++;
			headerCell = header.createCell(cellCounter);
			headerCell.setCellValue(alternativesFactorAnswerAhp.getFactor().getName());
			headerCell = header.createCell(cellCounter +1 );
			headerCell.setCellValue(alternativesFactorAnswerAhp.getAlternatives().getName());
			headerCell = header.createCell(cellCounter + 2);
			headerCell.setCellValue(alternativesFactorAnswerAhp.getWeight());
		}
	}
}
