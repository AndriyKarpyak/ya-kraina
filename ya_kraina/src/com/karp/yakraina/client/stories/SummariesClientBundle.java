package com.karp.yakraina.client.stories;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface SummariesClientBundle extends ClientBundle {
	SummariesClientBundle INSTANCE = GWT.create(SummariesClientBundle.class);

	@Source("підсумки.yaml")
	TextResource summariesTextResource();
}
