package com.karp.yakraina.client.stories;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface StoriesClientBundle extends ClientBundle {
	StoriesClientBundle INSTANCE = GWT.create(StoriesClientBundle.class);

	@Source("зберігання_велосипеда.yaml")
	TextResource dymmyStory1();

	@Source("громадськи_бюджет.yaml")
	TextResource dymmyStory2();
	
	@Source("освітлення_парку.yaml")
	TextResource dymmyStory3();
}
