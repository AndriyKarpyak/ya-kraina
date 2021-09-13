package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.views.game.StorySelectionView;

public class WelcomeView extends Composite {

	private static WelcomePageUiBinder uiBinder = GWT.create(WelcomePageUiBinder.class);

	interface WelcomePageUiBinder extends UiBinder<Widget, WelcomeView> {
	}

	public WelcomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	TextBox input_PlayerName;

	@UiField
	Button button_Accept;

	@UiField
	Button button_ChangeName;

	@UiField
	InlineLabel welcome_player_label;

	@Override
	protected void onLoad() {
		
		ColorThemeChangeEvent.fire("themeDark");

		input_PlayerName.getElement().setAttribute("placeholder", "Твоє ім’я");

		if (!GameSession.get().isEmpty()) {
			input_PlayerName.setVisible(false);
			welcome_player_label.setText(GameSession.get().getPlayerName());
			button_ChangeName.setVisible(true);
		} else {
			input_PlayerName.setVisible(true);
			welcome_player_label.setVisible(false);
			button_ChangeName.setVisible(false);
		}

		super.onLoad();
	}

	@UiHandler("button_Accept")
	public void onStartGame(ClickEvent event) {
		if (input_PlayerName.isVisible())
			GameSession.get().setPlayerName(input_PlayerName.getText());

		ShowNextViewEvent.fire(new ShowNextViewEvent(new StorySelectionView()));

//
//		if (GameSession.get().isEmpty())
//			ShowNextViewEvent.fire(new WelcomeNewPlayerView());
//		else
//			ShowNextViewEvent.fire(new StorySelectionView());
	}

	@UiHandler("button_ChangeName")
	public void onChangePlayerName(ClickEvent event) {
		GameSession.get().clear();

		ShowNextViewEvent.fire(new WelcomeView());

	}

}
