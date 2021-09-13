package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.karp.yakraina.client.model.story.StoryJs;

public class PlayerSelectedStoryEvent extends GwtEvent<PlayerSelectedStoryEvent.Handler> {

	public interface Handler extends EventHandler {
		void onPlayerSelectedStory(PlayerSelectedStoryEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire(final StoryJs scenario) {
		fire(new PlayerSelectedStoryEvent(scenario));
	}

	public static void fire(final PlayerSelectedStoryEvent event) {
		EventBus.get().fireEvent(event);
	}

	private final StoryJs story;

	public PlayerSelectedStoryEvent(final StoryJs scenario) {
		this.story = scenario;
	}

	public StoryJs getStory() {
		return story;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onPlayerSelectedStory(this);
	}

}
