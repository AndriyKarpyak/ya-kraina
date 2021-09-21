package com.karp.yakraina.client.views.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.OptionJs;
import com.karp.yakraina.client.model.story.DecisionStageJs;
import com.karp.yakraina.client.widgets.MatteBytton;

public class DecisionWithQuestionStageView extends Composite {

	private static DecisionWithQuestionStageViewUiBinder uiBinder = GWT.create(DecisionWithQuestionStageViewUiBinder.class);

	interface DecisionWithQuestionStageViewUiBinder extends UiBinder<Widget, DecisionWithQuestionStageView> {
	}

	@UiField
	HTML text;

	@UiField
	HTMLPanel optionsPanel;

	@UiField
	MatteBytton button_Accept;

	private DecisionStageJs stageData;

	private OptionJs selectedOption;

	public DecisionWithQuestionStageView(DecisionStageJs stage) {
		this.stageData = stage;

		initWidget(uiBinder.createAndBindUi(this));
		
		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {

		if (selectedOption != null)
			NextStageEvent.fire(selectedOption.getOutcomeJs().getNextStage());
	}

	@Override
	protected void onLoad() {
		
		String bgUri = UriUtils.fromTrustedString("images/stories/" + GameSession.get().getActiveStory().getKey() + "/" + stageData.getKey() + ".svg").asString();
		getElement().getStyle().setBackgroundImage("url(\"" + bgUri + "\")");
		
		ColorThemeChangeEvent.fire("themeLight");

		text.setHTML(SafeHtmlUtils.fromTrustedString(stageData.getText()));

		JsArray<OptionJs> options = stageData.getOptions();

		for (int i = 0; i < options.length(); i++) {
			final OptionJs option = options.get(i);

			RadioButton optionButton = new RadioButton("DecisionOptions", option.getText());
			Element radio = optionButton.getElement().getFirstChildElement();
			radio.setId("opt" + i);
			Element label = radio.getNextSiblingElement();
			label.setAttribute("for", "opt" + i);

			optionButton.addValueChangeHandler(event -> {
				selectedOption = option;
			});

			optionsPanel.add(optionButton);
		}

		super.onLoad();
	}

}
