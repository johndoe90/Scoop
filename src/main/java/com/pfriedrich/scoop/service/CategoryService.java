package com.pfriedrich.scoop.service;

import java.util.List;

import com.pfriedrich.scoop.domain.Category;


public interface CategoryService {
	Category persist(Category category);
	
	List<Category> findAll();
	List<Category> findAllCategories();
	
	Category findById(Long id);
	Category findByQualifiedName(String qualifiedName);
	List<Category> findById(List<Long> ids);
}
