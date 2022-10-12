package com.hua.gr;

import com.hua.model.Groups;
import com.hua.model.Menu;
import com.hua.model.Users;
import com.hua.repository.MenuRepository;
import com.hua.repository.UsersGroupRepository;
import com.hua.repository.UsersRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserGroupTests {

	@Autowired
	private UsersGroupRepository usersGroupRepository;
	@Autowired
	private UsersRepository usersRepository;

	public static Groups groups;
	public static Users user;

	@BeforeAll
	public static void setUp() {
		UserTests.setUp();
		user = UserTests.user;
		groups = new Groups("test", "test", user, new ArrayList<>(Arrays.asList(user)));
	}
	@Test
	@Order(1)
	void addUser() {
		user = usersRepository.save(user);
		assertThat(user).isNotNull();
	}
	@Test
	@Order(2)
	void add() {
		groups = usersGroupRepository.save(groups);
		assertThat(groups.getName()).isEqualTo("test");
	}
	@Test
	@Order(3)
	void edit() {
		String newName = "testEdit";
		groups.setName(newName);
		groups = usersGroupRepository.save(groups);
		assertThat(groups.getName()).isEqualTo(newName);
	}
	@Test
	@Order(4)
	void list() {
		Optional<List<Groups>> groupsTestList = usersGroupRepository.findAllByUserUsername(groups.getUser().getUsername());
		assertThat(groupsTestList).isNotEmpty();
	}
	@Test
	@Order(5)
	void remove() {
		Optional<Groups> groupsTest = usersGroupRepository.findById(groups.getId());
		assertThat(groupsTest).isNotEmpty();
		usersGroupRepository.deleteById(groups.getId());
		groupsTest = usersGroupRepository.findById(groups.getId());
		assertThat(groupsTest).isEmpty();
	}
	@Test
	@Order(6)
	void removeUser() {
		usersRepository.deleteById(user.getId());
	}
	@AfterAll
	public static void cleanUp() {
		groups = null;
		user = null;
	}
}
