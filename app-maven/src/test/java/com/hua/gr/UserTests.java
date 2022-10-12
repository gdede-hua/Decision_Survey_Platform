package com.hua.gr;

import com.hua.model.Authorities;
import com.hua.model.Menu;
import com.hua.model.Users;
import com.hua.repository.UsersRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTests {

	@Autowired
	private UsersRepository usersRepository;

	public static Users user;

	@BeforeAll
	public static void setUp() {
		String uuid = UUID.randomUUID().toString();
		String username = "testUser";
		String password = "testPassword";
		user = new Users(uuid, username, password, "Test User", "test@test.gr", 1, new Authorities(uuid, username, "ROLE_ADMIN"));
	}

	@Test
	@Order(1)
	void add() {
		user = usersRepository.save(user);
		assertThat(user.getRealName()).isEqualTo("Test User");
	}
	@Test
	@Order(2)
	void edit() {
		String newRealName = "new Test User";
		user.setRealName(newRealName);
		user = usersRepository.save(user);
		assertThat(user.getRealName()).isEqualTo(newRealName);
	}
	@Test
	@Order(3)
	void list1() {
		Optional<Users> usersTest = usersRepository.findByUsername(user.getUsername());
		assertThat(usersTest).isNotEmpty();
	}
	@Test
	@Order(4)
	void list2() {
		Optional<Users> usersTest = usersRepository.findByEmailAndEnabled(user.getEmail(), user.getEnabled());
		assertThat(usersTest).isNotEmpty();
	}
	@Test
	@Order(5)
	void remove() {
		Optional<Users> usersTest = usersRepository.findById(user.getId());
		assertThat(usersTest).isNotEmpty();
		usersRepository.deleteById(user.getId());
		usersTest = usersRepository.findById(user.getId());
		assertThat(usersTest).isEmpty();
	}
}
