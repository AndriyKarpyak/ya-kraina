package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;

public class SummaryConditionJs extends JavaScriptObject {

	protected SummaryConditionJs() {
	}

	public final native int getLowerBound() /*-{
		return this.Bound;
	}-*/;

	public final native String getText() /*-{
		return this.Text;
	}-*/;
}
