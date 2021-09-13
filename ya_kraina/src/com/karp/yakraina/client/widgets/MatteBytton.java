package com.karp.yakraina.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.HasSafeHtml;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class MatteBytton extends Composite implements HasText, HasSafeHtml, HasClickHandlers {

	private static MatteByttonUiBinder uiBinder = GWT.create(MatteByttonUiBinder.class);

	interface MatteByttonUiBinder extends UiBinder<Widget, MatteBytton> {
	}

	@UiConstructor
	public MatteBytton() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}
	
	@Override
	public void setHTML(SafeHtml html) {
		button.setHTML(html);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return button.addClickHandler(handler);
	}

	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}



}
