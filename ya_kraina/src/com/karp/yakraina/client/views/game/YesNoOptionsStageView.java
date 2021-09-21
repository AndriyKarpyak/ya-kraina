package com.karp.yakraina.client.views.game;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.DecisionOutcomeJs;
import com.karp.yakraina.client.model.story.InformationStageJs;
import com.karp.yakraina.client.model.story.StageJs;
import com.karp.yakraina.client.model.story.YesNoOptionJs;
import com.karp.yakraina.client.model.story.YesNoOptionsStageJs;
import com.karp.yakraina.client.widgets.MatteBytton;
import com.karp.yakraina.client.widgets.YesNoOptionButton;

public class YesNoOptionsStageView extends Composite {

	private static YesNoOptionsStageViewUiBinder uiBinder = GWT.create(YesNoOptionsStageViewUiBinder.class);

	interface YesNoOptionsStageViewUiBinder extends UiBinder<Widget, YesNoOptionsStageView> {
	}

	@UiField
	Image emoji;

	@UiField
	HTML text;

	@UiField
	HTMLPanel optionsPanel;

	@UiField
	MatteBytton button_Accept;

	private final YesNoOptionsStageJs stageData;

	public YesNoOptionsStageView(final YesNoOptionsStageJs stageData) {
		this.stageData = stageData;

		initWidget(uiBinder.createAndBindUi(this));

		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(final ClickEvent event) {
		NextStageEvent.fire(stageData.getOutcomeJs().getNextStage());
	}

	@Override
	protected void onLoad() {

		final String bgUri = UriUtils.fromTrustedString(
				"images/stories/" + GameSession.get().getActiveStory().getKey() + "/" + stageData.getKey() + ".svg")
				.asString();
		getElement().getStyle().setBackgroundImage("url(\"" + bgUri + "\")");

		emoji.setUrl(UriUtils.fromTrustedString("images/emoji/" + stageData.getEmoji() + ".svg"));
		text.setHTML(SafeHtmlUtils.fromTrustedString(stageData.getText()));

		boolean allCompleted = true;
		
		final JsArray<YesNoOptionJs> options = stageData.getOptionsJs();
		for (int i = 0; i < options.length(); i++) {
			final YesNoOptionJs option = options.get(i);

			final YesNoOptionButton optionButton = new YesNoOptionButton(option.getText());
			optionButton.setName(option.getText().hashCode()+"");

			final InformationStageJs yesStage = (InformationStageJs) option.getOutcomeJs().getYesStage();
			final DecisionOutcomeJs yesOutcome = (DecisionOutcomeJs) yesStage.getOutcomeJs().cast();
			
			final InformationStageJs noStage = (InformationStageJs) option.getOutcomeJs().getNoStage();
			final DecisionOutcomeJs noOutcome = (DecisionOutcomeJs) noStage.getOutcomeJs().cast();
			
			if (GameSession.get().isStageCompleted(yesStage) || GameSession.get().isStageCompleted(noStage)) {
				optionButton.setEnabled(false);
				optionButton.setYesSelected(GameSession.get().isStageCompleted(yesStage));
				optionButton.setAnsweredCorrectly(
						(GameSession.get().isStageCompleted(yesStage) && yesOutcome.getPoints() > 0) 
						|| (GameSession.get().isStageCompleted(noStage) && noOutcome.getPoints() > 0));
				optionsPanel.add(optionButton);
			} else {
				allCompleted = false;
				optionButton.addYesNoClickEventHandler(event -> NextStageEvent.fire(event.isYesClicked() ? yesStage : noStage));
				optionsPanel.add(optionButton);
			}
		}

		button_Accept.setEnabled(allCompleted);
		button_Accept.setVisible(allCompleted);

		super.onLoad();
	}

}
