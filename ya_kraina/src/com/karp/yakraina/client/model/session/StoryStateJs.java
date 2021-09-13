package com.karp.yakraina.client.model.session;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.karp.yakraina.client.model.story.StorySubResultJs;
import com.karp.yakraina.client.model.story.SummaryConditionJs;

public class StoryStateJs extends JavaScriptObject {

	protected StoryStateJs() {
	}

	public static native StoryStateJs create() /*-{
		return {
			results : []
		};
	}-*/;

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;

	public final native JsArray<SummaryConditionJs> getSummaryConditions() /*-{
		return this.Summary;
	}-*/;

	public final native void setSummaryConditions(JsArray<SummaryConditionJs> summaryConditions) /*-{
		this.Summary = summaryConditions;
	}-*/;

	public final native void addResult(StorySubResultJs result) /*-{
		this['results'].push(result);
	}-*/;

	public final native JsArray<StorySubResultJs> getResults() /*-{
		return this['results'];
	}-*/;

	public final int getCollectedPoints() {
		int points = 0;

		JsArray<StorySubResultJs> jsArray = getResults();

		for (int i = 0; i < jsArray.length(); i++)
			points += jsArray.get(i).getPoints();

		return points;
	}
}
