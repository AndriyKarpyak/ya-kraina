package com.karp.yakraina.client.model.story;

public class FinalStageJs extends StageJs {

	protected FinalStageJs() {
	}

	public final int getPoints() {
		if (hasPoints())
			return getPointsJs();
		else
			return 0;
	}

	private final native int getPointsJs() /*-{
		return this.Points;
	}-*/;

	private final native boolean hasPoints() /*-{
		return 'Points' in this;
	}-*/;
}
