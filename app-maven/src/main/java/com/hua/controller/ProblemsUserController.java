package com.hua.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.model.AlternativesCriteriaAnswer;
import com.hua.model.AlternativesFactorAnswer;
import com.hua.model.Criteria;
import com.hua.model.CriteriaAnswer;
import com.hua.model.Factor;
import com.hua.model.FactorAnswer;
import com.hua.model.ProblemUser;
import com.hua.model.Users;
import com.hua.repository.ProblemsUserRepository;
import com.hua.repository.UsersRepository;
import com.hua.util.Util;

@Controller
@RequestMapping("/problemsUser")
public class ProblemsUserController {
	
	private final ProblemsUserRepository problemsUserRepository;
	private final UsersRepository userRepository;

	public ProblemsUserController(ProblemsUserRepository problemsUserRepository, UsersRepository userRepository) {
		this.problemsUserRepository = problemsUserRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Endpoint for the load of the survey
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 *
	 * @return to survey page
	 */
	@GetMapping()
	public String getManageUser(Model model, Principal principal) {
		model.addAttribute("problemsUser", problemsUserRepository.findAllByUserUsernameAndStatus(principal.getName(), 1).isPresent() ? 
					problemsUserRepository.findAllByUserUsernameAndStatus(principal.getName(), 1).get() : new ArrayList<>()  );
		return "problems/problemsUser.html";
	}
	/**
	 * Endpoint for the opening of survey
	 *
	 * @param model Interface to set up information for the page
	 * @param id The id of the survey
	 *
	 * @return to user answer page
	 */
	@GetMapping("run/{id}")
	public String getManageUserRun(Model model, @PathVariable String id) {
		ProblemUser problemsUser = problemsUserRepository.findById(Integer.parseInt(id)).get();
		model.addAttribute("problemsUser", problemsUser);
		ArrayList<Factor> factorList = new ArrayList<>();
		for (Criteria criteria : problemsUser.getProblem().getCriteria()) {
			for (Factor factor : criteria.getFactor()) {
				factorList.add(factor);
	        }
        }
		model.addAttribute("factors", factorList);
		return "problems/problemsUserRun.html";
	}
	/**
	 * Endpoint for the save the survey data
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param problemUser that item have all the aswer of the user
	 *
	 * @return to survey page
	 */
	@PostMapping("save")
	public String UserSaveResearch(Model model, Principal principal, @ModelAttribute ProblemUser problemUser) {
		problemUser.setStatus(2);
		Optional<Users> user = userRepository.findByUsername(problemUser.getUser().getUsername());
		problemUser.setUser(user.get());
		
		if ( problemUser.getCriteriaAnswer() != null ) {
			List<CriteriaAnswer> criteriaAnswerTmp = new ArrayList<>(problemUser.getCriteriaAnswer());
			for (CriteriaAnswer criteriaAnswer : criteriaAnswerTmp)
				problemUser.getCriteriaAnswer().add(new CriteriaAnswer(criteriaAnswer.getCriteriaLeft(), criteriaAnswer.getCriteriaTop(), Util.weightReverse(criteriaAnswer.getWeight()) ));
		}
		if ( problemUser.getFactorAnswer() != null ) {
			List<FactorAnswer> factorAnswerTmp = new ArrayList<>(problemUser.getFactorAnswer());
			for (FactorAnswer factorAnswer : factorAnswerTmp)
				problemUser.getFactorAnswer().add(new FactorAnswer(factorAnswer.getCriteria(), factorAnswer.getFactorLeft(), factorAnswer.getFactorTop(), Util.weightReverse(factorAnswer.getWeight()) ));
		}
		if ( problemUser.getAlternativesCriteriaAnswer() != null ) {
			List<AlternativesCriteriaAnswer> alternativesCriteriaAnswerTmp = new ArrayList<>(problemUser.getAlternativesCriteriaAnswer());
			for (AlternativesCriteriaAnswer alternativesCriteriaAnswer : alternativesCriteriaAnswerTmp)
				problemUser.getAlternativesCriteriaAnswer().add(new AlternativesCriteriaAnswer(alternativesCriteriaAnswer.getCriteria(), alternativesCriteriaAnswer.getAlternativesLeft(), alternativesCriteriaAnswer.getAlternativesTop(), Util.weightReverse(alternativesCriteriaAnswer.getWeight()) ));
		}
		if ( problemUser.getAlternativesFactorAnswer() != null ) {
			List<AlternativesFactorAnswer> alternativesFactorAnswerTmp = new ArrayList<>(problemUser.getAlternativesFactorAnswer());
			for (AlternativesFactorAnswer alternativesFactorAnswer : alternativesFactorAnswerTmp)
				problemUser.getAlternativesFactorAnswer().add(new AlternativesFactorAnswer(alternativesFactorAnswer.getFactor(), alternativesFactorAnswer.getAlternativesLeft(), alternativesFactorAnswer.getAlternativesTop(), Util.weightReverse(alternativesFactorAnswer.getWeight()) ));
		}
		problemsUserRepository.save(problemUser);
		model.addAttribute("problemsUser", problemsUserRepository.findAllByUserUsernameAndStatus(principal.getName(), 1).isPresent() ? 
			problemsUserRepository.findAllByUserUsernameAndStatus(principal.getName(), 1).get() : new ArrayList<>()  );
		return "problems/problemsUser.html";
	}
	
}
