package com.hua.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hua.model.Groups;
import com.hua.model.Problem;
import com.hua.model.ProblemUser;
import com.hua.model.ProblemUserExpire;
import com.hua.model.Users;
import com.hua.repository.ProblemUserExpireRepository;
import com.hua.repository.ProblemsRepository;
import com.hua.repository.ProblemsUserRepository;
import com.hua.repository.UsersGroupRepository;
import com.hua.repository.UsersRepository;
import com.hua.util.Mailer;

/**
 * Class for the management of the problems.
 * @author      John Nikolaou
 */
@Controller
@RequestMapping("/problems")
public class ProblemsController {

	private final ProblemsRepository problemsRepository;
	private final UsersGroupRepository userGroupRepository;
	private final ProblemsUserRepository problemsUserRepository;
	private final ProblemUserExpireRepository problemUserExpireRepository;
	private final UsersRepository userRepository;
	
	@Value("${security.mail.sslEnable}")
	private boolean sslEnable;
	@Value("${security.mail.host}")
	private String host;
	@Value("${security.mail.from}")
	private String from;
	@Value("${security.mail.password}")
	private String password;
	@Value("${security.mail.socketPort}")
	private String socketPort;
	@Value("${security.mail.smtpPort}")
	private String smtpPort;

	public ProblemsController(ProblemsRepository problemsRepository, UsersGroupRepository userGroupRepository, ProblemsUserRepository problemsUserRepository, ProblemUserExpireRepository problemUserExpireRepository, UsersRepository userRepository) {
		this.problemsRepository = problemsRepository;
		this.userGroupRepository = userGroupRepository;
		this.problemsUserRepository = problemsUserRepository;
		this.problemUserExpireRepository = problemUserExpireRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Endpoint for the load of the research page
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 *
	 * @return to research page
	 */
	@GetMapping("manage")
	public String getManage(Model model, Principal principal) {
		return loadProblemsPage(model, principal);
	}
	/**
	 * Endpoint for to delete a research
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param id the id of the research
	 *
	 * @return to research page
	 */
	@GetMapping("manage/delete/{id}")
	public String getDelete(Model model, Principal principal, @PathVariable(required = false) String id) {
		problemsRepository.deleteById(Integer.parseInt(id));
		return loadProblemsPage(model, principal);
	}
	/**
	 * Endpoint for the wizard.
	 * The wizard used to create research.
	 *
	 * @param model Interface to set up information for the page
	 * @param id the id of the research if we are on edit mode
	 *
	 * @return to wizard page
	 */
	@GetMapping(path = {"/wizard", "/wizard/{id}"})
	public String getWizard(Model model, @PathVariable(required = false) String id) {
		if (id!=null)
			model.addAttribute("problem", problemsRepository.findById(Integer.parseInt(id)).get());
		else
			model.addAttribute("problem", new Problem());
		return "problems/wizard.html";
	}
	/**
	 * Endpoint for create/edit research
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param problem that item have all the information for the creation of the research
	 *
	 * @return to research page
	 */
	@PostMapping("wizard/save")
	public String wizardStart(Model model, Principal principal, @ModelAttribute Problem problem) {
		problem.setStatus(1);
		Optional<Users> userPrincipal = userRepository.findByUsername(principal.getName());
		problem.setUser(userPrincipal.get());
		problemsRepository.save(problem);
		model.addAttribute("successMsg", "Successfully add research: "+problem.getName());

		return loadProblemsPage(model, principal);
	}
	/**
	 * Endpoint for publishing a research
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param problemId The id of the research we want to publish
	 * @param userGroupId the group of user we would publish the research
	 *
	 * @return to research page
	 */
	@PostMapping("manage/start")
	public String getManageStart(Model model, Principal principal, @RequestParam("problemId") int problemId, @RequestParam("userGroupId") int userGroupId, 
			@RequestParam("expireDate") Date expireDate) {
		Optional<Groups> groupOpt = userGroupRepository.findById(userGroupId);
		Optional<Problem> problemOpt = problemsRepository.findById(problemId);
		if (groupOpt.isPresent() && problemOpt.isPresent()) {
			Groups group = groupOpt.get();
			Problem problem = problemOpt.get();
			Mailer mailer = new Mailer("request form assign", "You assign in new request form", sslEnable, host, from, password, socketPort, smtpPort);
			for (Users user : group.getUsers()) {
				if ( user.getEnabled()==1 ) {
					ProblemUser problemUser = new ProblemUser(user, 1, problemOpt.get());
					problemsUserRepository.save(problemUser);
					try {
					mailer.SendEmail(user.getEmail());
					} catch (Exception e) {
						model.addAttribute("warningMsg", "Unable to send email");
					}
				}
			}
			problem.setStatus(2);
			problemsRepository.save(problem);
			
			problemUserExpireRepository.save(new ProblemUserExpire(problem, expireDate));
			
			model.addAttribute("successMsg", "Successfully start the research: "+problemOpt.get().getName());
		} else {
			model.addAttribute("errorMsg", "Unable to find user group");
		}

		return loadProblemsPage(model, principal);
	}
	/**
	 * Unique function for the load of the information for the research page
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 *
	 * @return to research page
	 */
	private String loadProblemsPage(Model model, Principal principal) {
		model.addAttribute("problems", problemsRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				problemsRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>() );
		model.addAttribute("usersGroup", userGroupRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				userGroupRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>());
		return "problems/problems.html";
	}
}
