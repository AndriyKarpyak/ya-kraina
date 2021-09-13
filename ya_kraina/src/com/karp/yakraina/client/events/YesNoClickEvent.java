package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class YesNoClickEvent extends GwtEvent<YesNoClickEvent.Handler> {

	public interface HasYesNoClickEventHandlers extends EventHandler {
		void addYesNoClickEventHandler(YesNoClickEvent.Handler handler);
	}

	public interface Handler extends EventHandler {
		void onClick(YesNoClickEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final boolean isYesClicked;

	public YesNoClickEvent(final boolean isYesClicked) {
		this.isYesClicked = isYesClicked;
	}

	public boolean isYesClicked() {
		return isYesClicked;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onClick(this);
	}

}
