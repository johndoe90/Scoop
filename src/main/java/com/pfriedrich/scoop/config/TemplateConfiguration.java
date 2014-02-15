package com.pfriedrich.scoop.config;


/*public class TemplateConfiguration {
	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private ImageManager imageManager;
	@Inject private MediaService mediaService;

	private static final MediaProvider mediaProvider = MediaProviders.;
	
	@Bean(name = "MediaCollector)
	public MediaCollector ,MediaCollector(){
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
		return new ,ArticleCollectionTask(
			articleCollectionTaskConfiguration(), 
			mediaMapper(), 
			mediaService,
			imageManager);
	}
	
	public MediaMapper mediaMapper(){
		return new ,MediaMapper(mediaProvider);
	}
}*/
