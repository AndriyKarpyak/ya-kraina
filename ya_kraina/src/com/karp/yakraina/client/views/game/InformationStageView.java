package com.karp.yakraina.client.views.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.InformationStageJs;
import com.karp.yakraina.client.widgets.MatteBytton;

public class InformationStageView extends Composite {

	private static InformationStageViewUiBinder uiBinder = GWT.create(InformationStageViewUiBinder.class);

	interface InformationStageViewUiBinder extends UiBinder<Widget, InformationStageView> {
	}

	@UiField
	Image emoji;
	
	@UiField
	HTML text;

	@UiField
	MatteBytton button_Accept;

	private InformationStageJs stageData;

	public InformationStageView(InformationStageJs stage) {
		stageData = stage;

		initWidget(uiBinder.createAndBindUi(this));
		
		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		NextStageEvent.fire(stageData.getOutcomeJs().getNextStage());
	}

	@Override
	protected void onLoad() {
		
		String bgUri = UriUtils.fromTrustedString("images/stories/" + GameSession.get().getActiveStoryState().getId() + "/" + stageData.getKey() + ".svg").asString();
		getElement().getStyle().setBackgroundImage("url(\"" + bgUri + "\")");
		
		emoji.setUrl(UriUtils.fromTrustedString("images/emoji/" + stageData.getEmoji() + ".svg"));
		
		ColorThemeChangeEvent.fire("themeLight");
		
		text.setHTML(SafeHtmlUtils.fromTrustedString(stageData.getText()));

		super.onLoad();
	}

}
