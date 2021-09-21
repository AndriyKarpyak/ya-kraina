package com.karp.yakraina.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.model.story.DecisionOutcomeJs;

public class DecisionOutcome extends Composite implements HasClickHandlers {

	private static DecisionOutcomeUiBinder uiBinder = GWT.create(DecisionOutcomeUiBinder.class);

	interface DecisionOutcomeUiBinder extends UiBinder<Widget, DecisionOutcome> {
	}

	public DecisionOutcome() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Emoji emoji;

	@UiField
	HTML text;

	@UiField
	MatteButton button_Next;

	private DecisionOutcomeJs outcome;

	private int position = -1;

	public DecisionOutcome(final DecisionOutcomeJs outcome) {
		this();
		this.outcome = outcome;
		setPosition(-1);
		
		button_Next.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@Override
	protected void onLoad() {
		if (outcome != null) {
			emoji.setUrl(outcome);
			text.setHTML(SafeHtmlUtils.fromTrustedString(outcome.getText()));
		}
		super.onLoad();
	}

	@Override
	public HandlerRegistration addClickHandler(final ClickHandler handler) {
		return button_Next.addClickHandler(handler);
	}

	public void setPosition(int position) {

		removeStyleName("pos" + this.position);

		this.position = position;

		addStyleName("pos" + this.position);
	}

	public boolean isFirst() {
		return position == 1;
	}

}
