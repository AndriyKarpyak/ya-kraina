package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class StoryEndEvent extends GwtEvent<StoryEndEvent.Handler> {

	public interface Handler extends EventHandler {
		void onStoryEnd(StoryEndEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire() {
		fire(new StoryEndEvent());
	}

	public static void fire(final StoryEndEvent event) {
		EventBus.get().fireEvent(event);
	}

	public StoryEndEvent() {
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onStoryEnd(this);
	}

}
