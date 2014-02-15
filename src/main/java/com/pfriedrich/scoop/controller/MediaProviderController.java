package com.pfriedrich.scoop.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfriedrich.scoop.domain.MediaProvider;
import com.pfriedrich.scoop.domain.dto.DtoListConverter;
import com.pfriedrich.scoop.domain.dto.MediaProviderDto;
import com.pfriedrich.scoop.service.MediaProviderService;

@Controller
@RequestMapping("/mediaProviders")
public class MediaProviderController {

	@Inject
	private MediaProviderService mediaProviderService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<MediaProviderDto> getAll(){
		List<MediaProvider> mediaProviders = mediaProviderService.findAll();
		
		return DtoListConverter.getInstance().convert(mediaProviders, MediaProviderDto.class);
	}
}
