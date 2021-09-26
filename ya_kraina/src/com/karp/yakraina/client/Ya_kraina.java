package com.karp.yakraina.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.events.StoryStartEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.session.StoryStateJs;
import com.karp.yakraina.client.model.story.DecisionStageJs;
import com.karp.yakraina.client.model.story.InformationStageJs;
import com.karp.yakraina.client.model.story.OptionsStageJs;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.YesNoOptionsStageJs;
import com.karp.yakraina.client.views.StoriesListView;
import com.karp.yakraina.client.views.ViewWrapper;
import com.karp.yakraina.client.views.WelcomeView;
import com.karp.yakraina.client.views.footer.Footer;
import com.karp.yakraina.client.views.header.Header;
import com.karp.yakraina.client.views.stages.DecisionStageView;
import com.karp.yakraina.client.views.stages.FinalStageView;
import com.karp.yakraina.client.views.stages.InformationStageView;
import com.karp.yakraina.client.views.stages.OptionsStageView;
import com.karp.yakraina.client.views.stages.YesNoOptionsStageView;

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

			GWT.log(nextStage.getKey() + ": " + nextStage.getText());

			switch (nextStage.getType()) {
			case DECISION:
				ShowNextViewEvent.fire(new DecisionStageView((DecisionStageJs) nextStage));
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
			case FINAL:
				ShowNextViewEvent.fire(new FinalStageView());
				break;
			default:
				GWT.log("Not supported stage type: " + nextStage.getType().name());
				break;
			}
		});

		StoryEndEvent.register(event -> ShowNextViewEvent.fire(new StoriesListView()));

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
