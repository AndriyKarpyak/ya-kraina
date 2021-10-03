package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.model.session.GameSession;

public class GameResultsView extends View {

	private static GameResultsViewUiBinder uiBinder = GWT.create(GameResultsViewUiBinder.class);

	interface GameResultsViewUiBinder extends UiBinder<Widget, GameResultsView> {
	}
	
	@UiField
	HTML points;

	public GameResultsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {
		ColorThemeChangeEvent.fire("themeDark");
		
		points.setText(String.valueOf(GameSession.get().getTotalPoints()));
	}

}
