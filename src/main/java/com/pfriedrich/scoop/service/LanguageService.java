package com.pfriedrich.scoop.service;

import com.pfriedrich.scoop.domain.Language;

public interface LanguageService {
	Language findByCode(String code);
	Language persist(Language language);
	Boolean languageIsSupported(String languageCode);
}
