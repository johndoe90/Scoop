package com.pfriedrich.scoop.mediacollector;

import java.util.Arrays;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.pfriedrich.scoop.domain.Media;
import com.pfriedrich.scoop.mediamapper.DiePresseMediaMapper;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.miscellaneous.ImageUtils;
import com.pfriedrich.scoop.service.MediaService;

public class DiePresseArticleCollectionTask extends AbstractCrawlingArticleCollector{
	private final MediaService mediaService;
	private final DiePresseMediaMapper mediaMapper;
	private final ImageManager imageManager;
	
	public DiePresseArticleCollectionTask(ArticleCollectionTaskConfiguration config, DiePresseMediaMapper mediaMapper, MediaService mediaService, ImageManager imageManager) {
		super(config);
		this.mediaService = mediaService;
		this.mediaMapper = mediaMapper;
		this.imageManager = imageManager;
	}

	@Override
	protected boolean shouldVisit(String URL) {
		if(getConfig().getFilters().matcher(URL).matches())
			return false;

		for(String seed : getConfig().getSeeds()){
			if(URL.startsWith(seed) && !URL.endsWith("/print.do") && !URL.contains("#"))
				return true;
		}
		
		return false;
	}

	@Override
	protected void visit(Document document) {
		System.out.println("Visiting: " + document.baseUri());
		Media media = mediaMapper.map(document);
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
		}
	}
}
