package com.karp.yakraina.client.model.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Cookies;
import com.karp.yakraina.client.events.FinalStageEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.events.PlayerCompletedStoryEvent;
import com.karp.yakraina.client.events.PlayerSelectedStoryEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.events.StoryStartEvent;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.model.story.StorySubResultJs;
import com.karp.yakraina.client.model.story.SummaryConditionJs;

public class GameSession implements PlayerSelectedStoryEvent.Handler, PlayerCompletedStoryEvent.Handler,
		FinalStageEvent.Handler, StoryEndEvent.Handler, NextStageEvent.Handler {

	private static final String COOKIE_KEY = "YA_KRAINA_GAME_SESSION_STATE";
	private static final long MILLISECONDS_IN_WEEK = 604800000;

	private static GameSession instance;

	private Date expires;
	private GameSessionJs js;
	private StoryJs activeStory;
	private Map<String, StageJs> stages;
	private StageJs activeStage;
	private List<String> passedStages;
	private StoryStateJs activeStoryState;

	private GameSession() {
		this(GameSessionJs.create());
	}

	private GameSession(GameSessionJs js) {
		this.js = js;
		this.expires = new Date((new Date()).getTime() + MILLISECONDS_IN_WEEK);

		PlayerSelectedStoryEvent.register(this);
		PlayerCompletedStoryEvent.register(this);
		NextStageEvent.register(this);

		StoryEndEvent.register(this);

		FinalStageEvent.register(this);
	}

	@Override
	public void onPlayerSelectedStory(PlayerSelectedStoryEvent event) {
		activeStory = event.getStory();

		if (js.hasStory(activeStory.getKey()))
			activeStoryState = js.getStory(activeStory.getKey());
		else {
			activeStoryState = StoryStateJs.create();
			activeStoryState.setId(activeStory.getKey());
			activeStoryState.setSummaryConditions(activeStory.getSummaryConditions());
		}

		StoryStartEvent.fire(activeStory, getActiveStoryInitialStage());
	}

	@Override
	public void onPlayerCompletedStory(PlayerCompletedStoryEvent event) {

		js.addStory(activeStoryState.getId(), activeStoryState);
//		store();
	}

	@Override
	public void onStoryEnd(StoryEndEvent event) {
		activeStoryState = null;
		activeStage = null;
		stages = null;
		passedStages = null;
	}

	@Override
	public void onFinalStage(FinalStageEvent event) {

	}

	@Override
	public void onNextStage(NextStageEvent event) {

		if (activeStage != null) {
			if (passedStages == null)
				passedStages = new ArrayList<>();

			passedStages.add(activeStage.getKey());
		}

		activeStage = event.getNextStage();

	}

	public final static GameSession get() {
		if (instance == null) {
			String restoredState = Cookies.getCookie(COOKIE_KEY);

			if (restoredState != null && !restoredState.isEmpty())
				instance = new GameSession((GameSessionJs) JsonUtils.safeEval(restoredState));
			else
				instance = new GameSession();
		}
		return instance;
	}

	public final StageJs getStage(String stageKey) {

		if (stages == null) {

			stages = new HashMap<String, StageJs>();

			for (int i = 0; i < activeStory.getStagesJs().length(); i++) {
				StageJs stage = activeStory.getStagesJs().get(i);

				stages.put(stage.getKey(), stage);
			}
		}

		return stages.get(stageKey.startsWith("stage_") ? stageKey : "stage_" + stageKey);
	}

	public final String getPlayerName() {
		return js.getPlayerName();
	}

	public final void setPlayerName(String playerName) {
		js.setPlayerName(playerName);
		store();
	}

	public final String getFBId() {
		return js.getFBId();
	}

	public final void setFBId(String fbId) {
		js.setFBId(fbId);
		store();
	}

	public final boolean isStoryCompleted(StoryJs story) {
		return js.hasStory(story.getKey());
	}

	public final boolean isStageCompleted(StageJs nextStage) {
		return passedStages.contains(nextStage.getKey());
	}

	public StageJs getActiveStoryInitialStage() {
		return getStage("stage_початок");
	}

	public StoryJs getActiveStory() {
		return activeStory;
	}

	public StoryStateJs getActiveStoryState() {
		return activeStoryState;
	}

	public final List<StorySubResultJs> getActiveStoryResults() {
		JsArray<StorySubResultJs> jsArray = activeStoryState.getResults();

		List<StorySubResultJs> results = new ArrayList<>();

		for (int i = 0; i < jsArray.length(); i++)
			results.add(jsArray.get(i));

		return results;
	}

	public final List<SummaryConditionJs> getActiveStorySummaryConditions() {
		JsArray<SummaryConditionJs> jsArray = activeStoryState.getSummaryConditions();

		List<SummaryConditionJs> results = new ArrayList<>();

		for (int i = 0; i < jsArray.length(); i++)
			results.add(jsArray.get(i));

		return results;
	}

	public final int getActiveStoryCollectedPoints() {

		if (activeStoryState != null)
			return activeStoryState.getCollectedPoints();

		return 0;
	}

	public final void clear() {
		js = GameSessionJs.create();
		Cookies.removeCookie(COOKIE_KEY);
	}

	@Override
	public String toString() {
		return "GameSession [" + JsonUtils.stringify(js) + "]";
	}

	public final boolean isEmpty() {
		return js.isEmpty();
	}

	private void store() {
		Cookies.setCookie(COOKIE_KEY, JsonUtils.stringify(js), expires);
	}

	public void addSubResult(StorySubResultJs storySubResultJs) {
		GWT.log("Add points: " + storySubResultJs.getPoints() + " | " + storySubResultJs.getText());
		activeStoryState.addResult(storySubResultJs);
	}

	public Optional<StoryStateJs> getCompletedStory(StoryJs story) {
		return Optional.ofNullable(js.getStory(story.getKey()));
	}

}
