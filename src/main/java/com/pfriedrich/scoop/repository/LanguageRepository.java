package com.pfriedrich.scoop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfriedrich.scoop.domain.Language;

public interface LanguageRepository extends JpaRepository<Language, Long>{
	Language findByCode(String code);
}
