package com.karp.yakraina.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.session.StoryStateJs;
import com.karp.yakraina.client.model.story.StageJs;

public class AboutGameView extends View {

	private static AboutGameViewUiBinder uiBinder = GWT.create(AboutGameViewUiBinder.class);

	interface AboutGameViewUiBinder extends UiBinder<Widget, AboutGameView> {
	}

	@UiField
	Image close;

	public AboutGameView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("close")
	public void onClose(ClickEvent event) {
		StoryStateJs activeStory = GameSession.get().getActiveStory();
		if (activeStory != null) {
			StageJs activeStage = activeStory.getActiveStage();
			if (activeStage != null)
				NextStageEvent.fire(activeStage);
			else
				ShowNextViewEvent.fire(new StoriesListView());
		}
	}

	@Override
	protected void onLoad() {
		ColorThemeChangeEvent.fire("themeLight");
		super.onLoad();
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {
		setVisible(true);
	}

}
