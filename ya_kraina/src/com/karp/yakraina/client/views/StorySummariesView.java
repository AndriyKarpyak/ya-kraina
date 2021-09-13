package com.karp.yakraina.client.views;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StorySubResultJs;
import com.karp.yakraina.client.model.story.SummaryConditionJs;

public class StorySummariesView extends Composite {

	private static StorySummariesViewUiBinder uiBinder = GWT.create(StorySummariesViewUiBinder.class);

	interface StorySummariesViewUiBinder extends UiBinder<Widget, StorySummariesView> {
	}

	public StorySummariesView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel resultsPanel;

	@UiField
	Button button_Accept;

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		StoryEndEvent.fire();
	}

	@Override
	protected void onLoad() {
		
		ColorThemeChangeEvent.fire("themeDark");

		int points = 0;

		for (StorySubResultJs finalState : GameSession.get().getActiveStoryResults()) {

			if (finalState.getText() != null && !finalState.getText().isEmpty()) {
				resultsPanel.add(new HTML(finalState.getText()));
				resultsPanel.add(new HTML(SafeHtmlUtils.fromSafeConstant("<p></p>")));
			}

			points += finalState.getPoints();
		}

		List<SummaryConditionJs> conditions = GameSession.get().getActiveStorySummaryConditions();

		for (int i = conditions.size() - 1; i >= 0; i--) {
			SummaryConditionJs condition = conditions.get(i);

			if (points > condition.getLowerBound() || (points == 0 && points == condition.getLowerBound())) {
				resultsPanel.add(new HTML(SafeHtmlUtils.fromSafeConstant("-----")));
				resultsPanel.add(new HTML(SafeHtmlUtils.fromSafeConstant("<p></p>")));
				resultsPanel.add(new HTML("Зароблено балів: " + points));
				resultsPanel.add(new HTML(SafeHtmlUtils.fromSafeConstant("<p></p>")));
				resultsPanel.add(new HTML(condition.getText()));
				return;
			}

		}
		super.onLoad();
	}

}
