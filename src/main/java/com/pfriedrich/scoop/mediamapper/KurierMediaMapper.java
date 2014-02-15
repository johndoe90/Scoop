package com.pfriedrich.scoop.mediamapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jsoup.nodes.Document;

import com.pfriedrich.scoop.domain.Categories;
import com.pfriedrich.scoop.domain.Category;
import com.pfriedrich.scoop.domain.MediaProvider;


public class KurierMediaMapper extends AbstractMediaMapper{
	
	public KurierMediaMapper(MediaProvider mediaProvider){
		super(mediaProvider);
	}
	
	@Override
	public Long getDate(Document document) {
		String oldDate = getMetaProperty(document, "meta[name=sailthru.date]");
		if(oldDate != null){
			try{
				String[] parts = oldDate.split(" ");
				String newDate = parts[1] + "-" + parts[2] + "-" + parts[3] + " " + parts[4];
				return new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH).parse(newDate).getTime();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@Override
	public Category getCategory(Document document) {
		String url = document.baseUri();
		String[] parts = url.replaceAll("http://|https://", "").split("/");
		String top = parts[1].toLowerCase();
		String sub = parts[2].toLowerCase();

		switch(top){
			case "politik":
				switch(sub){
					case "inland": return Categories.POLITICS_DOMESTIC;
					case "eu": return Categories.POLITICS_FOREIGN;
					case "ausland": return Categories.POLITICS_FOREIGN;
					case "weltchronik": return Categories.PANORAMA_INTERNATIONAL;
					default: return Categories.POLITICS;
				}
				
			case "wirtschaft":
				switch(sub){
					case "boerse": return Categories.ECONOMY_STOCK;
					case "finanzen": return Categories.ECONOMY_STOCK;
					default: return Categories.ECONOMY;
				}
			
			case "menschen": return Categories.LIFE_PEOPLE;
			case "lebensart": 
				switch(sub){
					case "style": return Categories.LIFE_LIFESTYLE;
					case "reise": return Categories.LIFE_TRAVEL;
					case "genuss": return Categories.LIFE_FOOD;
					case "gesundheit": return Categories.LIFE_HEALTH;
					case "wohnen": return Categories.LIFE_LIVING;
					case "technik": return Categories.SCIENCE;
					case "motor": return Categories.SCIENCE;
					default: return Categories.LIFE;
				}
			
			case "chronik": return Categories.PANORAMA_NATIONAL;
		
			case "kultur":
				switch(sub){
					case "musik": return Categories.CULTURE_MUSIC;
					case "festivalsommer": return Categories.CULTURE_MUSIC;
					case "film": return Categories.CULTURE_FILM;
					case "buehne": return Categories.CULTURE_STAGE;
					case "kunst": return Categories.CULTURE_ARTS;
					case "literatur": return Categories.CULTURE_LITERATURE;
					case "fotografie": return Categories.CULTURE_ARTS;
					default: return Categories.CULTURE;
				}
				
			case "karrieren": return Categories.EDUCATION;
			case "immo": return Categories.REALESTATE;
			default: return Categories.OTHER;
		}
	}
}
