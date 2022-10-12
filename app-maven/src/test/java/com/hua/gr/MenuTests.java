package com.hua.gr;

import com.hua.model.Menu;
import com.hua.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MenuTests {

	@Autowired
	private MenuRepository menuRepository;

	private static Menu menu;

	@BeforeAll
	public static void setUp() {
		menu = new Menu("Test", "test", 100, 0, "ROLE_USER");
	}

	@Test
	@Order(1)
	void add() {
		menu = menuRepository.save(menu);
		assertThat(menu.getName()).isEqualTo("Test");
	}
	@Test
	@Order(2)
	void edit() {
		String newName = "testEdit";
		menu.setName(newName);
		menu = menuRepository.save(menu);
		assertThat(menu.getName()).isEqualTo(newName);
	}
	@Test
	@Order(3)
	void remove() {
		Optional<Menu> menuTest = menuRepository.findById(menu.getId());
		assertThat(menuTest).isNotEmpty();
		menuRepository.deleteById(menu.getId());
		menuTest = menuRepository.findById(menu.getId());
		assertThat(menuTest).isEmpty();
	}
}
