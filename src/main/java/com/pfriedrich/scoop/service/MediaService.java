package com.pfriedrich.scoop.service;

import java.util.List;

import com.pfriedrich.scoop.domain.Category;
import com.pfriedrich.scoop.domain.Media;
import com.pfriedrich.scoop.domain.MediaProvider;

public interface MediaService {
	Media persist(Media media);
	Media findById(Long id);
	Media findByUrl(String url);
	void consume(Media media);
	Boolean exists(String URL);
	
	List<Media> findAll();
	List<Media> findAll(List<Long> ids);
	List<Media> query(String q, Integer quantity, Media first);
	List<Media> findAfterThis(List<Category> categories, List<MediaProvider> mediaProviders, Media last, Integer quantity);
	List<Media> findBeforeThis(List<Category> categories, List<MediaProvider> mediaProviders, Media first, Integer quantity);
	List<Media> findRecent(List<Category> categories, List<MediaProvider> mediaProviders, Integer quantity);
}