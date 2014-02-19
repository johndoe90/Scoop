package com.pfriedrich.scoop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfriedrich.scoop.domain.Category;
import com.pfriedrich.scoop.domain.MediaProvider;

public interface MediaProviderRepository extends JpaRepository<MediaProvider, Long>{
	MediaProvider findById(Long id);
	MediaProvider findByMediaProviderId(String mediaProviderId);
	
	List<MediaProvider> findByIdIn(List<Long> ids);
}
