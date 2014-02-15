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
import com.pfriedrich.scoop.mediacollector.MediaCollectionTask;
import com.pfriedrich.scoop.mediacollector.MediaCollector;
import com.pfriedrich.scoop.mediacollector.MediaCollectorImpl;
import com.pfriedrich.scoop.mediacollector.TirTageszeitungArticleCollectionTask;
import com.pfriedrich.scoop.mediamapper.MediaMapper;
import com.pfriedrich.scoop.mediamapper.TirTageszeitungMediaMapper;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.service.MediaService;

@Configuration
public class TirTageszeitungConfig {
	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private ImageManager imageManager;
	@Inject private MediaService mediaService;

	private static final MediaProvider mediaProvider = MediaProviders.TIR_TAGESZEITUNG;
	
	@Bean(name = "tirTageszeitungMediaCollector")
	public MediaCollector tirTageszeitungMediaCollector(){
		MediaCollector mediaCollector = new MediaCollectorImpl();
		mediaCollector.addMediaCollectionTask(articleCollectionTask());
		 
		return mediaCollector;
	}
	
	public ArticleCollectionTaskConfiguration articleCollectionTaskConfiguration(){
		return new ArticleCollectionTaskConfiguration(
			env.getRequiredProperty("mediaProviders.historyLocation") + File.separator + mediaProvider.getMediaProviderId(),
			Arrays.asList(env.getRequiredProperty("mediaProviders." + mediaProvider.getMediaProviderId() + ".seeds").split(" "))
		);
	}

	public MediaCollectionTask articleCollectionTask(){
		return new TirTageszeitungArticleCollectionTask(
			articleCollectionTaskConfiguration(), 
			mediaMapper(), 
			mediaService,
			imageManager);
	}
	
	public MediaMapper mediaMapper(){
		return new TirTageszeitungMediaMapper(mediaProvider);
	}
}
