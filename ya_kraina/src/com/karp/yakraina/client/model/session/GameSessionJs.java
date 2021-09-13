package com.karp.yakraina.client.model.session;

import com.google.gwt.core.client.JavaScriptObject;

class GameSessionJs extends JavaScriptObject {

	protected GameSessionJs() {
	}

	public static native GameSessionJs create() /*-{
		return {};
	}-*/;

	public final native String getPlayerName() /*-{
		return this.playerName;
	}-*/;

	public final native void setPlayerName(String playerName) /*-{
		this.playerName = playerName;
	}-*/;

	public final native String getFBId() /*-{
		return this.fbId;
	}-*/;

	public final native void setFBId(String fbId) /*-{
		this.fbId = fbId;
	}-*/;

	public final native boolean hasStory(String id) /*-{
		return id in this;
	}-*/;

	public final native StoryStateJs getStory(String id) /*-{
		return this[id];
	}-*/;

	public final native void addStory(String id, StoryStateJs story) /*-{
		this[id] = story;
	}-*/;

	public final native boolean isEmpty() /*-{
		return JSON.stringify(this) === JSON.stringify({});
	}-*/;

}
