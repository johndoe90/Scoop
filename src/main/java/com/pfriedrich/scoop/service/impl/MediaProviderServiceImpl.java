package com.pfriedrich.scoop.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.pfriedrich.scoop.domain.MediaProvider;
import com.pfriedrich.scoop.repository.MediaProviderRepository;
import com.pfriedrich.scoop.service.MediaProviderService;

@Service
public class MediaProviderServiceImpl implements MediaProviderService{

	private final MediaProviderRepository mediaProviderRepository;
	
	@Inject
	public MediaProviderServiceImpl(MediaProviderRepository mediaProviderRepository){
		this.mediaProviderRepository = mediaProviderRepository;
	}

	@Override
	public MediaProvider persist(MediaProvider mediaProvider) {
		Assert.notNull(mediaProvider);
		
		return mediaProviderRepository.save(mediaProvider);
	}

	@Override
	public MediaProvider findByMediaProviderId(String mediaProviderId) {
		Assert.hasText(mediaProviderId);
		
		return mediaProviderRepository.findByMediaProviderId(mediaProviderId);
	}

	@Override
	public MediaProvider findById(Long id) {
		Assert.notNull(id);
		
		return mediaProviderRepository.findById(id);
	}

	@Override
	public List<MediaProvider> findById(List<Long> ids) {
		Assert.notEmpty(ids);
		
		return mediaProviderRepository.findByIdIn(ids);
	}

	@Override
	public List<MediaProvider> findAll() {
		return mediaProviderRepository.findAll();
	}
}
