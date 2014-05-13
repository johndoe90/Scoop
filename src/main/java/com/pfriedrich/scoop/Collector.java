package com.pfriedrich.scoop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pfriedrich.scoop.mediacollector.MediaCollectionTask;
import com.pfriedrich.scoop.mediacollector.MediaCollector;
import com.pfriedrich.scoop.mediacollector.MediaCollectors;

@Component
public class Collector {

	private static final Logger logger = LoggerFactory.getLogger(Collector.class); 
	
	@Inject private Application application;
	@Inject private MediaCollectors mediaCollectors;

	private static ExecutorService startExecution(MediaCollectors mediaCollectors){
		ExecutorService executor = Executors.newFixedThreadPool(mediaCollectors.getMediaCollectors().size());
		for(MediaCollector mediaCollector : mediaCollectors.getMediaCollectors()){
			for(MediaCollectionTask task : mediaCollector.getMediaCollectionTasks()){
				executor.execute(task);
			}
		}
		
		executor.shutdown();
		
		return executor;
	}
	
	@Scheduled(fixedRate = 600000)
	public void startCycle() throws Exception{
		//if(application.getCollect()){
		if(application.getRuns() < 1) {
			application.setRuns(application.getRuns() + 1);
			logger.info("starting new collection cycle");
			
			ExecutorService executor = startExecution(mediaCollectors); 
			while(!executor.isTerminated()){
				Thread.sleep(1000);
			}
	
			logger.info("finished collection cycle");
		}
	}
}
