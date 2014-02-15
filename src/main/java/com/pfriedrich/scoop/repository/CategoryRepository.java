package com.pfriedrich.scoop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfriedrich.scoop.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	@Query("SELECT DISTINCT category FROM Category as category LEFT JOIN FETCH category.translations ORDER BY category.sort ASC")
	List<Category> findAllCategories();
	List<Category> findByIdIn(List<Long> ids);
	
	Category findById(Long id);
	Category findByQualifiedName(String qualifiedName);
}
