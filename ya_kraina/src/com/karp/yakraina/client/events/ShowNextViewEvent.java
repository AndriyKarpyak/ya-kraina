package com.karp.yakraina.client.events;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class ShowNextViewEvent extends GwtEvent<ShowNextViewEvent.Handler> {

	public interface Handler extends EventHandler {
		void onShowNextViewTrigger(ShowNextViewEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire(final Widget nextView) {
		fire(new ShowNextViewEvent(nextView));
	}

	public static void fire(final ShowNextViewEvent event) {
		Scheduler.get().scheduleFixedDelay(() -> {
			EventBus.get().fireEvent(event);
			return false;
		}, 100);
	}

	private final Widget nextView;

	public ShowNextViewEvent(final Widget nextView) {
		this.nextView = nextView;
	}

	public Widget getNextView() {
		return nextView;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onShowNextViewTrigger(this);
	}

}
