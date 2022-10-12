package com.hua.gr;

import com.hua.model.*;
import com.hua.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProblemsTests {

	@Autowired
	private ProblemsRepository problemsRepository;
	@Autowired
	private ProblemUserExpireRepository problemUserExpireRepository;
	@Autowired
	private ProblemsUserRepository problemsUserRepository;
	@Autowired
	private UsersGroupRepository usersGroupRepository;
	@Autowired
	private UsersRepository usersRepository;

	private static Groups groups;
	private static Users user;
	public static Problem problem;
	public static ProblemUser problemUser;
	public static ProblemUserExpire problemUserExpire;

	@BeforeAll
	public static void setUp() {
		UserGroupTests.setUp();
		user = UserGroupTests.user;
		groups = UserGroupTests.groups;
		List<Alternatives> alternativesList = new ArrayList<>(Arrays.asList(new Alternatives("Accord"), new Alternatives("Pilot")));
		problem = new Problem("Car", "Car", user, null, alternativesList);
	}
	@Test
	@Order(1)
	void addUser() {
		user = usersRepository.save(user);
		assertThat(user).isNotNull();
		groups = usersGroupRepository.save(groups);
		assertThat(groups).isNotNull();
	}
	@Test
	@Order(1)
	void makeResearch() {
		problem.setStatus(1);
		problem.setUser(user);
		problem = problemsRepository.save(problem);
		assertThat(problem.getName()).isEqualTo("Car");
	}
	@Test
	@Order(2)
	void releaseSurvey() {
		problemUser = new ProblemUser(user, 1, problem);
		problemUser = problemsUserRepository.save(problemUser);
		problem.setStatus(2);
		problem = problemsRepository.save(problem);
	}
	@Test
	@Order(3)
	void removeSurvey() {
		Optional<ProblemUser> problemUserTest = problemsUserRepository.findById(problemUser.getId());
		assertThat(problemUserTest).isNotEmpty();
		problemsUserRepository.deleteById(problemUser.getId());
		problemUserTest = problemsUserRepository.findById(problemUser.getId());
		assertThat(problemUserTest).isEmpty();
	}
	@Test
	@Order(4)
	void removeResearch() {
		Optional<Problem> problemTest = problemsRepository.findById(problem.getId());
		assertThat(problemTest).isNotEmpty();
		problemsRepository.deleteById(problem.getId());
		problemTest = problemsRepository.findById(problem.getId());
		assertThat(problemTest).isEmpty();
	}
	@Test
	@Order(5)
	void removeUser() {
		usersGroupRepository.deleteById(groups.getId());
		usersRepository.deleteById(user.getId());
	}
}
