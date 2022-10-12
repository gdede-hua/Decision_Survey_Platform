package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Problem;
/**
 * Interface for the research communication with the database
 */
public interface ProblemsRepository extends JpaRepository<Problem, Integer> {
	Optional<List<Problem>> findAllByUserUsername(String username);
}
