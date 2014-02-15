package com.pfriedrich.scoop.mediamapper;

import org.jsoup.nodes.Document;

import com.pfriedrich.scoop.domain.Category;
import com.pfriedrich.scoop.domain.Media;

public interface MediaMapper {

	String getUrl(Document document);
	String getType(Document document);
	String getTitle(Document document);
	String getDescription(Document document);
	String getImage(Document document);
	String getKeywords(Document document);
	Long getDate(Document document);
	Category getCategory(Document document);
	String getAudio(Document document);
	String getVideo(Document document);
	
	Media map(Document document);
}
