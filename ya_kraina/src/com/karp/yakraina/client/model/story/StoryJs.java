package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class StoryJs extends JavaScriptObject {

	protected StoryJs() {
	}

	public final String getKey() {
		return "story_" + getName().toLowerCase().replace(" ", "_");
	}

	public final native String getName() /*-{
		return this.Name;
	}-*/;

	public final native int getMinPoints() /*-{
		return this.MinPoints;
	}-*/;

	public final native JsArray<SummaryConditionJs> getSummaryConditions() /*-{
		return this.Summary;
	}-*/;

	public final native JsArray<StageJs> getStagesJs() /*-{
		return this.Stages;
	}-*/;



}
