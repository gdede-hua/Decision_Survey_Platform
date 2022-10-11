package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.ProblemUserAhp;
/**
 * Interface for the survey answers communication with the database
 */
public interface ProblemsUserAhpRepository extends JpaRepository<ProblemUserAhp, Integer> {
	public Optional<List<ProblemUserAhp>> findAllByProblemUserProblemId(int problemId);
}
