package com.hua.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hua.model.AHP;
import com.hua.model.Alternatives;
import com.hua.model.AlternativesCriteriaAnswer;
import com.hua.model.AlternativesCriteriaAnswerAhp;
import com.hua.model.AlternativesCriteriaCrAhp;
import com.hua.model.AlternativesFactorAnswer;
import com.hua.model.AlternativesFactorAnswerAhp;
import com.hua.model.AlternativesFactorCrAhp;
import com.hua.model.Criteria;
import com.hua.model.CriteriaAnswer;
import com.hua.model.CriteriaAnswerAhp;
import com.hua.model.CriteriaCrAhp;
import com.hua.model.Factor;
import com.hua.model.FactorAnswer;
import com.hua.model.FactorAnswerAhp;
import com.hua.model.FactorCrAhp;
import com.hua.model.ProblemUser;
import com.hua.model.ProblemUserAhp;
import com.hua.repository.ProblemsUserAhpRepository;
import com.hua.repository.ProblemsUserRepository;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
/**
 * Run the AHP algorithm
 *
 * <p>In that file the system get a survey, for each comparison data category make a matrix and run the AHP
 * algorithm to generate the `CR` and the `weight` of each value.</p>
 * <p>At the end of each data category we save the data into database for future used.</p>
 * @author      John Nikolaou
 */
@Service
public class RunAHP {

	private final ProblemsUserRepository problemsUserRepository;
	private final ProblemsUserAhpRepository problemsUserAHPRepository;

	public RunAHP(ProblemsUserRepository problemsUserRepository, ProblemsUserAhpRepository problemsUserAHPRepository) {
		this.problemsUserRepository = problemsUserRepository;
		this.problemsUserAHPRepository = problemsUserAHPRepository;
	}
	/**
	 * The system run the AHP algorithm for each user have complete the survey
	 *
	 * @param id the id of the survey
	 */
	public void runAHP(int id) {
		Optional<List<ProblemUser>> problemUserListOpt = problemsUserRepository.findAllByProblemIdAndStatus(id, 2);
		if (problemUserListOpt.isPresent()) {
			List<ProblemUser> problemUserList = problemUserListOpt.get();
			for (ProblemUser problemUser : problemUserList) {

				ProblemUserAhp problemUserAhp = new ProblemUserAhp();
				problemUserAhp.setProblemUser(new ProblemUser(problemUser.getId(), problemUser.getUser()));

				criteriaAhp(problemUser, problemUserAhp);

				factorsForCriteriaAhp(problemUser, problemUserAhp);

				alternativesForCriteriaAhp(problemUser, problemUserAhp);

				alternativesForFactorsAhp(problemUser, problemUserAhp);
			}
		}

	}

