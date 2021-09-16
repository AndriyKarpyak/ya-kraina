package com.karp.yakraina.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.FinalStageEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.events.PlayerCompletedStoryEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.events.StoryStartEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.session.StoryStateJs;
import com.karp.yakraina.client.model.story.DecisionStageJs;
import com.karp.yakraina.client.model.story.FinalStageJs;
import com.karp.yakraina.client.model.story.InformationStageJs;
import com.karp.yakraina.client.model.story.OptionsStageJs;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.YesNoOptionsStageJs;
import com.karp.yakraina.client.views.StorySummariesView;
import com.karp.yakraina.client.views.ViewWrapper;
import com.karp.yakraina.client.views.WelcomeView;
import com.karp.yakraina.client.views.footer.Footer;
import com.karp.yakraina.client.views.game.DecisionWithIntroStageView;
import com.karp.yakraina.client.views.game.DecisionWithQuestionStageView;
import com.karp.yakraina.client.views.game.InformationStageView;
import com.karp.yakraina.client.views.game.OptionsStageView;
import com.karp.yakraina.client.views.game.StorySelectionView;
import com.karp.yakraina.client.views.game.YesNoOptionsStageView;
import com.karp.yakraina.client.views.header.Header;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ya_kraina implements EntryPoint {

	final ViewWrapper mainView = new ViewWrapper();

	/**
	 * 
	 * 
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		GameSession.get();

		// --------------------------------------------------------------------
		// Event handling

		ShowNextViewEvent.register((final ShowNextViewEvent event) -> {

			mainView.clear();
			mainView.add(event.getNextView());
		});

		StoryStartEvent.register(event -> {

//			GWT.log(event.getStory().getKey() + ": " + event.getStory().getName());

			NextStageEvent.fire(event.getInitialStage());
		});

		NextStageEvent.register((final NextStageEvent event) -> {

			final StageJs nextStage = event.getNextStage();

//			GWT.log(nextStage.getKey() + ": " + nextStage.getText());

			switch (nextStage.getType()) {
			case DECISION_INTRO:
				ShowNextViewEvent.fire(new DecisionWithIntroStageView((DecisionStageJs) nextStage));
				break;
			case DECISION_QUESTION:
				ShowNextViewEvent.fire(new DecisionWithQuestionStageView((DecisionStageJs) nextStage));
				break;
			case OPTIONS:
				ShowNextViewEvent.fire(new OptionsStageView((OptionsStageJs) nextStage));
				break;
			case YES_NO_OPTIONS:
				ShowNextViewEvent.fire(new YesNoOptionsStageView((YesNoOptionsStageJs) nextStage));
				break;
			case INFORMATION:
				ShowNextViewEvent.fire(new InformationStageView((InformationStageJs) nextStage));
				break;
			case SUB_SUMMARY:
				ShowNextViewEvent.fire(new InformationStageView((InformationStageJs) nextStage));
				break;
			case FINAL:
				FinalStageEvent.fire((FinalStageJs) nextStage);
				break;
			default:
				GWT.log("Not supported stage type: " + nextStage.getType().name());
				break;
			}
		});

		FinalStageEvent.register(new FinalStageEvent.Handler() {

			@Override
			public void onFinalStage(final FinalStageEvent event) {

				PlayerCompletedStoryEvent.fire();
			}
		});

		PlayerCompletedStoryEvent.register(event -> ShowNextViewEvent.fire(new StorySummariesView()));

		StoryEndEvent.register(event -> ShowNextViewEvent.fire(new StorySelectionView()));

		ColorThemeChangeEvent.register(new ColorThemeChangeEvent.Handler() {

			private String currentThemeName = "themeDark";

			@Override
			public void onColorThemeChanged(ColorThemeChangeEvent event) {

				String newThemeName = event.getThemeName();

				RootPanel rootPanel = RootPanel.get("root");

				rootPanel.removeStyleName(currentThemeName);
				rootPanel.addStyleName(newThemeName);

				currentThemeName = newThemeName;

			}
		});

		// --------------------------------------------------------------------
		// UI set up

		final RootPanel rootPanel = RootPanel.get("root");

		rootPanel.add(new Header());

		rootPanel.add(mainView);

		rootPanel.add(new Footer());

		
		StoryStateJs activeStory = GameSession.get().getActiveStory();
		
		if (activeStory != null && activeStory.getActiveStage() != null) {
			NextStageEvent.fire(new NextStageEvent(activeStory.getActiveStage()));
		} else {
			mainView.add(new WelcomeView());
		}

	}
}
