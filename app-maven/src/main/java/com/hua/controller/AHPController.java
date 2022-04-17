package com.hua.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.model.GenerateAhpResultsExcell;
import com.hua.model.Problem;
import com.hua.model.ProblemUserAhp;
import com.hua.repository.ProblemsRepository;
import com.hua.repository.ProblemsUserAhpRepository;
import com.hua.repository.ProblemsUserRepository;
import com.hua.repository.UsersGroupRepository;
import com.hua.util.GenerateAhpExcell;
import com.hua.util.GenerateDataExcell;
import com.hua.util.RunAHP;

@Controller
@RequestMapping("/runAHP")
public class AHPController {



	@Autowired
	private ProblemsRepository problemsRepository;
	@Autowired
	private UsersGroupRepository userGroupRepository;
	@Autowired
	private ProblemsUserRepository problemsUserRepository;
	@Autowired
	private ProblemsUserAhpRepository problemsUserAHPRepository;

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
	@GetMapping("downloadPreview/{id}")
	public String getManage(Model model, Principal principal, @PathVariable(required = true) int id) {

		model.addAttribute("problemsUserAhp", problemsUserAHPRepository.findAllByProblemUserProblemId(id).isPresent() ?
				problemsUserAHPRepository.findAllByProblemUserProblemId(id).get() : new ArrayList<>());
		model.addAttribute("problemsUserAhpId", id);
		return "problems/downloadPreview.html";
	}
	@PostMapping("excell")
	public ResponseEntity<InputStreamResource> getExcellAHP(Model model, Principal principal,
			@ModelAttribute GenerateAhpResultsExcell generateAhpResultsExcell) {
		
		return new GenerateAhpExcell().makeExceel(generateAhpResultsExcell.getId(), problemsUserAHPRepository, generateAhpResultsExcell);
		
	}
	@GetMapping("excell/data/{id}")
	public ResponseEntity<InputStreamResource> getExcellData(Model model, Principal principal, @PathVariable(required = true) int id) {
				
		return new GenerateDataExcell(problemsUserAHPRepository).makeExceel(id);
		
	}
}
