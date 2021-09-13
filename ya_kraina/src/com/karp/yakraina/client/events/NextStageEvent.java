package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.karp.yakraina.client.model.story.StageJs;

public class NextStageEvent extends GwtEvent<NextStageEvent.Handler> {

	public interface Handler extends EventHandler {
		void onNextStage(NextStageEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire(final StageJs nextStage) {
		fire(new NextStageEvent(nextStage));
	}

	public static void fire(final NextStageEvent event) {
		EventBus.get().fireEvent(event);
	}

	private final StageJs nextStage;

	public NextStageEvent(final StageJs nextStage) {
		this.nextStage = nextStage;
	}

	public StageJs getNextStage() {
		return nextStage;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onNextStage(this);
	}

}
