package com.karp.yakraina.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.session.StoryStateJs;
import com.karp.yakraina.client.model.story.SummaryConditionJs;

public class StoryOutcome extends Composite implements HasClickHandlers {

	private static StoryOutcomeUiBinder uiBinder = GWT.create(StoryOutcomeUiBinder.class);

	interface StoryOutcomeUiBinder extends UiBinder<Widget, StoryOutcome> {
	}

	@UiField
	HTML storyNameLabel;

	@UiField
	HTML storySummaryText;

	@UiField
	Image storyIcon;

	@UiField
	InlineLabel pointsText;

	@UiField
	MatteBytton button_Accept;

	@UiConstructor
	public StoryOutcome() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {

		hide();

		StoryStateJs story = GameSession.get().getActiveStory();
		int collectedPoints = GameSession.get().getActiveStoryCollectedPoints();
		List<SummaryConditionJs> conditions = GameSession.get().getActiveStorySummaryConditions();

		storyNameLabel.setHTML(SafeHtmlUtils.fromTrustedString(story.getName()));
		pointsText.setText(String.valueOf(collectedPoints));
		storyIcon.setUrl(UriUtils.fromTrustedString(
				"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + ".svg"));
		
		for (SummaryConditionJs condition : conditions) {
			
			if (collectedPoints < condition.getBound() || collectedPoints == 0) {
				storySummaryText.setHTML(SafeHtmlUtils.fromTrustedString(condition.getText()));
				break;
			}
		}

		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
		
		super.onLoad();
	}

	public void show() {
		removeStyleName("hidden");
	}

	public void hide() {
		if (!getStyleName().contains("hidden"))
			addStyleName("hidden");
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return button_Accept.addClickHandler(handler);
	}

}
