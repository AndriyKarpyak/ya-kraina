package com.karp.yakraina.client.model.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.karp.yakraina.client.events.FinalStageEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.events.PlayerCompletedStoryEvent;
import com.karp.yakraina.client.events.PlayerSelectedStoryEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.events.StoryStartEvent;
import com.karp.yakraina.client.model.story.DecisionOutcomeJs;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.model.story.SummaryConditionJs;

public class GameSession implements PlayerSelectedStoryEvent.Handler, PlayerCompletedStoryEvent.Handler,
		FinalStageEvent.Handler, StoryEndEvent.Handler, NextStageEvent.Handler {

	private static GameSession instance;

	private GameSessionJs js;
	private List<String> cssResources;

	private GameSession(GameSessionJs js) {
		this.js = js;
		this.cssResources = new ArrayList<>();

		PlayerSelectedStoryEvent.register(this);
		PlayerCompletedStoryEvent.register(this);
		NextStageEvent.register(this);

		StoryEndEvent.register(this);

		FinalStageEvent.register(this);
	}

	public final static GameSession get() {
		if (instance == null) {
			String sessionJs = GameSessionJs.restore();

			if (sessionJs != null && !sessionJs.isEmpty())
				instance = new GameSession((GameSessionJs) JsonUtils.safeEval(sessionJs));
			else
				instance = new GameSession(GameSessionJs.create());
		}
		return instance;
	}

	@Override
	public void onPlayerSelectedStory(PlayerSelectedStoryEvent event) {

		js.startStory((StoryStateJs) event.getStory());
		js.store();

		StoryStartEvent.fire(event.getStory(), getActiveStoryInitialStage());
	}

	@Override
	public void onPlayerCompletedStory(PlayerCompletedStoryEvent event) {

	}

	@Override
	public void onStoryEnd(StoryEndEvent event) {
		js.completeStory();
		js.store();
	}

	@Override
	public void onFinalStage(FinalStageEvent event) {

	}

	@Override
	public void onNextStage(NextStageEvent event) {

		if (js.getActiveStory().hasActiveStage())
			if (!event.getNextStage().getDontMemorisePreviosStage())
				js.getActiveStory().addPathEntry(js.getActiveStory().getActiveStage().getKey());

		js.getActiveStory().setActiveStage(event.getNextStage());

		js.store();
	}

	public final StageJs getStage(String stageKey) {

		return js.getActiveStory().getStage(stageKey);
	}

	public final String getPlayerName() {
		return js.getPlayerName();
	}

	public final void setPlayerName(String playerName) {
		js.setPlayerName(playerName);
		js.store();
	}

	public final String getFBId() {
		return js.getFBId();
	}

	public final void setFBId(String fbId) {
		js.setFBId(fbId);
		js.store();
	}

	public final boolean isStoryCompleted(StoryJs story) {
		return js.hasStoryInCompleted(story.getKey());
	}

	public final boolean isStageCompleted(StageJs stage) {
		return js.getActiveStory().getUserPath().join().contains(stage.getKey());
	}

	public StageJs getActiveStoryInitialStage() {
		return getStage("stage_початок");
	}

	public StoryStateJs getActiveStory() {
		return js.getActiveStory();
	}

	public final List<DecisionOutcomeJs> getActiveStoryResults() {
		JsArray<DecisionOutcomeJs> jsArray = js.getActiveStory().getResults();

		List<DecisionOutcomeJs> results = new ArrayList<>();

		for (int i = 0; i < jsArray.length(); i++)
			results.add(jsArray.get(i));

		return results;
	}

	public final List<SummaryConditionJs> getActiveStorySummaryConditions() {
		JsArray<SummaryConditionJs> jsArray = js.getActiveStory().getSummaryConditions();

		List<SummaryConditionJs> results = new ArrayList<>();

		for (int i = 0; i < jsArray.length(); i++)
			results.add(jsArray.get(i));

		return results;
	}

	public final int getActiveStoryCollectedPoints() {

		if (js.getActiveStory() != null)
			return js.getActiveStory().getCollectedPoints();

		return 0;
	}

	public final void discardActiveStory() {

		if (js.hasActiveStory()) {
			js.deleteActiveStory();
			js.store();
		}
	}

	public final void clear() {
		js = GameSessionJs.create();
		js.store();
	}

	@Override
	public String toString() {
		return JsonUtils.stringify(js);
	}

	public final boolean isEmpty() {
		return js.isEmpty();
	}

	public void addSubResult(DecisionOutcomeJs storySubResultJs) {
		js.getActiveStory().addResult(storySubResultJs);
	}

	public Optional<StoryStateJs> getCompletedStory(StoryJs story) {
		return Optional.ofNullable(js.getStoryFromCompleted(story.getKey()));
	}

	public boolean isCssAdded(String name) {
		if (cssResources.contains(name))
			return true;

		cssResources.add(name);
		return false;
	}

}
