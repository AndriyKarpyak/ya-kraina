package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.StoryJs;

public class StoryStartEvent extends GwtEvent<StoryStartEvent.Handler> {

	public interface Handler extends EventHandler {
		void onStoryStart(StoryStartEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire(final StoryJs scenario, final StageJs initialStage) {
		fire(new StoryStartEvent(scenario, initialStage));
	}

	public static void fire(final StoryStartEvent event) {
		EventBus.get().fireEvent(event);
	}

	private final StoryJs story;
	private final StageJs initialStage;

	public StoryStartEvent(final StoryJs story, final StageJs initialStage) {
		this.story = story;
		this.initialStage = initialStage;
	}

	public StoryJs getStory() {
		return story;
	}

	public StageJs getInitialStage() {
		return initialStage;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onStoryStart(this);
	}

}
