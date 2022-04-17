package com.hua.controller;


import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.gson.Gson;
import com.hua.repository.MenuRepository;

@Controller
public class MainController {

	@Autowired
	private MenuRepository menuRepository;
	
	@GetMapping("/main")
	public String getMain(Model model, HttpSession session, Principal principal) {
		String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		String[] rolesArray = new Gson().fromJson(roles, String[].class);
		session.setAttribute("menu", menuRepository.findAllByParentIdAndRoleOrderByOrderIdAsc(0, rolesArray[0]).get());
		return "main.html";
	}
//	@GetMapping("/menuFile")
//	public String getMenu(Model model, HttpSession session) {
//		session.setAttribute("menu", menuRepository.findAll());
//		return "menu.html";
//	}
}
