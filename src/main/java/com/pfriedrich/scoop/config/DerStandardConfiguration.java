package com.pfriedrich.scoop.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.services.s3.AmazonS3;
import com.pfriedrich.scoop.domain.MediaProvider;
import com.pfriedrich.scoop.domain.MediaProviders;
import com.pfriedrich.scoop.mediacollector.DerStandardArticleCollectionTask;
import com.pfriedrich.scoop.mediacollector.MediaCollector;
import com.pfriedrich.scoop.mediacollector.MediaCollectorImpl;
import com.pfriedrich.scoop.mediamapper.DerStandardMediaMapper;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.service.MediaService;

@Configuration
public class DerStandardConfiguration {

	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private ImageManager imageManager;
	@Inject private MediaService mediaService;

	private static final MediaProvider mediaProvider = MediaProviders.DER_STANDARD;
	
	@Bean(name = "derStandardMediaCollector")
	public MediaCollector derStandardMediaCollector(){
		MediaCollector mediaCollector = new MediaCollectorImpl();
		
		mediaCollector.addMediaCollectionTask(derStandardArticleCollectionTask());
		 
		return mediaCollector;
	}
	
	public Properties properties(){
		Properties properties = new Properties();
		properties.setProperty("pause", "2500");
		properties.setProperty("timeout", "10000");
		properties.setProperty("source", env.getRequiredProperty("mediaProviders.derStandard.source"));	
		properties.setProperty("userAgent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		properties.setProperty("historyLocation", env.getRequiredProperty("mediaProviders.historyLocation") + File.separator + mediaProvider.getMediaProviderId());
		
		return properties;
	}

	public DerStandardArticleCollectionTask derStandardArticleCollectionTask(){
		return new DerStandardArticleCollectionTask(
			properties(), 
			mediaMapper(), 
			mediaService, 
			imageManager);
	}
	
	public DerStandardMediaMapper mediaMapper(){
		DerStandardMediaMapper mediaMapper = new DerStandardMediaMapper(mediaProvider);
		return mediaMapper;
	}
}
