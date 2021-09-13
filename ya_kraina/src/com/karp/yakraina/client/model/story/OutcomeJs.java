package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;
import com.karp.yakraina.client.model.session.GameSession;

public class OutcomeJs extends JavaScriptObject {

	protected OutcomeJs() {
	}

	public final StageJs getNextStage() {

		if (hasPoints())
			GameSession.get().addSubResult((StorySubResultJs) this.cast());

		return GameSession.get().getStage(getGoTo());
	}

	private final native String getGoTo() /*-{
		return this.GoTo;
	}-*/;

	private final native boolean hasPoints() /*-{
		return 'Points' in this;
	}-*/;
}
