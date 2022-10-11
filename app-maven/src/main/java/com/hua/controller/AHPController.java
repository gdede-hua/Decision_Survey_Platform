package com.hua.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.model.GenerateAhpResultsExcel;
import com.hua.model.Problem;
import com.hua.model.ProblemUserAhp;
import com.hua.repository.ProblemsRepository;
import com.hua.repository.ProblemsUserAhpRepository;
import com.hua.repository.ProblemsUserRepository;
import com.hua.repository.UsersGroupRepository;
import com.hua.util.GenerateAhpExcell;
import com.hua.util.GenerateDataExcel;
import com.hua.util.RunAHP;

@Controller
@RequestMapping("/runAHP")
public class AHPController {

	private final ProblemsRepository problemsRepository;
	private final UsersGroupRepository userGroupRepository;
	private final ProblemsUserRepository problemsUserRepository;
	private final ProblemsUserAhpRepository problemsUserAHPRepository;

	public AHPController(ProblemsRepository problemsRepository, UsersGroupRepository userGroupRepository, ProblemsUserRepository problemsUserRepository, ProblemsUserAhpRepository problemsUserAHPRepository) {
		this.problemsRepository = problemsRepository;
		this.userGroupRepository = userGroupRepository;
		this.problemsUserRepository = problemsUserRepository;
		this.problemsUserAHPRepository = problemsUserAHPRepository;
	}

	/**
	 * Endpoint for the run of tha AHP algorithm
	 *
	 * @param id the id of the research
	 *
	 * @return to user research page
	 */
	@GetMapping("{id}")
	public String getManageRunAHP(Model model, Principal principal, @PathVariable(required = false) int id) {
		Optional<List<ProblemUserAhp>> problemUserAHPListOpt = problemsUserAHPRepository.findAllByProblemUserProblemId(id);
		Optional<Problem> problem = problemsRepository.findById(id);
		if ( problemUserAHPListOpt.isEmpty() ) {
			RunAHP r = new RunAHP(problemsUserRepository, problemsUserAHPRepository);
			r.runAHP(id);
		}
		problem.get().setStatus(4);
		problemsRepository.save(problem.get());
		
		model.addAttribute("problems", problemsRepository.findAllByUserUsername(principal.getName()).isPresent() ? 
				problemsRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>() );
		model.addAttribute("usersGroup", userGroupRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				userGroupRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>());
		return "problems/problems.html";
	}
	/**
	 * At the endpoint you could download the AHP results
	 * and all the answers
	 *
	 * @param id the id of the research
	 *
	 * @return to download page
	 */
	@GetMapping("downloadPreview/{id}")
	public String getManage(Model model, @PathVariable int id) {

		model.addAttribute("problemsUserAhp", problemsUserAHPRepository.findAllByProblemUserProblemId(id).isPresent() ?
				problemsUserAHPRepository.findAllByProblemUserProblemId(id).get() : new ArrayList<>());
		model.addAttribute("problemsUserAhpId", id);
		return "problems/downloadPreview.html";
	}
	/**
	 * At the endpoint you could download the AHP
	 *
	 *
	 * @return an Excel with the AHP results
	 */
	@PostMapping("excel")
	public ResponseEntity<InputStreamResource> getExcelAHP(@ModelAttribute GenerateAhpResultsExcel generateAhpResultsExcel) {
		
		return new GenerateAhpExcell().makeExceel(generateAhpResultsExcel.getId(), problemsUserAHPRepository, generateAhpResultsExcel);
		
	}
	/**
	 * At the endpoint you could download the survey answers
	 *
	 *
	 * @return an Excel with the answers
	 */
	@GetMapping("excel/data/{id}")
	public ResponseEntity<InputStreamResource> getExcelData(@PathVariable int id) {
				
		return new GenerateDataExcel(problemsUserAHPRepository).makeExceel(id);
		
	}
}
