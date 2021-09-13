package com.karp.yakraina.client.stories;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StoryJs;

public enum StoryState {
	
	AVAILIBLE(UriUtils.fromTrustedString("images/story_states/availible.svg")),
	BLOCKED(UriUtils.fromTrustedString("images/story_states/blocked.svg")),
	DONE(UriUtils.fromTrustedString("images/story_states/done.svg"));
	
	private SafeUri iconUri;

	StoryState(final SafeUri iconUri) {
		this.iconUri = iconUri;
	}
	
	public SafeUri getIconUri() {
		return iconUri;
	}

	public static StoryState calculate(StoryJs story, GameSession gameSession) {
		if (gameSession.isStoryCompleted(story))
			return DONE;
		else if (gameSession.getActiveStoryCollectedPoints() >= story.getMinPoints())
			return AVAILIBLE;
		else 
			return BLOCKED;
	}

}
