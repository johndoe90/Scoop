package com.pfriedrich.scoop.mediacollector;

import java.util.ArrayList;
import java.util.List;

public class MediaCollectorImpl implements MediaCollector{

	private List<MediaCollectionTask> mediaCollectionTasks = new ArrayList<MediaCollectionTask>();
	
	@Override
	public void addMediaCollectionTask(MediaCollectionTask mediaCollectionTask) {
		mediaCollectionTasks.add(mediaCollectionTask);
	}

	@Override
	public List<MediaCollectionTask> getMediaCollectionTasks() {
		return mediaCollectionTasks;
	}
}
