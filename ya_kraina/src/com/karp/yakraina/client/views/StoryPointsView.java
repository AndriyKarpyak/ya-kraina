package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.model.story.StorySubResultJs;
import com.karp.yakraina.client.widgets.MatteBytton;

public class StoryPointsView extends Composite {

	private static StoryPointsViewUiBinder uiBinder = GWT.create(StoryPointsViewUiBinder.class);

	interface StoryPointsViewUiBinder extends UiBinder<Widget, StoryPointsView> {
	}

	@UiField
	InlineLabel resultText;
	
	@UiField
	HTML storyNameLabel;
	
	@UiField
	Image storyIcon;

	@UiField
	MatteBytton button_Accept;

	public StoryPointsView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		ShowNextViewEvent.fire(new StorySummariesView());
	}

	@Override
	protected void onLoad() {

		ColorThemeChangeEvent.fire("themeDark");

		StoryJs story = GameSession.get().getActiveStory();

		storyNameLabel.setText(story.getName());
		storyIcon.setUrl(UriUtils.fromTrustedString("images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + ".svg"));

		int points = 0;

		for (StorySubResultJs finalState : GameSession.get().getActiveStoryResults()) {

			points += finalState.getPoints();
		}

		resultText.setText(String.valueOf(points));

		super.onLoad();
	}

}
