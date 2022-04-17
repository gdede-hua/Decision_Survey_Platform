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

@Service
public class RunAHP {

	private ProblemsUserRepository problemsUserRepository;
	private ProblemsUserAhpRepository problemsUserAHPRepository;

	public RunAHP(ProblemsUserRepository problemsUserRepository, ProblemsUserAhpRepository problemsUserAHPRepository) {
		this.problemsUserRepository = problemsUserRepository;
		this.problemsUserAHPRepository = problemsUserAHPRepository;
	}

	public void runAHP(int id) {
		Optional<List<ProblemUser>> problemUserListOpt = problemsUserRepository.findAllByProblemIdAndStatus(id, 2);
		if (problemUserListOpt.isPresent()) {
			List<ProblemUser> problemUserList = problemUserListOpt.get();
			for (ProblemUser problemUser : problemUserList) {

				ProblemUserAhp problemUserAhp = new ProblemUserAhp();
				problemUserAhp.setProblemUser(new ProblemUser(problemUser.getId(), problemUser.getUser()));
				List<Criteria> criteriaList = problemUser.getProblem().getCriteria();
				if (criteriaList.size() > 0) {

					System.out.println(
							"--------------------------------- Run Criteria  -----------------------------------");
					double[][] matrixCriteria = new double[criteriaList.size()][criteriaList.size()];
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
									matrixCriteria[i][j] = criteriaAnswer.getWeight();
								else
									matrixCriteria[i][j] = 1;
							} else
								matrixCriteria[i][j] = 1;
						}
					}
					AHP ahpDataCriteria = runAHPeig(matrixCriteria);
					List<CriteriaAnswerAhp> criteriaAnswerAHPList = new ArrayList<>();
					for (int i = 0; i < criteriaList.size(); i++) {
						criteriaAnswerAHPList.add(new CriteriaAnswerAhp(new Criteria(criteriaList.get(i).getId()),
								(float) ahpDataCriteria.getWeights()[i]));
					}
					problemUserAhp.setCriteriaCrAhp(new CriteriaCrAhp((float) ahpDataCriteria.getCr()));
					problemUserAhp.setCriteriaAnswerAhp(criteriaAnswerAHPList);
				}
				System.out.println(
						"--------------------------------- Complete Criteria  -----------------------------------");

