package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;
import com.karp.yakraina.client.model.session.GameSession;

public class YesNoOutcomeJs extends JavaScriptObject {

	protected YesNoOutcomeJs() {
	}

	public final StageJs getYesStage() {
		return GameSession.get().getStage(getYesGoTo());
	}

	private final native String getYesGoTo() /*-{
		return this.Yes.GoTo;
	}-*/;

	public final StageJs getNoStage() {
		return GameSession.get().getStage(getNoGoTo());
	}

	private final native String getNoGoTo() /*-{
		return this.No.GoTo;
	}-*/;
}
