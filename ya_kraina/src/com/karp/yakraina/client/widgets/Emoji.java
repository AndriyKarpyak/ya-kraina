package com.karp.yakraina.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.model.story.DecisionOutcomeJs;

public class Emoji extends Composite implements HasClickHandlers {

	private static EmojiUiBinder uiBinder = GWT.create(EmojiUiBinder.class);

	interface EmojiUiBinder extends UiBinder<Widget, Emoji> {
	}

	@UiConstructor
	public Emoji() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Image emoji;

	public Emoji(String url) {
		this();
		setUrl(url);
	}

	public Emoji(SafeUri url) {
		this();
		setUrl(url);
	}

	public Emoji(DecisionOutcomeJs outcome) {
		this();
		setUrl(outcome);
	}

	public void setUrl(DecisionOutcomeJs outcome) {
		emoji.setUrl(UriUtils.fromTrustedString("images/emoji/" + outcome.getEmoji() + ".svg"));
	}

	public void setUrl(SafeUri url) {
		emoji.setUrl(url);
	}

	public void setUrl(String url) {
		emoji.setUrl(url);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return emoji.addClickHandler(handler);
	}

}
