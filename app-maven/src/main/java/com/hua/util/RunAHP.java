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
 * run the AHP algorithm
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
	 * run the AHP algorithm
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
				List<Criteria> criteriaList = problemUser.getProblem().getCriteria();

				criteriaAhp(problemUser, problemUserAhp, criteriaList);

				factorsForCriteriaAhp(problemUser, problemUserAhp, criteriaList);

				alternativesForCriteriaAhp(problemUser, problemUserAhp, criteriaList);

				alternativesForFactorsAhp(problemUser, problemUserAhp);
			}
		}

	}

	/**
	 * Run tha AHP algorithm and save the results for the alternatives of factors
	 */
	private void alternativesForFactorsAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp) {
		ArrayList<Factor> factorList = new ArrayList<>();
		for (Criteria criteria : problemUser.getProblem().getCriteria()) {
			factorList.addAll(criteria.getFactor());
		}
		List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAHPList = new ArrayList<>();
		List<AlternativesFactorCrAhp> alternativesFactorCrAHPList = new ArrayList<>();
		for (Factor factor : factorList) {
			System.out.println("--------------------------------- Run Alternatives for Factors: "
					+ factor.getName() + "  -----------------------------------");
			List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
			if (alternativesList.size() > 0) {
				double[][] matrix = getMatrixAlternativesForFactors(problemUser, factor, alternativesList);
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
	 */
	private void alternativesForCriteriaAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp, List<Criteria> criteriaList) {
		List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAHPList = new ArrayList<>();
		List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAHPList = new ArrayList<>();
		for (Criteria criteria : criteriaList) {
			if (criteria.getFactor().size() == 0) {
				System.out.println("--------------------------------- Run Alternatives for Criteria: "
						+ criteria.getName() + "  -----------------------------------");
				List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
				if (alternativesList.size() > 0) {
					double[][] matrix = getMatrixAlternativesForCriteria(problemUser, criteria, alternativesList);
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
	 */
	private void factorsForCriteriaAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp, List<Criteria> criteriaList) {
		List<FactorAnswerAhp> factorAnswerAHPList = new ArrayList<>();
		List<FactorCrAhp> factorCrAhpList = new ArrayList<>();
		for (Criteria criteria : criteriaList) {
			if (criteria.getFactor().size() > 1) {
				System.out.println("--------------------------------- Run Facto for Criteria: " + criteria.getName() + "  -----------------------------------");
				List<Factor> factorList = criteria.getFactor();
				double[][] matrix = getMatrixFactorForCriteria(problemUser, criteria, factorList);
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
	 */
	private void criteriaAhp(ProblemUser problemUser, ProblemUserAhp problemUserAhp, List<Criteria> criteriaList) {
		System.out.println("--------------------------------- Run Criteria  -----------------------------------");
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
	 * @return the Matrix
	 */
	private double[][] getMatrixAlternativesForFactors(ProblemUser problemUser, Factor factor, List<Alternatives> alternativesList) {
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
									&& alternativesFactorTmp.getFactor().getId() == factor.getId())
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
	 * @return the Matrix
	 */
	private double[][] getMatrixAlternativesForCriteria(ProblemUser problemUser, Criteria criteria, List<Alternatives> alternativesList) {
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
									&& alternativesCriteriaTmp.getCriteria().getId() == criteria
											.getId())
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
	 * @return the Matrix
	 */
	private double[][] getMatrixFactorForCriteria(ProblemUser problemUser, Criteria criteria, List<Factor> factorList) {
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
									&& factorAnswerTmp.getCriteria().getId() == criteria.getId())
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
	 * run the AHP algorithm
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
