package com.pfriedrich.scoop.config;

import java.io.File;
import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.services.s3.AmazonS3;
import com.pfriedrich.scoop.domain.MediaProvider;
import com.pfriedrich.scoop.domain.MediaProviders;
import com.pfriedrich.scoop.mediacollector.ArticleCollectionTaskConfiguration;
import com.pfriedrich.scoop.mediacollector.DiePresseArticleCollectionTask;
import com.pfriedrich.scoop.mediacollector.MediaCollector;
import com.pfriedrich.scoop.mediacollector.MediaCollectorImpl;
import com.pfriedrich.scoop.mediamapper.DiePresseMediaMapper;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.service.MediaService;

@Configuration
public class DiePresseConfiguration {

	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private ImageManager imageManager;
	@Inject private MediaService mediaService;

	private static final MediaProvider mediaProvider = MediaProviders.DIE_PRESSE;
	
	@Bean(name = "diePresseMediaCollector")
	public MediaCollector diePresseMediaCollector(){
		MediaCollector mediaCollector = new MediaCollectorImpl();
		mediaCollector.addMediaCollectionTask(diePresseArticleCollectionTask());
		 
		return mediaCollector;
	}
	
	public ArticleCollectionTaskConfiguration articleCollectionTaskConfiguration(){
		return new ArticleCollectionTaskConfiguration(
			env.getRequiredProperty("mediaProviders.historyLocation") + File.separator + mediaProvider.getMediaProviderId(),
			Arrays.asList(env.getRequiredProperty("mediaProviders." + mediaProvider.getMediaProviderId() + ".seeds").split(" "))
		);
	}

	public DiePresseArticleCollectionTask diePresseArticleCollectionTask(){
		return new DiePresseArticleCollectionTask(
			articleCollectionTaskConfiguration(), 
			diePresseMediaMapper(), 
			mediaService,
			imageManager);
	}
	
	public DiePresseMediaMapper diePresseMediaMapper(){
		return new DiePresseMediaMapper(mediaProvider);
	}
}
