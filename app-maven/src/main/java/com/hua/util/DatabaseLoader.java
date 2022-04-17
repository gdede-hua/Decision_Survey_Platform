package com.hua.util;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hua.model.Authorities;
import com.hua.model.Menu;
import com.hua.model.Users;
import com.hua.repository.MenuRepository;
import com.hua.repository.UsersRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {
	

	private final UsersRepository userRepository;
	private final MenuRepository menuRepository;

	@Autowired
	public DatabaseLoader(MenuRepository menuRepository, UsersRepository userRepository) {
		this.menuRepository = menuRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public void run(String... strings) throws Exception {
		
		if ( userRepository.findAll().size() == 0) {
			String uuid = UUID.randomUUID().toString();
			userRepository.save(new Users(uuid, "admin", "password", "Admin User", "admin@hua.gr", 1, new Authorities(uuid, "admin", "ROLE_ADMIN")));
		}
		
		if (menuRepository.findAll().size() == 0) {
			menuRepository.save(new Menu(1, "Home", "main", 1, 0, "ROLE_ADMIN"));
			menuRepository.save(new Menu(2, "Research Management", "problems", 2, 0, "ROLE_ADMIN"));
			menuRepository.save(new Menu(3, "Researches", "problems/manage", 1, 2, "ROLE_ADMIN"));
			menuRepository.save(new Menu(4, "Research Wizard", "problems/wizard", 2, 2, "ROLE_ADMIN"));
			menuRepository.save(new Menu(5, "User Management", "users", 3, 0, "ROLE_ADMIN"));
			menuRepository.save(new Menu(6, "Users", "users", 1, 5, "ROLE_ADMIN"));
			menuRepository.save(new Menu(7, "Group of User", "users/group", 2, 5, "ROLE_ADMIN"));
			menuRepository.save(new Menu(8, "Home", "main", 1, 0, "ROLE_USER"));
			menuRepository.save(new Menu(9, "Survey", "problemsUser", 2, 0, "ROLE_USER"));
		}
	}
	
}