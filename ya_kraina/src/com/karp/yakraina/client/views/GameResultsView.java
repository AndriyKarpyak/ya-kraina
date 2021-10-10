package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.model.GameSummaries;
import com.karp.yakraina.client.model.GameSummaryJs;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.widgets.Emoji;

public class GameResultsView extends View {

	private static GameResultsViewUiBinder uiBinder = GWT.create(GameResultsViewUiBinder.class);

	interface GameResultsViewUiBinder extends UiBinder<Widget, GameResultsView> {
	}

	@UiField
	Emoji emoji;

	@UiField
	HTML mask;

	@UiField
	HTML points;

	@UiField
	HTML message;

	public GameResultsView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		message.getElement().getStyle().setOpacity(0);
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {
		ColorThemeChangeEvent.fire("themeDark");

		points.setText(String.valueOf(0));
		

		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

			double value = 0;
			double goalValue = GameSession.get().getTotalPoints();
			double goalPositionPCT = (goalValue * 100.0) / Double
					.valueOf(GameSummaries.get().getJs().getMaxPoints() - GameSummaries.get().getJs().getMinPoints());
			double stepPCT = goalPositionPCT / goalValue;
			double positionPCT = 0;
			JsArray<GameSummaryJs> summaries = GameSummaries.get().getJs().getSummaries();
			GameSummaryJs activeSummary = summaries.shift();

			@Override
			public boolean execute() {
				if (summaries.length() > 0 && (value == 0 || value >= activeSummary.getBound())) {
					
					if (value > 0)
						emoji.getElement().addClassName("fadeOut");
					
					Scheduler.get().scheduleFixedDelay(() -> {
						emoji.setUrl(activeSummary);
						emoji.getElement().removeClassName("fadeOut");
						return false;
					}, 20);
					
					
					message.setText(activeSummary.getText());
					activeSummary = summaries.shift();
				}

				points.setText(String.valueOf(++value));

				positionPCT += stepPCT;
				mask.getElement().getStyle().setLeft(positionPCT, Unit.PCT);

				if (value < goalValue)
					return true;
				else {
					message.addStyleName("fadeIn");
					return false;
				}
			}
		}, 12);

	}

}
