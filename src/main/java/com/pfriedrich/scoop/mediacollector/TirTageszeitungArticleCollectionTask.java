package com.pfriedrich.scoop.mediacollector;

import java.util.Map;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pfriedrich.scoop.domain.Media;
import com.pfriedrich.scoop.mediamapper.MediaMapper;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.miscellaneous.ImageUtils;
import com.pfriedrich.scoop.service.MediaService;


public class TirTageszeitungArticleCollectionTask extends AbstractCrawlingArticleCollector{	
	
	private static final Logger logger = LoggerFactory.getLogger(TirTageszeitungArticleCollectionTask.class);
	
	private final ImageManager imageManager;
	private final MediaService mediaService;
	private final MediaMapper mediaMapper;
	
	public TirTageszeitungArticleCollectionTask(ArticleCollectionTaskConfiguration config, MediaMapper mediaMapper, MediaService mediaService, ImageManager imageManager){
		super(config);
		this.mediaService = mediaService;
		this.mediaMapper = mediaMapper;
		this.imageManager = imageManager;
	}
	
	@Override
	protected boolean shouldVisit(String URL) {
		if(getConfig().getFilters().matcher(URL).matches()) { return false; }
		for(String domain : getConfig().getSeeds()){
			if(URL.startsWith(domain) && !URL.contains("#")) { 
				return true; 
			}
		}
		
		return false;
	}
	
	@Override
	protected void visit(Document document) {
		Media media = mediaMapper.map(document);
		if(media != null && !mediaService.exists(media.getUrl())){
			Map<String, String> links = imageManager.processImage(media.getImageSmall());
			media.setImageSmall(links.get("small") != null ? links.get("small") : media.getImageSmall());
			media.setImageMedium(links.get("medium"));
			media.setImageLarge(links.get("large"));
			media.setImageWidth(links.get("width") != null ? Integer.parseInt(links.get("width")) : null);
			media.setImageHeight(links.get("height") != null ? Integer.parseInt(links.get("height")) : null);						
			
			mediaService.persist(media);
		}
		
		/*Media media = mediaMapper.map(document);
		if(media != null && !mediaService.exists(media.getUrl())){
			if(media.getImageSmall() != null){
				Map<String, String> links = ImageUtils.buildImageTree(media.getImageSmall());
				media.setImageSmall(links.get("medium"));
				media.setImageMedium(links.get("medium"));
				media.setImageLarge(links.get("medium"));
				media.setImageWidth(links.get("width") != null ? Integer.parseInt(links.get("width")) : null);
				media.setImageHeight(links.get("height") != null ? Integer.parseInt(links.get("height")) : null);
			}else{
				media.setImageSmall("http://static.pagenstecher.de/uploads/f/f9/f9e/f9e6/photo-not-available.jpg");
				media.setImageMedium("http://static.pagenstecher.de/uploads/f/f9/f9e/f9e6/photo-not-available.jpg");
				media.setImageLarge("http://static.pagenstecher.de/uploads/f/f9/f9e/f9e6/photo-not-available.jpg");
				media.setImageWidth(500);
				media.setImageHeight(493);
			}
			
			
			mediaService.persist(media);
		}*/
	}
}