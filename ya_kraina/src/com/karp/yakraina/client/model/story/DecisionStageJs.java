package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JsArray;

public class DecisionStageJs extends StageJs {

	protected DecisionStageJs() {
	}

	public final native JsArray<OptionJs> getOptions() /*-{
		return this.Options;
	}-*/;
}
