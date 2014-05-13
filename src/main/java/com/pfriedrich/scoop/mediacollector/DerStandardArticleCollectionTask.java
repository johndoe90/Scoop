package com.pfriedrich.scoop.mediacollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pfriedrich.scoop.domain.Media;
import com.pfriedrich.scoop.mediamapper.DerStandardMediaMapper;
import com.pfriedrich.scoop.miscellaneous.ImageManager;
import com.pfriedrich.scoop.miscellaneous.ImageUtils;
import com.pfriedrich.scoop.service.MediaService;

public class DerStandardArticleCollectionTask extends AbstractFromListMediaCollector{

	private final ImageManager imageManager;
	private final MediaService mediaService;
	private final DerStandardMediaMapper mediaMapper;
	
	public DerStandardArticleCollectionTask(Properties properties, DerStandardMediaMapper mediaMapper, MediaService mediaService, ImageManager imageManager) {
		super(properties);
		this.mediaMapper = mediaMapper;
		this.mediaService = mediaService;
		this.imageManager = imageManager;
	}

	@Override
	protected List<String> getToDo() {
		List<String> todo = new ArrayList<String>();
		
		try {
			Document document = Jsoup
								  .connect(properties.getProperty("source"))
								  .timeout(Integer.parseInt(properties.getProperty("timeout")))
								  .userAgent(properties.getProperty("userAgent"))
								  .get();
			
			Elements elements = document.select("url > loc");
			for(Element element : elements){
				todo.add(element.text());
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
		return todo;
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
		
		/*System.out.println("Visiting: " + document.baseUri());
		Media media = mediaMapper.map(document);
		if(media != null && !mediaService.exists(media.getUrl())){
			if(media.getImageSmall() != null){
				Map<String, String> links = ImageUtils.buildImageTree(media.getImageSmall());
				media.setImageSmall(links.get("medium"));
				media.setImageMedium(links.get("medium"));
				media.setImageLarge(links.get("medium"));
				media.setImageWidth(Integer.parseInt(links.get("width")));
				media.setImageHeight(Integer.parseInt(links.get("height")));
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
