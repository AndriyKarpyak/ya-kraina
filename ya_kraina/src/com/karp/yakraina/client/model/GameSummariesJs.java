package com.karp.yakraina.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class GameSummariesJs extends JavaScriptObject {

	protected GameSummariesJs() {
	}

	public final native int getMinPoints() /*-{
		return this.Min;
	}-*/;

	public final native int getMaxPoints() /*-{
		return this.Max;
	}-*/;

	public final native JsArray<GameSummaryJs> getSummaries() /*-{
		return this.Summary;
	}-*/;

}
