package com.pfriedrich.scoop;

import org.springframework.stereotype.Component;

@Component
public class Application {
	
	private Integer runs = 0;
	private Boolean collect;
	
	public Application(){
		this.collect = System.getenv("collect") != null ? System.getenv("collect").equals("true") : true; //:false
	}
	
	public Integer getRuns() {
		return this.runs;
	}
	
	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Boolean getCollect() {
		return collect;
	}

	public void setCollect(Boolean collect) {
		this.collect = collect;
	}
}
