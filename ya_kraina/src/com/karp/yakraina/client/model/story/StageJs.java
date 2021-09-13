package com.karp.yakraina.client.model.story;

import com.google.gwt.core.client.JavaScriptObject;
import com.karp.yakraina.client.model.StageType;

public class StageJs extends JavaScriptObject {

	protected StageJs() {
	}

	public final String getKey() {
		if (hasKey())
			return "stage_" + getKeyJs().toLowerCase().replace(" ", "_");
		else
			throw new NullPointerException("Missing 'Key' parameter for stage: [" + String.valueOf(this) + "]");
	}

	private final native String getKeyJs() /*-{
		return this.Key;
	}-*/;

	private final native boolean hasKey() /*-{
		return 'Key' in this;
	}-*/;

	public final StageType getType() {
		return hasType() ? StageType.of(getTypeJs()) : StageType.FINAL;
	}

	private final native String getTypeJs() /*-{
		return this.Type;
	}-*/;

	private final native boolean hasType() /*-{
		return 'Type' in this;
	}-*/;

	public final String getText() {
		return hasText() ? getTextJs() : null;
	}

	private final native String getTextJs() /*-{
		return this.Text;
	}-*/;

	private final native boolean hasText() /*-{
		return 'Text' in this;
	}-*/;
}
