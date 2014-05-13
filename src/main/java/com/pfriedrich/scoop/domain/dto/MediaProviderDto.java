package com.pfriedrich.scoop.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfriedrich.scoop.domain.MediaProvider;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaProviderDto {
	
	private MediaProviderDto(){}
	public MediaProviderDto(MediaProvider mediaProvider){
		this.id = mediaProvider.getId();
		this.mediaProviderId = mediaProvider.getMediaProviderId();
		this.mediaProviderName = mediaProvider.getMediaProviderName();
		this.logoSmall = mediaProvider.getLogoSmall();
		this.logoMedium = mediaProvider.getLogoMedium();
		this.logoLarge = mediaProvider.getLogoLarge();
		this.domain = mediaProvider.getDomain();
	}
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("mediaProviderId")
	private String mediaProviderId;
	
	@JsonProperty("mediaProviderName")
	private String mediaProviderName;
	
	@JsonProperty("domain")
	private String domain;
	
	@JsonProperty("logoSmall")
	private String logoSmall;
	
	@JsonProperty("logoMedium")
	private String logoMedium;
	
	@JsonProperty("logoLarge")
	private String logoLarge;
	
	public String getMediaProviderId() {
		return mediaProviderId;
	}
	public void setMediaProviderId(String mediaProviderId) {
		this.mediaProviderId = mediaProviderId;
	}
	public String getMediaProviderName() {
		return mediaProviderName;
	}
	public void setMediaProviderName(String mediaProviderName) {
		this.mediaProviderName = mediaProviderName;
	}
	public String getLogoSmall() {
		return logoSmall;
	}
	public void setLogoSmall(String logoSmall) {
		this.logoSmall = logoSmall;
	}
	public String getLogoMedium() {
		return logoMedium;
	}
	public void setLogoMedium(String logoMedium) {
		this.logoMedium = logoMedium;
	}
	public String getLogoLarge() {
		return logoLarge;
	}
	public void setLogoLarge(String logoLarge) {
		this.logoLarge = logoLarge;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
