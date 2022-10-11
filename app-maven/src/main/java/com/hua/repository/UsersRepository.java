package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Users;
/**
 * Interface for the users communication with the database
 */
public interface UsersRepository extends JpaRepository<Users, String> {

	public Optional<Users> findByUsername(String username);
	public Optional<Users> findByEmailAndEnabled(String email, int enabled);
	public List<Users> findAllByAuthoritiesAuthorityAndEnabled(String role, int enabled);

}
