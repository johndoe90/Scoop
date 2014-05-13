package com.pfriedrich.scoop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pfriedrich.scoop.domain.Category;
import com.pfriedrich.scoop.domain.Media;
import com.pfriedrich.scoop.domain.MediaProvider;
import com.pfriedrich.scoop.repository.CategoryRepository;
import com.pfriedrich.scoop.repository.MediaProviderRepository;
import com.pfriedrich.scoop.repository.MediaRepository;
import com.pfriedrich.scoop.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{
	private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	private final MediaRepository mediaRepository;
	private final CategoryRepository categoryRepository;
	private final MediaProviderRepository mediaProviderRespository;
	
	@Inject
	public MediaServiceImpl(MediaRepository mediaRepository, MediaProviderRepository mediaProviderRespository, CategoryRepository categoryRepository){
		this.mediaRepository = mediaRepository;
		this.mediaProviderRespository = mediaProviderRespository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Media persist(Media media) {
		Assert.notNull(media);
		
		MediaProvider mediaProvider = mediaProviderRespository.findByMediaProviderId(media.getMediaProvider().getMediaProviderId());		
		media.setMediaProvider(mediaProvider);
		
		Category category = categoryRepository.findByQualifiedName(media.getCategory().getQualifiedName());
		media.setCategory(category);
		
		logger.info("persisting medium at '{}' to database", media.getUrl());
		
		return mediaRepository.save(media);
	}
	
	@Override
	public Media findById(Long id) {
		Assert.notNull(id);
		
		return mediaRepository.findOne(id);
	}

	@Override
	public Boolean exists(String URL) {
		Assert.hasText(URL);
		
		return findByUrl(URL) != null ? true : false;
	}

	@Override
	public Media findByUrl(String URL) {
		Assert.hasText(URL);
		
		return mediaRepository.findByUrl(URL);
	}
	
	@Override
	public List<Media> findAll() {
		List<Media> all = new ArrayList<Media>();
		for(Media media : mediaRepository.findAll()){
			all.add(media);
		}
		
		return all;
	}
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findRecent(List<Category> categories, List<MediaProvider> mediaProviders, Integer quantity) {
		if(categories == null || categories.isEmpty() || mediaProviders == null || mediaProviders.isEmpty()) { return new ArrayList<Media>(); }		
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(quantity)
								.add(Restrictions.in("category", categories))
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.addOrder(Order.desc("date"))
								.addOrder(Order.desc("id"))
								.list();
		
		return media;
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findAfterThis(List<Category> categories, List<MediaProvider> mediaProviders, Media last, Integer quantity) {
		if(categories == null || categories.isEmpty() || mediaProviders == null || mediaProviders.isEmpty()) { return new ArrayList<Media>(); }		
		Assert.noNullElements(new Object[]{last, quantity});
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(quantity)
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.add(Restrictions.in("category", categories))
								.add(Restrictions.or(
										Restrictions.and(
												Restrictions.eq("date", last.getDate()),
												Restrictions.gt("id", last.getId())
										),
										Restrictions.gt("date", last.getDate())
								))
								.addOrder(Order.asc("date"))
								.addOrder(Order.asc("id"))
								.list();
		
		return media;
	}
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Media> findBeforeThis(List<Category> categories, List<MediaProvider> mediaProviders, Media first, Integer quantity) {
		if(categories == null || categories.isEmpty() || mediaProviders == null || mediaProviders.isEmpty()) { return new ArrayList<Media>(); }		
		Assert.noNullElements(new Object[]{first, quantity});
		
		Session session = em.unwrap(Session.class);
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(quantity)
								.add(Restrictions.in("mediaProvider", mediaProviders))
								.add(Restrictions.in("category", categories))
								.add(Restrictions.or(
										Restrictions.and(
												Restrictions.eq("date", first.getDate()),
												Restrictions.lt("id", first.getId())	//wa gt
										),
										Restrictions.lt("date", first.getDate())
								))
								.addOrder(Order.desc("date"))
								.addOrder(Order.desc("id"))
								.list();
		
		return media;
	}

	@Override
	@Transactional
	public void consume(Media media) {
		media.setConsumed(media.getConsumed() + 1);
		mediaRepository.save(media);
	}

	@Override
	public List<Media> findAll(List<Long> ids){
		Assert.notEmpty(ids);
		
		return mediaRepository.findAll(ids);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Media> query(String q, Integer quantity, Media first) {		
		Session session = em.unwrap(Session.class);
		Query query = session.getNamedQuery("queryMedia");
		query.setString("lang", "german");
		query.setString("q", q.trim().replace(" ", " & "));
		query.setParameter("quantity", quantity);
		query.setParameter("date", first != null ? first.getDate() : new Date().getTime());
		
		return query.list();
	}
}
