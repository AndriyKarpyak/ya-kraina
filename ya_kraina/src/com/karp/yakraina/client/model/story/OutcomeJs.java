package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;
import com.karp.yakraina.client.model.session.GameSession;

public class OutcomeJs extends JavaScriptObject {

	protected OutcomeJs() {
	}

	public final StageJs getNextStage() {

		if (hasPoints())
			GameSession.get().addSubResult((DecisionOutcomeJs) this.cast());

		StageJs nextStage = GameSession.get().getStage(getGoTo());

		if (isIgnored())
			nextStage.setDontMemorisePreviosStageJs();

		return nextStage;
	}

	public final native String getGoTo() /*-{
		return this.GoTo;
	}-*/;

	private final native boolean hasPoints() /*-{
		return 'Points' in this;
	}-*/;

	public final boolean isIgnored() {
		return hasIgnore() && isIgnoredJs();
	}

	private final native boolean isIgnoredJs() /*-{
		return this.Ignore;
	}-*/;

	private final native boolean hasIgnore() /*-{
		return 'Ignore' in this;
	}-*/;
}
