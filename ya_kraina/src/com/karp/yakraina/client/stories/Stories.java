package com.karp.yakraina.client.stories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.karp.yakraina.client._3rdparty.YamlUtils;
import com.karp.yakraina.client.model.story.StoryJs;

public enum Stories {

	Story1(StoriesClientBundle.INSTANCE.dymmyStory1().getText()),
	Story2(StoriesClientBundle.INSTANCE.dymmyStory2().getText());

	private final String storyString;

	Stories(final String jsonString) {
		storyString = jsonString;
	}

	public StoryJs getJs() {
		return YamlUtils.safeEval(storyString);
	}

	public static List<StoryJs> getAll() {
		return Arrays.asList(Stories.values()).stream().map(e -> e.getJs()).collect(Collectors.toList());
	}

}
