package com.karp.yakraina.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class GameSummaryJs extends JavaScriptObject {

	protected GameSummaryJs() {
	}

	public final double getBound(double distance) {
		return (getBoundJs() * distance) / 100;
	}
	
	public final native int getBoundJs() /*-{
		return this.Bound;
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

	public final native String getText() /*-{
		return this.Text;
	}-*/;
}
