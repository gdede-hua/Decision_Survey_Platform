package com.hua.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hua.model.Authorities;
import com.hua.model.Groups;
import com.hua.model.Users;
import com.hua.repository.UsersGroupRepository;
import com.hua.repository.UsersRepository;
import com.hua.util.JwtUtil;
import com.hua.util.Mailer;

@Controller
@RequestMapping("/users")
public class UsersController {

	private final UsersRepository userRepository;
	private final UsersGroupRepository userGroupRepository;
	private final JwtUtil jwtTokenUtil;

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
	@Value("${security.mail.domain}")
	private String domain;

	public UsersController(UsersRepository userRepository, UsersGroupRepository userGroupRepository, JwtUtil jwtTokenUtil) {
		this.userRepository = userRepository;
		this.userGroupRepository = userGroupRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	/**
	 * Endpoint for the loading of all users
	 *
	 * @param model Interface to set up information for the page
	 *
	 * @return to user page
	 */
	@GetMapping()
	public String getUsers(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "users.html";
	}
	/**
	 * Endpoint for the deleting of a user
	 *
	 * @param model Interface to set up information for the page
	 * @param uuid The id of the user
	 *
	 * @return to user page
	 */
	@PostMapping("delete/{uuid}")
	public String deleteUser(@PathVariable("uuid") String uuid, Model model) {
		Optional<Users> user = userRepository.findById(uuid);
		if (user.isPresent()) {
			user.get().setUsername(uuid);
			user.get().setEmail(uuid);
			user.get().setRealName(uuid);
			user.get().setEnabled(0);
			user.get().setPassword(uuid);
			userRepository.save(user.get());
			model.addAttribute("successMsg", "Successfully invalidate user information: "+user.get().getUsername());
		} else  {
			model.addAttribute("errorMsg", "Unable to find user.");
		}
		model.addAttribute("users",  userRepository.findAll());
		return "users.html";
	}
	/**
	 * Endpoint for the editing of a user
	 *
	 * @param model Interface to set up information for the page
	 * @param request It used for role check
	 * @param userEdit The user information
	 *
	 * @return to user page
	 */
	@PostMapping("edit")
	public String editUser(Model model, HttpServletRequest request, @ModelAttribute Users userEdit) {
		Optional<Users> userDB = userRepository.findById(userEdit.getId());
		if (userDB.isPresent()) {
			userDB.get().setEmail(userEdit.getEmail());
			userDB.get().setRealName(userEdit.getRealName());
			if (request.isUserInRole("ROLE_ADMIN")) {
				userDB.get().getAuthorities().setAuthority(userEdit.getAuthorities().getAuthority());
			}
			userRepository.save(userDB.get());
			model.addAttribute("successMsg", "Successfully update user: "+userDB.get().getUsername());
		} else {
			model.addAttribute("errorMsg", "Unable to find user.");
		}
		model.addAttribute("users",  userRepository.findAll());
		return "users.html";
	}
	/**
	 * Endpoint for the creation of a user
	 *
	 * @param model Interface to set up information for the page
	 * @param user The user information
	 *
	 * @return to login page
	 */
	@PostMapping("/registerAction")
    public String registerAction(Model model, Users user) {
		Optional<Users> userDB = userRepository.findByUsername(user.getUsername());
		if(userDB.isPresent()) {
			model.addAttribute("errorMsg", "Username already exist.");
			return "register.html";
		}

		String uuid = UUID.randomUUID().toString();
		user.setId(uuid);
		user.setEnabled(1);
		user.setAuthorities(new Authorities(uuid, user.getUsername(), "ROLE_USER"));
		userRepository.save(user);
		
        model.addAttribute("register", "Complete Register");
        return "login.html";
    }
	/**
	 * Endpoint for the reset password
	 *
	 * @return to reset page
	 */
	@GetMapping("/reset")
    public String reset() {// validate user info
        return "reset.html";
    }

	/**
	 * Endpoint for the reset email
	 *
	 * @param model Interface to set up information for the page
	 * @param user The user information
	 *
	 * @return to login page
	 */
	@PostMapping("/resetAction")
    public String resetAction(Model model, Users user) {
		Optional<Users> userDB = userRepository.findByEmailAndEnabled(user.getEmail(), 1);
		if(userDB.isPresent() && userDB.get().getEnabled()!=1) {
			model.addAttribute("errorMsg", "Email Couldn't be found.");
			return "login.html";
		}
		
		String token = jwtTokenUtil.generateToken(userDB.get().getUsername());

		Mailer mailer = new Mailer("Reset Password Hua AHP application", "Please click the <a href='"+domain+"/users/resetPage/"+token+"' >link</a>", sslEnable, host, from, password, socketPort, smtpPort);
		
		try {
			mailer.SendEmail(user.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("warningMsg", "Unable to send email");
		}
		
        model.addAttribute("register", "An email has been sent.");
        return "login.html";
    }
	/**
	 * Endpoint for insert your new password
	 *
	 * @param model Interface to set up information for the page
	 * @param jwt key to bind email with user
	 *
	 * @return to login page
	 */
	@GetMapping("/resetPage/{jwt}")
    public String resetPage(Model model, @PathVariable("jwt") String jwt) {
		try {
			if(!jwtTokenUtil.validateToken(jwt)) {
				model.addAttribute("errorMsg", "Incorrect token.");
				return "login.html";
			}
		}catch (Exception e) {
			model.addAttribute("errorMsg", "Incorrect token.");
			return "login.html";
		}
	    model.addAttribute("jwt", jwt);
        return "resetPage.html";
    }
	/**
	 * Endpoint for saving your new password
	 *
	 * @param model Interface to set up information for the page
	 * @param jwt key to bind email with user
	 * @param newPassword the new password
	 *
	 * @return to login page
	 */
	@PostMapping("/resetPageAction/{jwt}")
    public String resetPageAction(Model model, @PathVariable("jwt") String jwt, String newPassword) {
		try {
			if(!jwtTokenUtil.validateToken(jwt)) {
				model.addAttribute("errorMsg", "Incorect token.");
				return "login.html";
			}
		}catch (Exception e) {
			model.addAttribute("errorMsg", "Incorect token.");
			return "login.html";
		}
		String username = jwtTokenUtil.extractUsername(jwt);
		Optional<Users> userDB = userRepository.findByUsername(username);
		userDB.get().setPassword(newPassword);
		userRepository.save(userDB.get());
		
        model.addAttribute("register", "Complete Reset Password");
        return "login.html";
    }
	/**
	 * Endpoint for loading the user groups
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 *
	 * @return to user group page
	 */
	@GetMapping("group")
	public String getUserGroup(Model model, Principal principal) {
		model.addAttribute("users", userRepository.findAllByAuthoritiesAuthorityAndEnabled("ROLE_USER", 1));

		model.addAttribute("usersGroup", userGroupRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				userGroupRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>());
		return "usersGroup.html";
	}
	/**
	 * Endpoint for creation of the user group
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param group Information for the group
	 * @param usersSelect list of the users of the group
	 *
	 * @return to user group page
	 */
	@PostMapping("group/add")
	public String addGroup(HttpServletRequest request, Model model, Principal principal, @ModelAttribute Groups group, @RequestParam String[] usersSelect) {
		List<Users> users = new ArrayList<>();
		for (String user : usersSelect) {
			users.add(new Users(user));
		}
		group.setUsers(users);
		Optional<Users> userPrincipal = userRepository.findByUsername(principal.getName());
		group.setUser(userPrincipal.get());
		userGroupRepository.save(group);
		
		model.addAttribute("users",  userRepository.findAllByAuthoritiesAuthorityAndEnabled("ROLE_USER", 1));
		model.addAttribute("usersGroup", userGroupRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				userGroupRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>());
		return "usersGroup.html";
	}
	/**
	 * Endpoint for edit the user group
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param group Information for the group
	 * @param usersSelect list of the users of the group
	 *
	 * @return to user group page
	 */
	@PostMapping("group/edit")
	public String editGroup(Model model, Principal principal, @ModelAttribute Groups group, @RequestParam String[] usersSelect) {
		Optional<Groups> groupsDB = userGroupRepository.findById(group.getId());
		List<Users> users = new ArrayList<>();
		for (String user : usersSelect) {
			users.add(new Users(user));
		}
		if (groupsDB.isPresent()) {
			groupsDB.get().setUsers(users);
			groupsDB.get().setDescription(group.getDescription());
			groupsDB.get().setName(group.getName());
			userGroupRepository.save(groupsDB.get());
			model.addAttribute("successMsg", "Successfully update user proup: "+groupsDB.get().getName());
		} else {
			model.addAttribute("errorMsg", "Unable to find user group.");
		}
		
		model.addAttribute("users", userRepository.findAllByAuthoritiesAuthorityAndEnabled("ROLE_USER", 1));
		model.addAttribute("usersGroup", userGroupRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				userGroupRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>());
		return "usersGroup.html";
	}
	/**
	 * Endpoint for delete the user group
	 *
	 * @param model Interface to set up information for the page
	 * @param principal Information for the login user
	 * @param id the id of the user group
	 *
	 * @return to user group page
	 */
	@PostMapping("group/delete/{id}")
	public String deleteGroup(@PathVariable("id") int id, Model model,  Principal principal) {
		userGroupRepository.deleteById(id);
		model.addAttribute("usersGroup", userGroupRepository.findAllByUserUsername(principal.getName()).isPresent() ?
				userGroupRepository.findAllByUserUsername(principal.getName()).get() : new ArrayList<>());
		model.addAttribute("users", userRepository.findAllByAuthoritiesAuthorityAndEnabled("ROLE_USER", 1));
		return "usersGroup.html";
	}
}