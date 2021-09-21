package com.karp.yakraina.client.views.stages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.OptionJs;
import com.karp.yakraina.client.model.story.OptionsStageJs;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.views.View;
import com.karp.yakraina.client.widgets.MatteButton;

public class OptionsStageView extends View {

	private static OptionsStageViewUiBinder uiBinder = GWT.create(OptionsStageViewUiBinder.class);

	interface OptionsStageViewUiBinder extends UiBinder<Widget, OptionsStageView> {
	}

	@UiField
	HTML background;

	@UiField
	Image emoji;

	@UiField
	HTML text;

	@UiField
	HTMLPanel optionsPanel;

	@UiField
	MatteButton button_Accept;

	private OptionsStageJs stageData;

	public OptionsStageView(OptionsStageJs stageData) {
		super();
		this.stageData = stageData;

		initWidget(uiBinder.createAndBindUi(this));

		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {

		boolean allCompleted = true;

		JsArray<OptionJs> options = stageData.getOptionsJs();
		for (int i = 0; i < options.length(); i++) {
			final OptionJs subStory = options.get(i);

			Button optionButton = new Button(subStory.getText());

			StageJs nextStage = subStory.getOutcomeJs().getNextStage();
			if (!GameSession.get().isStageCompleted(nextStage)) {
				allCompleted = false;
				optionButton.addClickHandler(event -> {
					NextStageEvent.fire(nextStage);
				});
			} else
				optionButton.setEnabled(false);

			optionsPanel.add(optionButton);
		}

		button_Accept.setEnabled(allCompleted);
		button_Accept.setVisible(allCompleted);
	}

	@Override
	protected void onLoad() {
		ColorThemeChangeEvent.fire("themeLight");

		String bgUri = UriUtils.fromTrustedString(
				"images/stories/" + GameSession.get().getActiveStory().getKey() + "/" + stageData.getKey() + ".svg")
				.asString();
		background.getElement().getStyle().setBackgroundImage("url(\"" + bgUri + "\")");

		emoji.setUrl(UriUtils.fromTrustedString("images/emoji/" + stageData.getEmoji() + ".svg"));
		text.setHTML(SafeHtmlUtils.fromTrustedString(stageData.getText()));

		super.onLoad();
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		NextStageEvent.fire(stageData.getOutcomeJs().getNextStage());
	}

}
