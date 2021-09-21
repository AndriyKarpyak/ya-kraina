package com.karp.yakraina.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.stories.StoryState;

public class StoryButton extends Composite implements HasClickHandlers {

	private static StoryButtonUiBinder uiBinder = GWT.create(StoryButtonUiBinder.class);

	interface StoryButtonUiBinder extends UiBinder<Widget, StoryButton> {
	}

	public StoryButton(StoryJs story) {
		initWidget(uiBinder.createAndBindUi(this));

		storyName.setText(story.getName());

		StoryState state = StoryState.calculate(story, GameSession.get());

		stateIcon.setUrl(state.getIconUri());
		disabled = StoryState.BLOCKED.equals(state);
		if (disabled) {
			getElement().setAttribute("disabled", "");
			storyIcon.setUrl(UriUtils.fromTrustedString(
					"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + "_locked.svg"));
		} else {
			storyIcon.setUrl(UriUtils.fromTrustedString(
					"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + ".svg"));
		}

		GameSession.get().getCompletedStory(story).ifPresent(storyState -> {
			stateIcon.setStyleName("done", true);
			result.getElement().getStyle().clearDisplay();
			result.setText(storyState.getCollectedPoints() + "");
		});
		
		storyName.addMouseOverHandler(event -> {
			if (!disabled)
				storyIcon.setUrl(UriUtils.fromTrustedString(
						"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + "_in.svg"));
		});

		storyName.addMouseOutHandler(event -> {
			storyIcon.setUrl(UriUtils.fromTrustedString(
					"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + ".svg"));
		});

		storyIcon.addMouseOverHandler(event -> {
			if (!disabled)
				storyIcon.setUrl(UriUtils.fromTrustedString(
						"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + "_in.svg"));
		});

		storyIcon.addMouseOutHandler(event -> {
			storyIcon.setUrl(UriUtils.fromTrustedString(
					"images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + ".svg"));
		});

	}

	@UiField
	Image storyIcon;
	@UiField
	Image stateIcon;
	@UiField
	InlineLabel result;
	@UiField
	InlineLabel storyName;

	private boolean disabled = true;

	@Override
	public HandlerRegistration addClickHandler(final ClickHandler handler) {
		return addDomHandler(event -> {

			if (!disabled)
				handler.onClick(event);

		}, ClickEvent.getType());
	}

}
