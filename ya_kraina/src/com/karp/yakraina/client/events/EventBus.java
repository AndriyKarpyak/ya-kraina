package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;

@Singleton
public class EventBus extends SimpleEventBus {

	private EventBus() {
	};

	private static EventBus instance;

	public static EventBus get() {
		if (instance == null)
			instance = new EventBus();
		return instance;
	}
}
