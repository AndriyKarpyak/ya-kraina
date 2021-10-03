package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.PlayerSelectedStoryEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.stories.Stories;
import com.karp.yakraina.client.widgets.StoryButton;

public class StoriesListView extends View {

	private static StoriesListViewUiBinder uiBinder = GWT.create(StoriesListViewUiBinder.class);

	interface StoriesListViewUiBinder extends UiBinder<Widget, StoriesListView> {
	}

	@UiField
	HTMLPanel root;

	@UiField
	Button button_Accept;

	public StoriesListView() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {
		boolean allCompleted = true;

		for (final StoryJs story : Stories.getAll()) {

			StoryButton storyActivator = new StoryButton(story);

			storyActivator.addClickHandler(event -> {

				StoriesListView.this.addStyleName("fadeOut");

				Scheduler.get().scheduleFixedDelay(() -> {
					PlayerSelectedStoryEvent.fire(story);
					return false;
				}, 700);

			});

			if (!GameSession.get().isStoryCompleted(story))
				allCompleted = false;

			root.add(storyActivator);
		}

		button_Accept.setEnabled(allCompleted);
		button_Accept.setVisible(allCompleted);
	}

	@Override
	protected void onLoad() {

		GameSession.get().discardActiveStory();
		ColorThemeChangeEvent.fire("themeDark");

		super.onLoad();
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		ShowNextViewEvent.fire(new GameResultsView());
	}

}
