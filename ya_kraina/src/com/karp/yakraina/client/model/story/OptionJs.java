package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;

public class OptionJs extends JavaScriptObject {

	protected OptionJs() {
	}

	public final native String getText() /*-{
		return this.Text;
	}-*/;

	public final native OutcomeJs getOutcomeJs() /*-{
		return this.Outcome;
	}-*/;
}
