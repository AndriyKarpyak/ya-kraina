package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;

public class YesNoOptionJs extends JavaScriptObject {

	protected YesNoOptionJs() {
	}

	public final native String getText() /*-{
		return this.Text;
	}-*/;

	public final native YesNoOutcomeJs getOutcomeJs() /*-{
		return this.Outcome;
	}-*/;
}
