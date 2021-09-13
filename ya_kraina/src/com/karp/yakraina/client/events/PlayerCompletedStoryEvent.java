package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class PlayerCompletedStoryEvent extends GwtEvent<PlayerCompletedStoryEvent.Handler> {

	public interface Handler extends EventHandler {
		void onPlayerCompletedStory(PlayerCompletedStoryEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire() {
		fire(new PlayerCompletedStoryEvent());
	}

	public static void fire(final PlayerCompletedStoryEvent event) {
		EventBus.get().fireEvent(event);
	}

	public PlayerCompletedStoryEvent() {

	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onPlayerCompletedStory(this);
	}

}
