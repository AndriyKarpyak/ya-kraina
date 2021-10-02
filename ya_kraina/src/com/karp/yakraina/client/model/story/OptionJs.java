package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;
import com.karp.yakraina.client.model.session.GameSession;

public class OptionJs extends JavaScriptObject {

	protected OptionJs() {
	}

	public final native String getText() /*-{
		return this.Text;
	}-*/;

	public final StageJs getCompleteStage() {
		if (hasComplete())
			return GameSession.get().getStage(getComplete());

		return GameSession.get().getStage(getOutcomeJs().getGoTo());
	}

	private final native String getComplete() /*-{
		return this.Complete;
	}-*/;

	private final native boolean hasComplete() /*-{
		return 'Complete' in this;
	}-*/;

	public final native OutcomeJs getOutcomeJs() /*-{
		return this.Outcome;
	}-*/;
}
