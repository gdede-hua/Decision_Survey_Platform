package com.hua.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hua.model.Alternatives;
import com.hua.model.AlternativesCriteriaAnswer;
import com.hua.model.AlternativesCriteriaCrAhp;
import com.hua.model.AlternativesFactorAnswer;
import com.hua.model.AlternativesFactorCrAhp;
import com.hua.model.Criteria;
import com.hua.model.CriteriaAnswer;
import com.hua.model.Factor;
import com.hua.model.FactorAnswer;
import com.hua.model.FactorCrAhp;
import com.hua.model.ProblemUser;
import com.hua.model.ProblemUserAhp;
import com.hua.repository.ProblemsUserAhpRepository;
/**
 * generate the Excel with the survey answers
 */
@Service
public class GenerateDataExcel {

	private final ProblemsUserAhpRepository problemsUserAHPRepository;

	public GenerateDataExcel(ProblemsUserAhpRepository problemsUserAHPRepository) {
		this.problemsUserAHPRepository = problemsUserAHPRepository;
	}

	/**
	 * Make the Excel file and start to insert the answers
	 */
	public ResponseEntity<InputStreamResource> makeExcel(int id) {
		Optional<List<ProblemUserAhp>> ProblemUserAhpListOpt = problemsUserAHPRepository.findAllByProblemUserProblemId(id);
		if ( ProblemUserAhpListOpt.isPresent() ) {
			List<ProblemUserAhp> problemUserAhpList = ProblemUserAhpListOpt.get();
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook();
			for ( ProblemUserAhp problemUserAhp : problemUserAhpList ) {
				Sheet sheet = workbook.createSheet(problemUserAhp.getProblemUser().getUser().getUsername());
				makeExcelPerUser(problemUserAhp, sheet);
			}
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				workbook.write(out);
				
				String filename = problemUserAhpList.get(0).getProblemUser().getProblem().getName().replace(" ", "_")+"_data.xlsx";
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

	/**
	 * Insert the answers of users for each category of data
	 */
	private void makeExcelPerUser(ProblemUserAhp problemUserAhp, Sheet sheet) {
		int rowCounter=0;
		
		ProblemUser problemUser = problemUserAhp.getProblemUser();
		List<Criteria> criteriaList = problemUser.getProblem().getCriteria();
		Row headerBottom = sheet.createRow(rowCounter);
		rowCounter = addCriteriaData(problemUserAhp, sheet, rowCounter, problemUser, criteriaList);

		rowCounter = addFactorForCriteriaData(problemUserAhp, sheet, rowCounter, problemUser, criteriaList);

		rowCounter = addAlternativeForCriteriaData(problemUserAhp, sheet, rowCounter, problemUser, criteriaList);

		addAlternativeForFactorData(problemUserAhp, sheet, rowCounter, problemUser);

	}
	/**
	 * Insert the answers of users for alternative of factor
	 */
	private static void addAlternativeForFactorData(ProblemUserAhp problemUserAhp, Sheet sheet, int rowCounter, ProblemUser problemUser) {
		Row headerBottom;
		ArrayList<Factor> factorList = new ArrayList<>();
		for (Criteria criteria : problemUser.getProblem().getCriteria()) {
			factorList.addAll(criteria.getFactor());
        }
		for (Factor factor : factorList) {
			Row headerHeader = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			headerHeader.createCell(0).setCellValue(factor.getName());
			rowCounter++;
			List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
			if( alternativesList.size()>0) {
				for ( int i=0; i<alternativesList.size(); i++) {
					Row headerTop = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
					headerTop.createCell(i+1).setCellValue(alternativesList.get(i).getName());
					Row headerLeft = sheet.getRow(rowCounter +i+1)!=null ? sheet.getRow(rowCounter +i+1) : sheet.createRow(rowCounter +i+1);
					headerLeft.createCell(0).setCellValue(alternativesList.get(i).getName());
					for ( int j=0; j<alternativesList.size(); j++) {
						if (i != j) {
							final int iFinal = i;
							final int jFinal = j;
							AlternativesFactorAnswer alternativesFactorAnswer = problemUser.getAlternativesFactorAnswer().stream()
									  .filter(alternativesFactorTmp -> alternativesFactorTmp.getAlternativesTop().getId()==alternativesList.get(iFinal).getId() &&
											  			alternativesFactorTmp.getAlternativesLeft().getId()==alternativesList.get(jFinal).getId() &&
											  			alternativesFactorTmp.getFactor().getId()==factor.getId())
									  .findAny()
									  .orElse(null);
							if (alternativesFactorAnswer!=null)
								headerLeft.createCell(j+1).setCellValue(alternativesFactorAnswer.getWeight());
							else
								headerLeft.createCell(j+1).setCellValue((float) 1);

						} else
							headerLeft.createCell(j+1).setCellValue((float) 1);
					}
				}
				rowCounter = rowCounter +alternativesList.size()+1;
				headerBottom = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
				headerBottom.createCell(0).setCellValue("CR");
				AlternativesFactorCrAhp alternativesFactorCrAhp = problemUserAhp.getAlternativesFactorCrAhp().stream()
						.filter(alternativesFactorCrAhpTmp -> alternativesFactorCrAhpTmp.getFactor().getId()==factor.getId())
						.findAny().orElse(null);
				headerBottom.createCell(1).setCellValue(alternativesFactorCrAhp != null ? alternativesFactorCrAhp.getCr() : 0);
				rowCounter = rowCounter +3;
			}
		}
	}

	/**
	 * Insert the answers of users for alternative of criteria
	 */
	private static int addAlternativeForCriteriaData(ProblemUserAhp problemUserAhp, Sheet sheet, int rowCounter, ProblemUser problemUser, List<Criteria> criteriaList) {
		Row headerBottom;
		for ( Criteria criteria : criteriaList) {
			if (criteria.getFactor().size()==0) {
				Row headerHeader = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
				headerHeader.createCell(0).setCellValue(criteria.getName());
				rowCounter++;
				List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
				if (alternativesList.size()>0) {
					for ( int i=0; i<alternativesList.size(); i++) {
						Row headerTop = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
						headerTop.createCell(i+1).setCellValue(alternativesList.get(i).getName());
						Row headerLeft = sheet.getRow(rowCounter +i+1)!=null ? sheet.getRow(rowCounter +i+1) : sheet.createRow(rowCounter +i+1);
						headerLeft.createCell(0).setCellValue(alternativesList.get(i).getName());
						for ( int j=0; j<alternativesList.size(); j++) {
							if (i != j) {
								final int iFinal = i;
								final int jFinal = j;
								AlternativesCriteriaAnswer alternativesCriteriaAnswer = problemUser.getAlternativesCriteriaAnswer().stream()
										  .filter(alternativesCriteriaTmp -> alternativesCriteriaTmp.getAlternativesTop().getId()==alternativesList.get(iFinal).getId() &&
												  			alternativesCriteriaTmp.getAlternativesLeft().getId()==alternativesList.get(jFinal).getId() &&
												  			alternativesCriteriaTmp.getCriteria().getId()==criteria.getId())
										  .findAny()
										  .orElse(null);
								if (alternativesCriteriaAnswer!=null)
									headerLeft.createCell(j+1).setCellValue(alternativesCriteriaAnswer.getWeight());
								else
									headerLeft.createCell(j+1).setCellValue((float) 1);

							} else
								headerLeft.createCell(j+1).setCellValue((float) 1);
						}
					}
					rowCounter = rowCounter +alternativesList.size()+1;
					headerBottom = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
					headerBottom.createCell(0).setCellValue("CR");
					AlternativesCriteriaCrAhp alternativesCriteriaCrAhp = problemUserAhp.getAlternativesCriteriaCrAhp().stream()
							.filter(alternativesCriteriaCrAhpTmp -> alternativesCriteriaCrAhpTmp.getCriteria().getId()==criteria.getId())
							.findAny().orElse(null);
					headerBottom.createCell(1).setCellValue(alternativesCriteriaCrAhp != null ? alternativesCriteriaCrAhp.getCr() : 0);
					rowCounter = rowCounter +3;
				}
			}
		}
		return rowCounter;
	}

	/**
	 * Insert the answers of users for Factor of criteria
	 */
	private static int addFactorForCriteriaData(ProblemUserAhp problemUserAhp, Sheet sheet, int rowCounter, ProblemUser problemUser, List<Criteria> criteriaList) {
		Row headerBottom;
		for ( Criteria criteria : criteriaList) {
			if (criteria.getFactor().size()>1) {
				Row headerHeader = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
				headerHeader.createCell(0).setCellValue(criteria.getName());
				rowCounter++;
				List<Factor> factorList = criteria.getFactor();
				for ( int i=0; i<factorList.size(); i++) {
					Row headerTop = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
					headerTop.createCell(i+1).setCellValue(factorList.get(i).getName());
					Row headerLeft = sheet.getRow(rowCounter +i+1)!=null ? sheet.getRow(rowCounter +i+1) : sheet.createRow(rowCounter +i+1);
					headerLeft.createCell(0).setCellValue(factorList.get(i).getName());
					for ( int j=0; j<factorList.size(); j++) {
						if (i != j) {
							final int iFinal = i;
							final int jFinal = j;
							FactorAnswer factorAnswer = problemUser.getFactorAnswer().stream()
									  .filter(factorAnswerTmp -> factorAnswerTmp.getFactorTop().getId()==factorList.get(iFinal).getId() &&
											  					factorAnswerTmp.getFactorLeft().getId()==factorList.get(jFinal).getId() &&
											  					factorAnswerTmp.getCriteria().getId()==criteria.getId())
									  .findAny()
									  .orElse(null);
							if (factorAnswer!=null)
								headerLeft.createCell(j+1).setCellValue(factorAnswer.getWeight());
							else
								headerLeft.createCell(j+1).setCellValue((float) 1);
						} else
							headerLeft.createCell(j+1).setCellValue((float) 1);
					}
				}
				rowCounter = rowCounter +factorList.size()+1;
				headerBottom = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
				headerBottom.createCell(0).setCellValue("CR");
				FactorCrAhp factorCrAhp = problemUserAhp.getFactorCrAhp().stream()
						.filter(factorCrAhpTmp -> factorCrAhpTmp.getCriteria().getId()==criteria.getId())
						.findAny().orElse(null);
				headerBottom.createCell(1).setCellValue(factorCrAhp != null ? factorCrAhp.getCr() : 0);
				rowCounter = rowCounter +3;
			}
		}
		return rowCounter;
	}

	/**
	 * Insert the answers of users for criteria
	 */
	private static int addCriteriaData(ProblemUserAhp problemUserAhp, Sheet sheet, int rowCounter, ProblemUser problemUser, List<Criteria> criteriaList) {
		Row headerBottom;
		if ( criteriaList.size()>1 ) {
			for (int i = 0; i< criteriaList.size(); i++) {
				Row headerTop = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
				headerTop.createCell(i+1).setCellValue(criteriaList.get(i).getName());
				Row headerLeft = sheet.getRow(rowCounter +i+1)!=null ? sheet.getRow(rowCounter +i+1) : sheet.createRow(rowCounter +i+1);
				headerLeft.createCell(0).setCellValue(criteriaList.get(i).getName());
				for (int j = 0; j< criteriaList.size(); j++) {
					if (i != j) {
						final int iFinal = i;
						final int jFinal = j;
						CriteriaAnswer criteriaAnswer = problemUser.getCriteriaAnswer().stream()
								.filter(criteriaAnswerTmp -> criteriaAnswerTmp.getCriteriaTop().getId()== criteriaList.get(iFinal).getId() &&
									criteriaAnswerTmp.getCriteriaLeft().getId()== criteriaList.get(jFinal).getId() )
								  .findAny()
								  .orElse(null);
						if (criteriaAnswer!=null)
							headerLeft.createCell(j+1).setCellValue(criteriaAnswer.getWeight());
						else
							headerLeft.createCell(j+1).setCellValue((float) 1);
					} else
						headerLeft.createCell(j+1).setCellValue((float) 1);
				}
			}
			rowCounter = rowCounter + criteriaList.size()+1;
			headerBottom = sheet.getRow(rowCounter)!=null ? sheet.getRow(rowCounter) : sheet.createRow(rowCounter);
			headerBottom.createCell(0).setCellValue("CR");
			headerBottom.createCell(1).setCellValue(problemUserAhp.getCriteriaCrAhp().getCr());
			rowCounter = rowCounter +3;
		}
		return rowCounter;
	}

}





