	/**
	 * Run tha AHP algorithm and save the results for the alternatives of factors.
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param problemUserAhp used to save the results of the algorithm to the database.
	 */
	private void alternativesForFactorsAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp) {
		ArrayList<Factor> factorList = new ArrayList<>();
		List<Criteria> criteriaList = problemUser.getProblem().getCriteria();
		for (Criteria criteria : criteriaList) {
			factorList.addAll(criteria.getFactor());
		}
		List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAHPList = new ArrayList<>();
		List<AlternativesFactorCrAhp> alternativesFactorCrAHPList = new ArrayList<>();
		for (Factor factor : factorList) {
			System.out.println("--------------------------------- Run Alternatives for Factors: "
					+ factor.getName() + "  -----------------------------------");
			List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
			if (alternativesList.size() > 0) {
				double[][] matrix = getMatrixAlternativesForFactors(problemUser, factor.getId(), alternativesList);
				AHP ahpDataAlternatives = runAHPeig(matrix);
				for (int i = 0; i < alternativesList.size(); i++) {
					alternativesFactorAnswerAHPList.add(new AlternativesFactorAnswerAhp(
							new Factor(factor.getId()), new Alternatives(alternativesList.get(i).getId()),
							(float) ahpDataAlternatives.getWeights()[i]));
				}
				alternativesFactorCrAHPList.add(new AlternativesFactorCrAhp(new Factor(factor.getId()),
						(float) ahpDataAlternatives.getCr()));
			}
		}
		problemUserAhp.setAlternativesFactorCrAhp(alternativesFactorCrAHPList);
		problemUserAhp.setAlternativesFactorAnswerAhp(alternativesFactorAnswerAHPList);
		problemsUserAHPRepository.save(problemUserAhp);
	}

	/**
	 * Run tha AHP algorithm and save the results for the alternatives of criteria
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param problemUserAhp used to save the results of the algorithm to the database.
	 */
	private void alternativesForCriteriaAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp) {
		List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAHPList = new ArrayList<>();
		List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAHPList = new ArrayList<>();
		List<Criteria> criteriaList = problemUser.getProblem().getCriteria();
		for (Criteria criteria : criteriaList) {
			if (criteria.getFactor().size() == 0) {
				System.out.println("--------------------------------- Run Alternatives for Criteria: "
						+ criteria.getName() + "  -----------------------------------");
				List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
				if (alternativesList.size() > 0) {
					double[][] matrix = getMatrixAlternativesForCriteria(problemUser, criteria.getId(), alternativesList);
					AHP ahpDataAlternatives = runAHPeig(matrix);
					for (int i = 0; i < alternativesList.size(); i++) {
						alternativesCriteriaAnswerAHPList
								.add(new AlternativesCriteriaAnswerAhp(new Criteria(criteria.getId()),
										new Alternatives(alternativesList.get(i).getId()),
										(float) ahpDataAlternatives.getWeights()[i]));
					}
					alternativesCriteriaCrAHPList.add(new AlternativesCriteriaCrAhp(
							new Criteria(criteria.getId()), (float) ahpDataAlternatives.getCr()));
				}
			}
		}
		problemUserAhp.setAlternativesCriteriaCrAhp(alternativesCriteriaCrAHPList);
		problemUserAhp.setAlternativesCriteriaAnswerAhp(alternativesCriteriaAnswerAHPList);
		System.out.println("--------------------------------- Complete Criteria Alternatives  -----------------------------------");
	}

	/**
	 * Run tha AHP algorithm and save the results for the factors of criteria
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param problemUserAhp used to save the results of the algorithm to the database.
	 */
	private void factorsForCriteriaAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp) {
		List<FactorAnswerAhp> factorAnswerAHPList = new ArrayList<>();
		List<FactorCrAhp> factorCrAhpList = new ArrayList<>();
		List<Criteria> criteriaList = problemUser.getProblem().getCriteria();
		for (Criteria criteria : criteriaList) {
			if (criteria.getFactor().size() > 1) {
				System.out.println("--------------------------------- Run Facto for Criteria: " + criteria.getName() + "  -----------------------------------");
				List<Factor> factorList = criteria.getFactor();
				double[][] matrix = getMatrixFactorForCriteria(problemUser, criteria.getId(), factorList);
				AHP ahpDataFactor = runAHPeig(matrix);
				for (int i = 0; i < factorList.size(); i++) {
					factorAnswerAHPList.add(new FactorAnswerAhp(new Criteria(criteria.getId()),
							new Factor(factorList.get(i).getId()), (float) ahpDataFactor.getWeights()[i]));
				}
				factorCrAhpList
						.add(new FactorCrAhp(new Criteria(criteria.getId()), (float) ahpDataFactor.getCr()));
			}
		}
		problemUserAhp.setFactorCrAhp(factorCrAhpList);
		problemUserAhp.setFactorAnswerAhp(factorAnswerAHPList);
		System.out.println( "--------------------------------- Complete Factor  -----------------------------------");
	}

	/**
	 * Run tha AHP algorithm and save the results for the criteria
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param problemUserAhp used to save the results of the algorithm to the database.
	 */
	private void criteriaAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp ) {
		System.out.println("--------------------------------- Run Criteria  -----------------------------------");
		List<Criteria> criteriaList = problemUser.getProblem().getCriteria();
		if (criteriaList.size() > 0) {
			double[][] matrix = getMatrixCriteria(problemUser, criteriaList);
			AHP ahpDataCriteria = runAHPeig(matrix);
			List<CriteriaAnswerAhp> criteriaAnswerAHPList = new ArrayList<>();
			for (int i = 0; i < criteriaList.size(); i++) {
				criteriaAnswerAHPList.add(new CriteriaAnswerAhp(new Criteria(criteriaList.get(i).getId()),
						(float) ahpDataCriteria.getWeights()[i]));
			}
			problemUserAhp.setCriteriaCrAhp(new CriteriaCrAhp((float) ahpDataCriteria.getCr()));
			problemUserAhp.setCriteriaAnswerAhp(criteriaAnswerAHPList);
		}
		System.out.println( "--------------------------------- Complete Criteria  -----------------------------------");
	}

	/**
	 * Generate the matrix which are necessary for tha AHP algorithm for the alternatives of factors
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param factorId the id of the factor we called to make the matrix.
	 * @param alternativesList the list of the alternatives we make the matrix.
	 *
	 * @return the Matrix
	 */
	private double[][] getMatrixAlternativesForFactors(ProblemUser problemUser, int factorId, List<Alternatives> alternativesList) {
		double[][] matrix = new double[alternativesList.size()][alternativesList.size()];
		for (int i = 0; i < alternativesList.size(); i++) {
			for (int j = 0; j < alternativesList.size(); j++) {
				if (i != j) {
					final int iFinal = i;
					final int jFinal = j;
					AlternativesFactorAnswer alternativesFactorAnswer = problemUser
							.getAlternativesFactorAnswer().stream()
							.filter(alternativesFactorTmp -> alternativesFactorTmp.getAlternativesTop()
									.getId() == alternativesList.get(iFinal).getId()
									&& alternativesFactorTmp.getAlternativesLeft()
											.getId() == alternativesList.get(jFinal).getId()
									&& alternativesFactorTmp.getFactor().getId() == factorId)
							.findAny().orElse(null);
					if (alternativesFactorAnswer != null)
						matrix[i][j] = alternativesFactorAnswer.getWeight();
					else
						matrix[i][j] = 1;

				} else
					matrix[i][j] = 1;
			}
		}
		return matrix;
	}

	/**
	 * Generate the matrix which are necessary for tha AHP algorithm for the alternatives of criteria
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param criteriaId the id of the criteria we called to make the matrix.
	 * @param alternativesList the list of the alternatives we make the matrix.
	 *
	 * @return the Matrix
	 */
	private double[][] getMatrixAlternativesForCriteria(ProblemUser problemUser, int criteriaId, List<Alternatives> alternativesList) {
		double[][] matrix = new double[alternativesList.size()][alternativesList
				.size()];
		for (int i = 0; i < alternativesList.size(); i++) {
			for (int j = 0; j < alternativesList.size(); j++) {
				if (i != j) {
					final int iFinal = i;
					final int jFinal = j;
					AlternativesCriteriaAnswer alternativesCriteriaAnswer = problemUser
							.getAlternativesCriteriaAnswer().stream()
							.filter(alternativesCriteriaTmp -> alternativesCriteriaTmp
									.getAlternativesTop()
									.getId() == alternativesList.get(iFinal).getId()
									&& alternativesCriteriaTmp.getAlternativesLeft()
											.getId() == alternativesList.get(jFinal).getId()
									&& alternativesCriteriaTmp.getCriteria().getId() == criteriaId)
							.findAny().orElse(null);
					if (alternativesCriteriaAnswer != null)
						matrix[i][j] = alternativesCriteriaAnswer.getWeight();
					else
						matrix[i][j] = 1;

				} else
					matrix[i][j] = 1;
			}
		}
		return matrix;
	}

	/**
	 * Generate the matrix which are necessary for tha AHP algorithm for the factors of criteria
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param criteriaId the id of the criteria we called to make the matrix.
	 * @param factorList the list of the factors we make the matrix.
	 *
	 * @return the Matrix
	 */
	private double[][] getMatrixFactorForCriteria(ProblemUser problemUser, int criteriaId, List<Factor> factorList) {
		double[][] matrix = new double[factorList.size()][factorList.size()];
		for (int i = 0; i < factorList.size(); i++) {
			for (int j = 0; j < factorList.size(); j++) {
				if (i != j) {
					final int iFinal = i;
					final int jFinal = j;
					FactorAnswer factorAnswer = problemUser.getFactorAnswer().stream()
							.filter(factorAnswerTmp -> factorAnswerTmp.getFactorTop()
									.getId() == factorList.get(iFinal).getId()
									&& factorAnswerTmp.getFactorLeft().getId() == factorList.get(jFinal)
											.getId()
									&& factorAnswerTmp.getCriteria().getId() == criteriaId)
							.findAny().orElse(null);
					if (factorAnswer != null)
						matrix[i][j] = factorAnswer.getWeight();
					else
						matrix[i][j] = 1;
				} else
					matrix[i][j] = 1;
			}
		}
		return matrix;
	}

	/**
	 * Generate the matrix which are necessary for tha AHP algorithm for the criteria
	 *
	 * @param problemUser have the data from the answers of a simple user.
	 * @param criteriaList the list of the criteria we make the matrix.
	 *
	 * @return the Matrix
	 */
	private double[][] getMatrixCriteria(ProblemUser problemUser, List<Criteria> criteriaList) {
		double[][] matrix = new double[criteriaList.size()][criteriaList.size()];
		for (int i = 0; i < criteriaList.size(); i++) {
			for (int j = 0; j < criteriaList.size(); j++) {
				if (i != j) {
					final int iFinal = i;
					final int jFinal = j;
					CriteriaAnswer criteriaAnswer = problemUser.getCriteriaAnswer().stream()
							.filter(criteriaAnswerTmp -> criteriaAnswerTmp.getCriteriaTop()
									.getId() == criteriaList.get(iFinal).getId()
									&& criteriaAnswerTmp.getCriteriaLeft().getId() == criteriaList
											.get(jFinal).getId())
							.findAny().orElse(null);
					if (criteriaAnswer != null)
						matrix[i][j] = criteriaAnswer.getWeight();
					else
						matrix[i][j] = 1;
				} else
					matrix[i][j] = 1;
			}
		}
		return matrix;
	}

	/**
	 * That function get a matrix of data and execute the AHP algorithm to produce
	 * the results of the answers.
	 *
	 * @return AHP the results of the answers
	 */
	public AHP runAHPeig(double[][] matrix) {

		Matrix matrixEig = new Matrix(matrix);

		EigenvalueDecomposition e = matrixEig.eig();
		double maxDiagonValue = 0.0;
		int positionDiagonValue = 0;
		for (int i = 0; i < e.getD().getRowDimension(); i++) {
			if (Math.abs(e.getD().get(i, i)) > maxDiagonValue) {
				maxDiagonValue = Math.abs(e.getD().get(i, i));
				positionDiagonValue = i;
			}
		}
		System.out.println("maxDiagonValue: " + maxDiagonValue + " | positionDiagonValue: " + positionDiagonValue);
		double sumRightVector = 0.0;
		for (int i = 0; i < e.getV().getRowDimension(); i++) {
			sumRightVector += Math.abs(e.getV().get(i, positionDiagonValue));
		}
		System.out.println("sumRightVector: " + sumRightVector);
		double[] weight = new double[e.getV().getRowDimension()];
		for (int i = 0; i < e.getV().getRowDimension(); i++) {
			weight[i] = Math.abs(e.getV().get(i, positionDiagonValue)) / sumRightVector;
			System.out.println(
					Math.abs(e.getV().get(i, positionDiagonValue)) + " / " + sumRightVector + " = " + weight[i]);
		}
		 
		double ci = (maxDiagonValue - e.getV().getRowDimension()) / (e.getV().getRowDimension() - 1);
		System.out.println("CI= " + ci);
		double ri = Util.getRi(e);
		double cr = ri == 0 ? 0 : ci / ri;
		DecimalFormat df = new DecimalFormat("###.###");
		System.out.println("CR= " + df.format(cr));

		return new AHP(weight, cr);
	}


}
