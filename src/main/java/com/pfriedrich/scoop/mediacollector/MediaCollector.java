package com.pfriedrich.scoop.mediacollector;

import java.util.List;

public interface MediaCollector {
	void addMediaCollectionTask(MediaCollectionTask mediaCollectionTask);
	List<MediaCollectionTask> getMediaCollectionTasks();
}
