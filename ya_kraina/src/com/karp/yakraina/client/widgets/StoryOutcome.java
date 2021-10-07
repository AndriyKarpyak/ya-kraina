package com.karp.yakraina.client.widgets;

import java.util.List;
import java.util.Optional;

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

	private String storyName;
	private int collectedPoints;
	private SummaryConditionJs satisfiedCondition;

	@UiConstructor
	public StoryOutcome() {
		initWidget(uiBinder.createAndBindUi(this));

		storyName = GameSession.get().getActiveStory().getName();
		collectedPoints = GameSession.get().getActiveStoryCollectedPoints();
		
		List<SummaryConditionJs> conditions = GameSession.get().getActiveStorySummaryConditions();
		for (int i = conditions.size() - 1; i >= 0; i--) {
			SummaryConditionJs condition = conditions.get(i);
			
			if (collectedPoints >= condition.getBound()) {
				satisfiedCondition = condition;
				break;
			}
		}
	}

	@Override
	protected void onLoad() {

		hide();

		storyNameLabel.setHTML(SafeHtmlUtils.fromTrustedString(storyName));
		pointsText.setText(String.valueOf(collectedPoints));
		storyIcon.setUrl(UriUtils.fromTrustedString("images/story_selection/" + storyName.replace(" ", "_").toLowerCase() + ".svg"));
		storySummaryText.setHTML(SafeHtmlUtils.fromTrustedString(satisfiedCondition.getText()));

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

	public Optional<SummaryConditionJs> getSatisfiedCondition() {
		return Optional.ofNullable(satisfiedCondition);
	}
	
	

}
