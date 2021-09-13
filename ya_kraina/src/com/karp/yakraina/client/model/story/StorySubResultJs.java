package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;

public class StorySubResultJs extends JavaScriptObject {

	protected StorySubResultJs() {
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

	private final native boolean hasText() /*-{
		return 'Text' in this;
	}-*/;
}
