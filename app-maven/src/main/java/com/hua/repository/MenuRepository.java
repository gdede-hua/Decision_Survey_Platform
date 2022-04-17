package com.hua.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{
	public Optional<List<Menu>> findAllByOrderByOrderIdAsc();
	public Optional<List<Menu>> findAllByParentIdOrderByOrderIdAsc(int parentId);
	public Optional<List<Menu>> findAllByParentIdAndRoleOrderByOrderIdAsc(int parentId, String role);
	
}
