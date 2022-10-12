package com.hua.gr;

import com.hua.controller.*;
import com.hua.repository.*;
import com.hua.util.*;
import org.junit.jupiter.api.Order;
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
	void ahpControllerLoads() {
		assertThat(ahpController).isNotNull();
	}
	@Test
	void mainControllerLoads() {
		assertThat(mainController).isNotNull();
	}
	@Test
	void problemsControllerLoads() {
		assertThat(problemsController).isNotNull();
	}
	@Test
	void problemsUserControllerLoads() {
		assertThat(problemsUserController).isNotNull();
	}
	@Test
	void usersControllerLoads() {
		assertThat(usersController).isNotNull();
	}

	@Test
	void menuRepositoryLoads() {
		assertThat(menuRepository).isNotNull();
	}
	@Test
	void problemsRepositoryLoads() {
		assertThat(problemsRepository).isNotNull();
	}
	@Test
	void problemsUserAhpRepositoryLoads() {
		assertThat(problemsUserAhpRepository).isNotNull();
	}
	@Test
	void problemsUserRepositoryLoads() {
		assertThat(problemsUserRepository).isNotNull();
	}
	@Test
	void problemUserExpireRepositoryLoads() {
		assertThat(problemUserExpireRepository).isNotNull();
	}
	@Test
	void usersGroupRepositoryLoads() {
		assertThat(usersGroupRepository).isNotNull();
	}
	@Test
	void usersRepositoryLoads() {
		assertThat(usersRepository).isNotNull();
	}

	@Test
	void databaseLoaderLoads() {
		assertThat(databaseLoader).isNotNull();
	}
	@Test
	void generateDataExcelLoads() {
		assertThat(generateDataExcel).isNotNull();
	}
	@Test
	void jwtUtilLoads() {
		assertThat(jwtUtil).isNotNull();
	}
	@Test
	void runAHPLoads() {
		assertThat(runAHP).isNotNull();
	}
	@Test
	void scheduledTasksLoads() {
		assertThat(scheduledTasks).isNotNull();
	}
}
