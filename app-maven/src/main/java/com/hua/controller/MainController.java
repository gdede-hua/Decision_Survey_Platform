package com.hua.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.gson.Gson;
import com.hua.repository.MenuRepository;

/**
 * Class for the management of UI main page.
 * @author      John Nikolaou
 */
@Controller
public class MainController {

	private final MenuRepository menuRepository;

	public MainController(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	/**
	 * Endpoint for the load of the main page
	 *
	 * @param session it used for page redirection
	 *
	 * @return to main page after login
	 */
	@GetMapping("/main")
	public String getMain(HttpSession session) {
		String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		String[] rolesArray = new Gson().fromJson(roles, String[].class);
		session.setAttribute("menu", menuRepository.findAllByParentIdAndRoleOrderByOrderIdAsc(0, rolesArray[0]).get());
		return "main.html";
	}
}
