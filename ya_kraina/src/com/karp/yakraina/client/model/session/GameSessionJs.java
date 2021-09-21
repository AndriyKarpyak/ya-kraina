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

	public final native void startStory(StoryStateJs story) /*-{
		this.ActiveStory = story;
	}-*/;

	public final void completeStory() {
		if (hasActiveStory()) {
			StoryStateJs activeStory = getActiveStory();
			addStoryToCompleted(activeStory.getKey(), activeStory);
			deleteActiveStory();
		}
	}

	public final native StoryStateJs getStoryFromCompleted(String id) /*-{
		return this[id];
	}-*/;

	private final native void addStoryToCompleted(String id, StoryStateJs story) /*-{
		this[id] = story;
	}-*/;

	public final native boolean hasStoryInCompleted(String id) /*-{
		return id in this;
	}-*/;

	public final native StoryStateJs getActiveStory() /*-{
		return this.ActiveStory;
	}-*/;

	public final native void deleteActiveStory() /*-{
		delete this.ActiveStory;
	}-*/;

	public final native boolean hasActiveStory() /*-{
		return 'ActiveStory' in this;
	}-*/;

	public final native boolean isEmpty() /*-{
		return JSON.stringify(this) === JSON.stringify({});
	}-*/;

	public final native void store() /*-{
		sessionStorage.setItem("autosave-ya-kraina", JSON.stringify(this));
	}-*/;

	public static native String restore() /*-{
		return sessionStorage.getItem("autosave-ya-kraina");
	}-*/;

}
