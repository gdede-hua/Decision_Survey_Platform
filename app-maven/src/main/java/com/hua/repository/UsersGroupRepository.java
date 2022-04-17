package com.hua.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Groups;

public interface UsersGroupRepository extends JpaRepository<Groups, Integer> {
	public Optional<List<Groups>> findAllByUserUsername(String username);
}