				List<FactorAnswerAhp> factorAnswerAHPList = new ArrayList<>();
				List<FactorCrAhp> factorCrAhpList = new ArrayList<>();
				for (Criteria criteria : criteriaList) {
					if (criteria.getFactor().size() > 1) {
						System.out.println("--------------------------------- Run Facto for Criteria: "
								+ criteria.getName() + "  -----------------------------------");
						List<Factor> factorList = criteria.getFactor();
						double[][] matrixFactor = new double[factorList.size()][factorList.size()];
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
										matrixFactor[i][j] = factorAnswer.getWeight();
									else
										matrixFactor[i][j] = 1;
								} else
									matrixFactor[i][j] = 1;
							}
						}
						AHP ahpDataFactor = runAHPeig(matrixFactor);
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
				System.out.println(
						"--------------------------------- Complete Factor  -----------------------------------");

				List<AlternativesCriteriaAnswerAhp> alternativesCriteriaAnswerAHPList = new ArrayList<>();
				List<AlternativesCriteriaCrAhp> alternativesCriteriaCrAHPList = new ArrayList<>();
				for (Criteria criteria : criteriaList) {
					if (criteria.getFactor().size() == 0) {
						System.out.println("--------------------------------- Run Alternatives for Criteria: "
								+ criteria.getName() + "  -----------------------------------");
						List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
						if (alternativesList.size() > 0) {
							double[][] matrixAlternatives = new double[alternativesList.size()][alternativesList
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
											matrixAlternatives[i][j] = alternativesCriteriaAnswer.getWeight();
										else
											matrixAlternatives[i][j] = 1;

									} else
										matrixAlternatives[i][j] = 1;
								}
							}
							AHP ahpDataAlternatives = runAHPeig(matrixAlternatives);
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
				System.out.println(
						"--------------------------------- Complete Criteria Alternatives  -----------------------------------");

				ArrayList<Factor> factorList = new ArrayList<>();
				for (Criteria criteria : problemUser.getProblem().getCriteria()) {
					for (Factor factor : criteria.getFactor()) {
						factorList.add(factor);
					}
				}
				List<AlternativesFactorAnswerAhp> alternativesFactorAnswerAHPList = new ArrayList<>();
				List<AlternativesFactorCrAhp> alternativesFactorCrAHPList = new ArrayList<>();
				for (Factor factor : factorList) {
					System.out.println("--------------------------------- Run Alternatives for Factors: "
							+ factor.getName() + "  -----------------------------------");
					List<Alternatives> alternativesList = problemUser.getProblem().getAlternatives();
					if (alternativesList.size() > 0) {
						double[][] matrixAlternatives = new double[alternativesList.size()][alternativesList.size()];
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
										matrixAlternatives[i][j] = alternativesFactorAnswer.getWeight();
									else
										matrixAlternatives[i][j] = 1;

								} else
									matrixAlternatives[i][j] = 1;
							}
						}
						AHP ahpDataAlternatives = runAHPeig(matrixAlternatives);
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
		}

	}

	public AHP runAHPeig(double[][] matrix) {

		Matrix matrixEig = new Matrix(matrix);
//		 System.out.println("--------------------------------- Matrix -----------------------------------");
//		 System.out.println(jamaToString(matrixEig));

		EigenvalueDecomposition e = matrixEig.eig();
//		 System.out.println("----------- Diagonal -------------");
//		 System.out.println(jamaToString(e.getD()));
		double maxDiagonValue = 0.0;
		int poisitionDiagonValue = 0;
		for (int i = 0; i < e.getD().getRowDimension(); i++) {
			if (Math.abs(e.getD().get(i, i)) > maxDiagonValue) {
				maxDiagonValue = Math.abs(e.getD().get(i, i));
				poisitionDiagonValue = i;
			}
		}
		System.out.println("maxDiagonValue: " + maxDiagonValue + " | poisitionDiagonValue: " + poisitionDiagonValue);
//		 System.out.println("----------- Vectors -------------");
//		 System.out.println(jamaToString(e.getV()));
		double sumRightVector = 0.0;
		for (int i = 0; i < e.getV().getRowDimension(); i++) {
			sumRightVector += Math.abs(e.getV().get(i, poisitionDiagonValue));
		}
		System.out.println("sumRightVector: " + sumRightVector);
		double[] weight = new double[e.getV().getRowDimension()];
		for (int i = 0; i < e.getV().getRowDimension(); i++) {
			weight[i] = Math.abs(e.getV().get(i, poisitionDiagonValue)) / sumRightVector;
			System.out.println(
					Math.abs(e.getV().get(i, poisitionDiagonValue)) + " / " + sumRightVector + " = " + weight[i]);
		}
		
//		 System.out.println("----------- weight -------------");
//		 for (int i = 0; i < weight.length; i++) {
//			 System.out.println(weight[i]+" ");
//		 }
		 
		double ci = (maxDiagonValue - e.getV().getRowDimension()) / (e.getV().getRowDimension() - 1);
		System.out.println("CI= " + ci);
		double ri = 0;
		if (e.getV().getRowDimension() == 1 || e.getV().getRowDimension() == 2) {
			ri = 0;
		} else if (e.getV().getRowDimension() == 3) {
			ri = 0.52;
		} else if (e.getV().getRowDimension() == 4) {
			ri = 0.89;
		} else if (e.getV().getRowDimension() == 5) {
			ri = 1.11;
		} else if (e.getV().getRowDimension() == 6) {
			ri = 1.25;
		} else if (e.getV().getRowDimension() == 7) {
			ri = 1.35;
		} else if (e.getV().getRowDimension() == 8) {
			ri = 1.4;
		} else if (e.getV().getRowDimension() == 9) {
			ri = 1.45;
		} else if (e.getV().getRowDimension() == 10) {
			ri = 1.49;
		}
		double cr = ri == 0 ? 0 : ci / ri;
		DecimalFormat df = new DecimalFormat("###.###");
		System.out.println("CR= " + df.format(cr));

		return new AHP(weight, cr);
	}
//	private String jamaToString(Matrix m) {
//	    StringBuilder b = new StringBuilder();
//	    DecimalFormat df = new DecimalFormat("###.###");
//	    b.append("[");
//	    for(int i=0; i<m.getRowDimension(); i++) {
//	        for(int j=0; j<m.getColumnDimension(); j++) {
//	            b.append(df.format((m.get(i, j))) );
//	            if(j<m.getColumnDimension() - 1) b.append(", ");
//	        }
//	        if(i<m.getRowDimension() - 1) b.append(";\n");
//	    }
//	    b.append("]");
//	    return b.toString();
//	}
}
