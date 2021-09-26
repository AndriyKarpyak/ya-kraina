package com.karp.yakraina.client.views.stages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.DecisionStageJs;
import com.karp.yakraina.client.model.story.OptionJs;
import com.karp.yakraina.client.views.View;
import com.karp.yakraina.client.widgets.MatteButton;

public class DecisionStageView extends View {

	private static DecisionStageViewUiBinder uiBinder = GWT.create(DecisionStageViewUiBinder.class);

	interface DecisionStageViewUiBinder extends UiBinder<Widget, DecisionStageView> {
	}

	@UiField
	HTML text;
	
	@UiField
	HTML background;

	@UiField
	HTMLPanel optionsPanel;

	@UiField
	MatteButton button_Accept;

	private DecisionStageJs stageData;

	private OptionJs selectedOption;
	

	public DecisionStageView(DecisionStageJs stage) {
		super();
		this.stageData = stage;

		initWidget(uiBinder.createAndBindUi(this));
		
		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
		button_Accept.setVisible(false);
	}
	

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {

		String bgUri = UriUtils.fromTrustedString("images/stories/" + GameSession.get().getActiveStory().getKey() + "/" + stageData.getKey() + ".svg").asString();
		background.getElement().getStyle().setBackgroundImage("url(\"" + bgUri + "\")");

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
				button_Accept.setVisible(true);
			});

			optionsPanel.add(optionButton);
		}
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {

		if (selectedOption != null) {
			
			addStyleName("fadeOut");
			
			Scheduler.get().scheduleFixedDelay(() -> {
				NextStageEvent.fire(selectedOption.getOutcomeJs().getNextStage());
				return false;
			}, 1600);
		} else {
			addStyleName("notSelected");
		}
	}

	@Override
	protected void onLoad() {
		ColorThemeChangeEvent.fire("themeLight");
		
		super.onLoad();
	}


}
