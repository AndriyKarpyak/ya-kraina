package com.karp.yakraina.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.annotations.IsSafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.YesNoClickEvent;
import com.karp.yakraina.client.events.YesNoClickEvent.HasYesNoClickEventHandlers;

public class YesNoOptionButton extends Composite implements HasYesNoClickEventHandlers {

	private static YesNoOptionButtonUiBinder uiBinder = GWT.create(YesNoOptionButtonUiBinder.class);

	interface YesNoOptionButtonUiBinder extends UiBinder<Widget, YesNoOptionButton> {
	}

	public YesNoOptionButton() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public YesNoOptionButton(@IsSafeHtml String html) {
		this();
		text.setHTML(html);
	}

	@UiField
	HTML text;

	@UiField
	Button yes;

	@UiField
	Button no;

	@UiHandler("yes")
	void onYes(ClickEvent e) {
		fireEvent(new YesNoClickEvent(true));
	}

	@UiHandler("no")
	void onNo(ClickEvent e) {
		fireEvent(new YesNoClickEvent(false));
	}

	@Override
	public void addYesNoClickEventHandler(YesNoClickEvent.Handler handler) {
		addHandler(handler, YesNoClickEvent.TYPE);
	}

	public void setEnabled(boolean enabled) {

		if (enabled)
			getElement().removeAttribute("disabled");
		else {
			getElement().setAttribute("disabled", "");
		}

		yes.setVisible(enabled);
		no.setVisible(enabled);
		yes.setEnabled(enabled);
		no.setEnabled(enabled);
	}

	public void setYesSelected(boolean yesSelected) {

		if (yesSelected) {
			getElement().addClassName("yes");
			getElement().removeClassName("no");
		} else {
			getElement().addClassName("no");
			getElement().removeClassName("yes");
		}
	}

}
