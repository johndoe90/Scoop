package com.pfriedrich.scoop.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.amazonaws.services.s3.AmazonS3;
import com.pfriedrich.scoop.mediacollector.MediaCollector;
import com.pfriedrich.scoop.mediacollector.MediaCollectors;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.service.MediaService;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = "com.pfriedrich.scoop")
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig extends WebMvcConfigurerAdapter{
	
	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private MediaService mediaService;
	@Inject private MediaCollector kurierMediaCollector;
	@Inject private MediaCollector diePresseMediaCollector;
	@Inject private MediaCollector derStandardMediaCollector;
	@Inject private MediaCollector kronenZeitungMediaCollector;
	@Inject private MediaCollector ooeNachrichtenMediaCollector;
	@Inject private MediaCollector tirTageszeitungMediaCollector;
	
	@Bean
	public ImageManager imageManager(){
		Properties properties = new Properties();
		properties.setProperty("bucketName", env.getRequiredProperty("amazonS3.images.bucketName"));
		properties.setProperty("defaultImageSmall", env.getRequiredProperty("amazonS3.images.defaultImageSmall"));
		properties.setProperty("defaultImageMedium", env.getRequiredProperty("amazonS3.images.defaultImageMedium"));
		properties.setProperty("defaultImageLarge", env.getRequiredProperty("amazonS3.images.defaultImageLarge"));
		properties.setProperty("defaultImageWidth", env.getRequiredProperty("amazonS3.images.defaultImage.width"));
		properties.setProperty("defaultImageHeight", env.getRequiredProperty("amazonS3.images.defaultImage.height"));
		properties.setProperty("temporaryStorageLocation", env.getRequiredProperty("amazonS3.images.temporaryLocalStorageLocation"));
		properties.setProperty("widthSmall", env.getRequiredProperty("amazonS3.images.widthSmall"));
		properties.setProperty("heightSmall", env.getRequiredProperty("amazonS3.images.heightSmall"));
		properties.setProperty("widthMedium", env.getRequiredProperty("amazonS3.images.widthMedium"));
		properties.setProperty("heightMedium", env.getRequiredProperty("amazonS3.images.heightMedium"));
		
		return new ImageManager(amazonS3Client, properties);
	}
	
	@Bean
	public MediaCollectors mediaCollectors(){
		MediaCollectors mediaCollectors = new MediaCollectors();
		mediaCollectors.addMediaCollector(kurierMediaCollector);
		mediaCollectors.addMediaCollector(derStandardMediaCollector);
		mediaCollectors.addMediaCollector(diePresseMediaCollector);
		mediaCollectors.addMediaCollector(kronenZeitungMediaCollector);
		mediaCollectors.addMediaCollector(ooeNachrichtenMediaCollector);
		mediaCollectors.addMediaCollector(tirTageszeitungMediaCollector);

		return mediaCollectors;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	/*@Bean
	public InternalResourceViewResolver configureInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}*/
}

