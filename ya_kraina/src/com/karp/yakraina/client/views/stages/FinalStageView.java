package com.karp.yakraina.client.views.stages;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.DecisionOutcomeJs;
import com.karp.yakraina.client.views.View;
import com.karp.yakraina.client.widgets.DecisionOutcome;
import com.karp.yakraina.client.widgets.Emoji;
import com.karp.yakraina.client.widgets.StoryOutcome;

public class FinalStageView extends View {

	private static FinalStageViewUiBinder uiBinder = GWT.create(FinalStageViewUiBinder.class);

	interface FinalStageViewUiBinder extends UiBinder<Widget, FinalStageView> {
	}

	@UiField
	Image backImage;

	@UiField
	HTMLPanel barPanel;

	@UiField
	HTMLPanel mainPanel;

	final private List<Widget> barElements = new ArrayList<>();
	final private List<DecisionOutcome> mainElements = new ArrayList<>();
	final private List<DecisionOutcomeJs> data = new ArrayList<>();

	private StoryOutcome storyOutcome;
	private boolean allMessagesRead = false;

	public FinalStageView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		ColorThemeChangeEvent.fire("themeDark");

		mainElements.clear();
		barElements.clear();
		data.clear();

		for (DecisionOutcomeJs outcome : GameSession.get().getActiveStoryResults()) {
			if (outcome.hasText() && !outcome.getText().isEmpty()) {

				final Emoji barPanelElement = new Emoji(outcome);
				barPanelElement.addStyleName("hidden");

				barPanelElement.addClickHandler(new ClickHandler() {

					int index = data.size();

					@Override
					public void onClick(ClickEvent event) {
						if (allMessagesRead) {
							mainPanel.getElement().getStyle().clearWidth();
							if (backImage.getStyleName().contains("fadeIn"))
								backImage.removeStyleName("fadeIn");
							
							if (!backImage.getStyleName().contains("fadeOut"))
								backImage.addStyleName("fadeOut");
							storyOutcome.hide();

							for (int i = 0; i < mainElements.size(); i++) {
								DecisionOutcome outcome = mainElements.get(i);
								if (outcome.isFirst()) {
									barElements.get(i).removeStyleName("hidden");
									outcome.setPosition(0);
								}
							}

							barElements.get(index).addStyleName("hidden");
							Scheduler.get().scheduleDeferred(() -> Scheduler.get().scheduleFixedDelay(() -> {
								mainElements.get(index).setPosition(1);
								return false;
							}, 500));

						}
					}
				});
				barElements.add(barPanelElement);

				final DecisionOutcome mainPanelElement = new DecisionOutcome(outcome);
				mainPanelElement.addClickHandler(new ClickHandler() {

					int index = data.size();

					@Override
					public void onClick(ClickEvent event) {

						DecisionOutcome targetOutcomeView = mainElements.get(index);
						if (targetOutcomeView.isFirst()) {

							barElements.get(index).removeStyleName("hidden");

							if (!allMessagesRead) {
								for (int i = 0; i < 3; i++)
									if ((index + 1 + i) < mainElements.size())
										mainElements.get(index + 1 + i).setPosition(i + 1);

								Scheduler.get().scheduleDeferred(() -> Scheduler.get().scheduleFixedDelay(() -> {
									targetOutcomeView.setPosition(0);
									return false;
								}, 200));

								if (index == data.size() - 1) {
									allMessagesRead = true;
									showStoryOutcome();
								}
							} else {
								Scheduler.get().scheduleDeferred(() -> Scheduler.get().scheduleFixedDelay(() -> {
									targetOutcomeView.setPosition(0);
									showStoryOutcome();
									return false;
								}, 200));
							}
						}

					}
				});
				mainElements.add(mainPanelElement);

				data.add(outcome);
			}
		}

		for (int i = 0; i < data.size(); i++) {
			barPanel.add(barElements.get(i));
			mainPanel.add(mainElements.get(i));
		}

		storyOutcome = new StoryOutcome();
		storyOutcome.addClickHandler(event -> StoryEndEvent.fire());

		mainPanel.add(storyOutcome);

		storyOutcome.getSatisfiedCondition()
				.ifPresent(condition -> backImage.setUrl(UriUtils.fromTrustedString("images/stories/"
						+ GameSession.get().getActiveStory().getKey() + "/points_" + condition.getBound() + ".svg")));

		super.onLoad();

		if (data.isEmpty())
			Scheduler.get().scheduleDeferred(() -> Scheduler.get().scheduleFixedDelay(() -> {
				showStoryOutcome();
				return false;
			}, 200));
		else
			Scheduler.get().scheduleDeferred(() -> Scheduler.get().scheduleFixedDelay(() -> {
				for (int i = 0; i < (data.size() < 3 ? data.size() : 3); i++)
					mainElements.get(i).setPosition(i + 1);
				return false;
			}, 200));
	}

	private void showStoryOutcome() {
		Scheduler.get().scheduleFixedDelay(() -> {
			mainPanel.getElement().getStyle().setWidth(90, Unit.PCT);
			return false;
		}, 1000);
		
		if (!backImage.getStyleName().contains("fadeIn"))
			backImage.addStyleName("fadeIn");

		storyOutcome.show();
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub

	}

}
