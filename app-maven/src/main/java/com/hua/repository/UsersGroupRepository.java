package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Groups;
/**
 * Interface for the user group communication with the database
 */
public interface UsersGroupRepository extends JpaRepository<Groups, Integer> {
	Optional<List<Groups>> findAllByUserUsername(String username);
}
