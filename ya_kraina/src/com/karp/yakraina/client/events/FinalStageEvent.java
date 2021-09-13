package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.karp.yakraina.client.model.story.FinalStageJs;

public class FinalStageEvent extends GwtEvent<FinalStageEvent.Handler> {

	public interface Handler extends EventHandler {
		void onFinalStage(FinalStageEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire(final FinalStageJs finalStage) {
		fire(new FinalStageEvent(finalStage));
	}

	public static void fire(final FinalStageEvent event) {
		EventBus.get().fireEvent(event);
	}

	private final FinalStageJs finalStage;

	public FinalStageEvent(final FinalStageJs finalStage) {
		this.finalStage = finalStage;
	}

	public FinalStageJs getFinalStage() {
		return finalStage;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onFinalStage(this);
	}

}
