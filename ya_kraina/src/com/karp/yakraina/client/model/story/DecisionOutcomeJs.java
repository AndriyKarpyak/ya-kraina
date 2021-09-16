package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;

public class DecisionOutcomeJs extends JavaScriptObject {

	protected DecisionOutcomeJs() {
	}

	public final int getPoints() {
		if (hasPoints())
			return getPointsJs();
		else
			return 0;
	}

	private final native int getPointsJs() /*-{
		return this.Points;
	}-*/;

	private final native boolean hasPoints() /*-{
		return 'Points' in this;
	}-*/;

	public final String getText() {
		return hasText() ? getTextJs() : null;
	}

	private final native String getTextJs() /*-{
		return this.Text;
	}-*/;

	public final native boolean hasText() /*-{
		return 'Text' in this;
	}-*/;
	
	public final String getEmoji() {
		if (hasEmoji())
			return getEmojiJs().toLowerCase();
		else
			return "confused";
	}

	private final native String getEmojiJs() /*-{
		return this.Emoji;
	}-*/;

	private final native boolean hasEmoji() /*-{
		return 'Emoji' in this;
	}-*/;
}
