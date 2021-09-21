package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.views.StoriesListView;

public class GameRulesView extends Composite {

	private static GameRulesViewUiBinder uiBinder = GWT.create(GameRulesViewUiBinder.class);

	interface GameRulesViewUiBinder extends UiBinder<Widget, GameRulesView> {
	}

	public GameRulesView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button_Accept;

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {

		ShowNextViewEvent.fire(new StoriesListView());

	}
}
