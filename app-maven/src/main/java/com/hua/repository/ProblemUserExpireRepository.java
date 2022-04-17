package com.hua.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.ProblemUserExpire;

public interface ProblemUserExpireRepository extends JpaRepository<ProblemUserExpire, Integer> {
	
	List<ProblemUserExpire> findAllByExpireDateLessThanAndProblemStatus(Date publicationDate, int status);
}
