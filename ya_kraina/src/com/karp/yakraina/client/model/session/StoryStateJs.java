package com.karp.yakraina.client.model.session;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.model.story.DecisionOutcomeJs;

public class StoryStateJs extends StoryJs {

	protected StoryStateJs() {
	}

	public final native StageJs getActiveStage() /*-{
		return this.ActiveStage;
	}-*/;

	public final native void setActiveStage(StageJs stage) /*-{
		this.ActiveStage = stage;
	}-*/;

	public final native boolean hasActiveStage() /*-{
		return 'ActiveStage' in this;
	}-*/;

	public final native void addResult(DecisionOutcomeJs result) /*-{
		if ('Results' in this) {
			this['Results'].push(result);
		} else {
			this.Results = [];
		}
	}-*/;

	public final native JsArray<DecisionOutcomeJs> getResults() /*-{
		return this['Results'];
	}-*/;

	public final native boolean hasResults() /*-{
		return 'Results' in this;
	}-*/;

	public final native void addPathEntry(String step) /*-{
		if ('UserPath' in this) {
			this['UserPath'].push(step);
		} else {
			this.UserPath = [];
		}
	}-*/;

	public final native JsArrayString getUserPath() /*-{
		return this.UserPath;
	}-*/;

	public final int getCollectedPoints() {
		if (hasResults()) {
			int points = 0;

			JsArray<DecisionOutcomeJs> jsArray = getResults();

			for (int i = 0; i < jsArray.length(); i++)
				points += jsArray.get(i).getPoints();

			return points;
		} else {
			return 0;

		}
	}
}
