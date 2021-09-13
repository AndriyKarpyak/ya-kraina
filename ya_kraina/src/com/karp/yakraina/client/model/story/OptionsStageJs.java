package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JsArray;

public class OptionsStageJs extends StageJs {

	protected OptionsStageJs() {
	}

	public final String getEmoji() {
		if (hasEmoji())
			return getEmojiJs().toLowerCase();
		else
			return "confused";
	}

	private final native String getEmojiJs() /*-{
		return this.Emoji;
	}-*/;

	private final native boolean hasEmoji() /*-{
		return 'Emoji' in this;
	}-*/;

	public final native OutcomeJs getOutcomeJs() /*-{
		return this.Outcome;
	}-*/;

	public final native JsArray<OptionJs> getOptionsJs() /*-{
		return this.Options;
	}-*/;

}
