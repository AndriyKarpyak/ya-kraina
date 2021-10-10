package com.karp.yakraina.client.model;

import com.karp.yakraina.client._3rdparty.YamlUtils;
import com.karp.yakraina.client.stories.SummariesClientBundle;

public class GameSummaries {

	private final GameSummariesJs js;

	private GameSummaries() {
		js = YamlUtils.safeEval(SummariesClientBundle.INSTANCE.summariesTextResource().getText());
	}

	public GameSummariesJs getJs() {
		return js;
	}

	private static GameSummaries instance;

	public static GameSummaries get() {
		if (instance == null)
			instance = new GameSummaries();
		return instance;
	}

}
