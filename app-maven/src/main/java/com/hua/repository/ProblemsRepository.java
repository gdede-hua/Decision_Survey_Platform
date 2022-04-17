package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Problem;

public interface ProblemsRepository extends JpaRepository<Problem, Integer> {
	public Optional<List<Problem>> findAllByUserUsername(String username);
}
