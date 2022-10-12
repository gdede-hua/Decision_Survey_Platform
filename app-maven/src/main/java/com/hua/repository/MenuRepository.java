package com.hua.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hua.model.Menu;
/**
 * Interface for the menu communication with the database
 */
public interface MenuRepository extends JpaRepository<Menu, Integer>{
	Optional<List<Menu>> findAllByParentIdAndRoleOrderByOrderIdAsc(int parentId, String role);
	
}
