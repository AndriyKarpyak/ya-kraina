package com.karp.yakraina.client.views.stages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.NextStageEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.InformationStageJs;
import com.karp.yakraina.client.views.View;
import com.karp.yakraina.client.widgets.MatteButton;

public class InformationStageView extends View {

	private static InformationStageViewUiBinder uiBinder = GWT.create(InformationStageViewUiBinder.class);

	interface InformationStageViewUiBinder extends UiBinder<Widget, InformationStageView> {
	}

	@UiField
	HTML background;

	@UiField
	Image emoji;

	@UiField
	HTML text;

	@UiField
	MatteButton button_Accept;

	private InformationStageJs stageData;

	public InformationStageView(InformationStageJs stage) {
		stageData = stage;

		initWidget(uiBinder.createAndBindUi(this));

		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
		button_Accept.setVisible(false);
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		addStyleName("fadeOut");
		
		Scheduler.get().scheduleFixedDelay(() -> {
			NextStageEvent.fire(stageData.getOutcomeJs().getNextStage());
			return false;
		}, 1600);

	}

	@Override
	protected void onLoad() {

		String bgUri = UriUtils.fromTrustedString(
				"images/stories/" + GameSession.get().getActiveStory().getKey() + "/" + stageData.getKey() + ".svg")
				.asString();
		background.getElement().getStyle().setBackgroundImage("url(\"" + bgUri + "\")");

		emoji.setUrl(UriUtils.fromTrustedString("images/emoji/" + stageData.getEmoji() + ".svg"));

		ColorThemeChangeEvent.fire("themeLight");

		text.setHTML(SafeHtmlUtils.fromTrustedString(stageData.getText()));

		super.onLoad();
	}

	@Override
	protected String getName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void onShow() {
		button_Accept.setVisible(true);
	}

}
