package com.pfriedrich.scoop.controller;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfriedrich.scoop.domain.Category;
import com.pfriedrich.scoop.domain.Media;
import com.pfriedrich.scoop.domain.MediaProvider;
import com.pfriedrich.scoop.domain.dto.DtoListConverter;
import com.pfriedrich.scoop.domain.dto.MediaDto;
import com.pfriedrich.scoop.service.CategoryService;
import com.pfriedrich.scoop.service.MediaProviderService;
import com.pfriedrich.scoop.service.MediaService;
 
@Controller
@RequestMapping("/media")
public class MediaController {

	@Inject
	private MediaService mediaService;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private MediaProviderService mediaProviderService;
	
	private void requestedMedia(List<Media> media){
		for(Media medium : media){
			System.out.println(medium.getId() + " / " + medium.getTitle());
		}
	}
	
	//@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {""})
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"ids[]"})
	public @ResponseBody List<MediaDto> findAll(@RequestParam("ids[]") Long[] ids){
		List<Media> media = mediaService.findAll(Arrays.asList(ids));
		
		return DtoListConverter.getInstance().convert(media, MediaDto.class);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"q"})
	public @ResponseBody List<MediaDto> query(@RequestParam String q, @RequestParam(required = false) Integer quantity, @RequestParam(required = false) Long first){
		Media firstMedium = first != null ? mediaService.findById(first) : null;
		List<Media> media = mediaService.query(q, quantity != null ? quantity : 10, firstMedium);
		
		return DtoListConverter.getInstance().convert(media, MediaDto.class);
	}
	
	
	@RequestMapping(value = "/{id}/consume", method = RequestMethod.POST)
	public @ResponseBody void consume(@PathVariable("id") Long id){
		Media media = mediaService.findById(id);
		if(media != null){
			mediaService.consume(media);
		}
	}
	 
	//works    
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"categories[]", "mediaProviders[]", "last"})
	public @ResponseBody List<MediaDto> getAfter(@RequestParam("categories[]") Long[] categories, @RequestParam("mediaProviders[]") Long[] mediaProviders, @RequestParam Long last, @RequestParam(required = false) Integer quantity){
		Long start = System.currentTimeMillis();
		
		List<MediaDto> mediaDtos = null; 
		Media medium = mediaService.findById(last);
		if(medium != null){
			List<Category> cats = categoryService.findById(Arrays.asList(categories));
			List<MediaProvider> provs = mediaProviderService.findById(Arrays.asList(mediaProviders));
			List<Media> media = mediaService.findAfterThis(cats, provs, medium, quantity != null ? quantity : 10);
			mediaDtos = DtoListConverter.getInstance().convert(media, MediaDto.class);
			
			requestedMedia(media);
		}
		
		//System.out.println("MediaController/after: " + (System.currentTimeMillis() - start) + "ms!");
		return mediaDtos;
	}
	
	//works
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"categories[]", "mediaProviders[]", "first"})
	public @ResponseBody List<MediaDto> getBefore(@RequestParam("categories[]") Long[] categories, @RequestParam("mediaProviders[]") Long[] mediaProviders, @RequestParam Long first, @RequestParam(required = false) Integer quantity){
		Long start = System.currentTimeMillis();
		
		List<MediaDto> mediaDtos = null;
		Media medium = mediaService.findById(first);
		if(medium != null){
			List<Category> cats = categoryService.findById(Arrays.asList(categories));
			List<MediaProvider> provs = mediaProviderService.findById(Arrays.asList(mediaProviders));	
			List<Media> media = mediaService.findBeforeThis(cats, provs, medium, quantity != null ? quantity : 10);
			mediaDtos = DtoListConverter.getInstance().convert(media, MediaDto.class);
			
			requestedMedia(media);
		}

		//System.out.println("MediaController/before: " + (System.currentTimeMillis() - start) + "ms!");
		return mediaDtos;
	}
	
	//works
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"categories[]", "mediaProviders[]"})
	public @ResponseBody List<MediaDto> getRecent(@RequestParam("categories[]") Long[] categories, @RequestParam("mediaProviders[]") Long[] mediaProviders, @RequestParam(required = false) Integer quantity){
		//Long start = System.currentTimeMillis();
		
		List<Category> cats = categoryService.findById(Arrays.asList(categories));
		System.out.println("Number of cats: " + cats.size());
		
		List<MediaProvider> provs = mediaProviderService.findById(Arrays.asList(mediaProviders));	
		System.out.println("Number of provs: " + provs.size());
		
		List<Media> media = mediaService.findRecent(cats, provs, quantity != null ? quantity : 10);
		//System.out.println("MediaController/recent: " + (System.currentTimeMillis() - start) + "ms!");
		requestedMedia(media);
		
		return DtoListConverter.getInstance().convert(media, MediaDto.class);
	}
}
