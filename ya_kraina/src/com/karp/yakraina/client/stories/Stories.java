package com.karp.yakraina.client.stories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.karp.yakraina.client._3rdparty.YamlUtils;
import com.karp.yakraina.client.model.story.StoryJs;

public enum Stories {

	Story1(StoriesClientBundle.INSTANCE.dymmyStory1().getText()),
	Story2(StoriesClientBundle.INSTANCE.dymmyStory2().getText());

	private final StoryJs story;

	Stories(final String jsonString) {
		story = YamlUtils.safeEval(jsonString);
	}

	public StoryJs getJs() {
		return story;
	}

	public static List<StoryJs> getAll() {
		
		Arrays.asList(Stories.values()).stream().map(e -> e.getJs()).collect(Collectors.toList()).forEach(s -> GWT.log(String.valueOf(s)));
		
		return Arrays.asList(Stories.values()).stream().map(e -> e.getJs()).collect(Collectors.toList());
	}

}
