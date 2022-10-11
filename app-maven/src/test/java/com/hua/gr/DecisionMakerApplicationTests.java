package com.hua.gr;

import com.hua.controller.*;
import com.hua.repository.*;
import com.hua.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DecisionMakerApplicationTests {

	@Autowired
	private AHPController ahpController;
	@Autowired
	private MainController mainController;
	@Autowired
	private ProblemsController problemsController;
	@Autowired
	private ProblemsUserController problemsUserController;
	@Autowired
	private UsersController usersController;

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private ProblemsRepository problemsRepository;
	@Autowired
	private ProblemsUserAhpRepository problemsUserAhpRepository;
	@Autowired
	private ProblemsUserRepository problemsUserRepository;
	@Autowired
	private ProblemUserExpireRepository problemUserExpireRepository;
	@Autowired
	private UsersGroupRepository usersGroupRepository;
	@Autowired
	private UsersRepository usersRepository;


	@Autowired
	private DatabaseLoader databaseLoader;
	@Autowired
	private GenerateDataExcel generateDataExcel;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RunAHP runAHP;
	@Autowired
	private ScheduledTasks scheduledTasks;

	@Test
	void controllerLoads() {
		assertThat(ahpController).isNotNull();
		assertThat(mainController).isNotNull();
		assertThat(problemsController).isNotNull();
		assertThat(problemsUserController).isNotNull();
		assertThat(usersController).isNotNull();
	}
	@Test
	void repositoryLoads() {
		assertThat(menuRepository).isNotNull();
		assertThat(problemsRepository).isNotNull();
		assertThat(problemsUserAhpRepository).isNotNull();
		assertThat(problemsUserRepository).isNotNull();
		assertThat(problemUserExpireRepository).isNotNull();
		assertThat(usersGroupRepository).isNotNull();
		assertThat(usersRepository).isNotNull();
	}
	@Test
	void utilServiceLoads() {
		assertThat(databaseLoader).isNotNull();
		assertThat(generateDataExcel).isNotNull();
		assertThat(jwtUtil).isNotNull();
		assertThat(runAHP).isNotNull();
		assertThat(scheduledTasks).isNotNull();
	}
}
