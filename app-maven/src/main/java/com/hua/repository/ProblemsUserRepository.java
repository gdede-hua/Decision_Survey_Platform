package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.ProblemUser;

public interface ProblemsUserRepository extends JpaRepository<ProblemUser, Integer> {
	public Optional<List<ProblemUser>> findAllByProblemId(int problemId);
	public Optional<List<ProblemUser>> findAllByProblemIdAndStatus(int problemId, int Status);
	public Optional<List<ProblemUser>> findAllByUserUsernameAndStatus(String username, int status);
}
