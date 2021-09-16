package com.karp.yakraina.client.views.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.PlayerSelectedStoryEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.stories.Stories;
import com.karp.yakraina.client.widgets.StoryButton;

public class StorySelectionView extends Composite {

	private static StorySelectionViewUiBinder uiBinder = GWT.create(StorySelectionViewUiBinder.class);

	interface StorySelectionViewUiBinder extends UiBinder<Widget, StorySelectionView> {
	}

	@UiField
	HTMLPanel root;

	@UiField
	Button button_Accept;

	public StorySelectionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		
		ColorThemeChangeEvent.fire("themeDark");

		boolean allCompleted = true;

		for (final StoryJs story : Stories.getAll()) {
			
			StoryButton storyActivator = new StoryButton(story);

			storyActivator.addClickHandler(event -> {
				
				PlayerSelectedStoryEvent.fire(story);
				ColorThemeChangeEvent.fire("themeLight");
			});
				
			if (!GameSession.get().isStoryCompleted(story))
				allCompleted = false;
			

			root.add(storyActivator);
		}

		button_Accept.setEnabled(allCompleted);
		button_Accept.setVisible(allCompleted);

		super.onLoad();
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {

	}

}
