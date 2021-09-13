package com.karp.yakraina.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class ColorThemeChangeEvent extends GwtEvent<ColorThemeChangeEvent.Handler> {

	public interface Handler extends EventHandler {
		void onColorThemeChanged(ColorThemeChangeEvent event);
	}

	public static final Type<Handler> TYPE = new Type<Handler>();

	public static HandlerRegistration register(final Handler handler) {
		return EventBus.get().addHandler(TYPE, handler);
	}

	public static void fire(final String themeName) {
		fire(new ColorThemeChangeEvent(themeName));
	}

	public static void fire(final ColorThemeChangeEvent event) {
		EventBus.get().fireEvent(event);
	}

	private final String themeName;

	public ColorThemeChangeEvent(final String themeName) {
		this.themeName = themeName;
	}

	public String getThemeName() {
		return themeName;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onColorThemeChanged(this);
	}

}
