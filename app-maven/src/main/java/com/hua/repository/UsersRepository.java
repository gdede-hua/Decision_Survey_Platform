package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Users;
/**
 * Interface for the users communication with the database
 */
public interface UsersRepository extends JpaRepository<Users, String> {

	Optional<Users> findByUsername(String username);
	Optional<Users> findByEmailAndEnabled(String email, int enabled);
	List<Users> findAllByAuthoritiesAuthorityAndEnabled(String role, int enabled);

}
