package com.karp.yakraina.client.messaging;

import java.util.logging.Level;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ErrorMessageDisplay extends PopupPanel {

	private static ErrorMessageDisplayUiBinder uiBinder = GWT.create(ErrorMessageDisplayUiBinder.class);

	interface ErrorMessageDisplayUiBinder extends UiBinder<Widget, ErrorMessageDisplay> {
	}

	@UiField
	AbsolutePanel message_Panel;
	@UiField
	Button button_Ok;

	private final Level level;
	private final SafeHtml message;
	private final Throwable reason;
	private final Command closeCallback;

	private ErrorMessageDisplay(final Level level, final SafeHtml message, final Throwable reason,
			final Command closeCallback) {

		this.level = level;
		this.message = message;
		this.reason = reason;
		this.closeCallback = closeCallback;

		setWidget(uiBinder.createAndBindUi(this));

		message_Panel.getElement().setInnerSafeHtml(message);
	}

	@UiHandler("button_Ok")
	public void onAcceptPlayerName(final ClickEvent event) {

		if (closeCallback != null)
			closeCallback.execute();

		hide();
	}

	public static final ErrorMessageDisplay create(final Level level, final String message) {
		return new ErrorMessageDisplay(level, SafeHtmlUtils.fromTrustedString(message), null, null);
	}

	public static final ErrorMessageDisplay create(final Level level, final String message,
			final Command closeCallback) {
		return new ErrorMessageDisplay(level, SafeHtmlUtils.fromTrustedString(message), null, closeCallback);
	}

	public static final ErrorMessageDisplay create(final Level level, final String message, final Throwable reason,
			final Command closeCallback) {
		return new ErrorMessageDisplay(level, SafeHtmlUtils.fromTrustedString(message), reason, closeCallback);
	}

	public static final ErrorMessageDisplay create(final Level level, final SafeHtml message) {
		return new ErrorMessageDisplay(level, message, null, null);
	}

	public static final ErrorMessageDisplay create(final Level level, final SafeHtml message,
			final Command closeCallback) {
		return new ErrorMessageDisplay(level, message, null, closeCallback);
	}

	public static final ErrorMessageDisplay create(final Level level, final SafeHtml message, final Throwable reason,
			final Command closeCallback) {
		return new ErrorMessageDisplay(level, message, reason, closeCallback);
	}

}
