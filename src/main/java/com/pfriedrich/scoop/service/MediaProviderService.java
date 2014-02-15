package com.pfriedrich.scoop.service;

import java.util.List;

import com.pfriedrich.scoop.domain.MediaProvider;


public interface MediaProviderService {
	MediaProvider findById(Long id);
	MediaProvider persist(MediaProvider mediaProvider);
	MediaProvider findByMediaProviderId(String mediaProviderId);
	
	
	List<MediaProvider> findById(List<Long> ids);
	List<MediaProvider> findAll();
}
