package com.karp.yakraina.client.model.story;

public class InformationStageJs extends StageJs {

	protected InformationStageJs() {
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

}
