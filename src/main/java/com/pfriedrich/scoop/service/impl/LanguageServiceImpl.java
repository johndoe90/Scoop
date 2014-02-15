package com.pfriedrich.scoop.service.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.pfriedrich.scoop.domain.Language;
import com.pfriedrich.scoop.domain.Languages;
import com.pfriedrich.scoop.repository.LanguageRepository;
import com.pfriedrich.scoop.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService{
	
	@PersistenceContext
	private EntityManager em;
	
	private final LanguageRepository languageRepository;
	
	@Inject
	public LanguageServiceImpl(LanguageRepository languageRepository){
		this.languageRepository = languageRepository;
	}

	@Override
	public Language persist(Language language) {
		Assert.notNull(language);
		
		return languageRepository.save(language);
	}

	@Override
	public Language findByCode(String code) {
		Assert.hasText(code);
		
		return languageRepository.findByCode(code);
	}
 
	@Override
	public Boolean languageIsSupported(String languageCode) {
		for(Language language : Languages.getSupportedLanguages()){
			if(languageCode.equals(language.getCode())){
				return true;
			}
		}
		
		return false;
	}
}
