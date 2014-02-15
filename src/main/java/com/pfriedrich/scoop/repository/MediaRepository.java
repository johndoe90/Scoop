package com.pfriedrich.scoop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfriedrich.scoop.domain.Media;

public interface MediaRepository extends JpaRepository<Media, Long>{
	Media findByUrl(String url);
}
