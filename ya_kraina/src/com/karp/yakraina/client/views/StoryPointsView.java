package com.karp.yakraina.client.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.StoryEndEvent;
import com.karp.yakraina.client.model.session.GameSession;
import com.karp.yakraina.client.model.story.StoryJs;
import com.karp.yakraina.client.model.story.StorySubResultJs;
import com.karp.yakraina.client.model.story.SummaryConditionJs;
import com.karp.yakraina.client.widgets.MatteBytton;

public class StoryPointsView extends Composite {

	private static StoryPointsViewUiBinder uiBinder = GWT.create(StoryPointsViewUiBinder.class);

	interface StoryPointsViewUiBinder extends UiBinder<Widget, StoryPointsView> {
	}

	@UiField
	HTMLPanel pointsPanel;
	
	@UiField
	InlineLabel pointsText;
	
	@UiField
	HTML storyNameLabel;
	
	@UiField
	HTML storySummaryText;
	
	@UiField
	Image storyIcon;

	@UiField
	HTMLPanel singleSummaryPanel;
	
	@UiField
	Image emoji;
	
	@UiField
	HTML singleSummaryText;
	
	@UiField
	MatteBytton button_Next;
	
	@UiField
	MatteBytton button_Accept;
	
	
	private List<StorySubResultJs> messages = new ArrayList<>();

	private Iterator<StorySubResultJs> messagesIterator;

	public StoryPointsView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		pointsPanel.getElement().getStyle().setDisplay(Display.NONE);
		singleSummaryPanel.getElement().getStyle().setDisplay(Display.NONE);
		
		button_Next.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
		button_Accept.setHTML(SafeHtmlUtils.fromTrustedString("ДАЛІ &#x2192;"));
	}

	@UiHandler("button_Accept")
	public void onAcceptPlayerName(ClickEvent event) {
		StoryEndEvent.fire();
//		ShowNextViewEvent.fire(new StorySummariesView());
	}
	
	@UiHandler("button_Next")
	public void onNextSummaryMessage(ClickEvent event) {
		if (messagesIterator.hasNext())
			nextMessage(messagesIterator.next());
		else {
			singleSummaryPanel.getElement().getStyle().setDisplay(Display.NONE);
			pointsPanel.getElement().getStyle().clearDisplay();
		}
	}

	@Override
	protected void onLoad() {
		
		ColorThemeChangeEvent.fire("themeDark");
		
		StoryJs story = GameSession.get().getActiveStory();

		storyNameLabel.setText(story.getName());
		storyIcon.setUrl(UriUtils.fromTrustedString("images/story_selection/" + story.getName().replace(" ", "_").toLowerCase() + ".svg"));

		int points = 0;

		for (StorySubResultJs finalState : GameSession.get().getActiveStoryResults()) {

			if (finalState.getText() != null && !finalState.getText().isEmpty()) {
				messages.add(finalState);
			}
			
			points += finalState.getPoints();
		}

		pointsText.setText(String.valueOf(points));
		
		List<SummaryConditionJs> conditions = GameSession.get().getActiveStorySummaryConditions();

		for (int i = conditions.size() - 1; i >= 0; i--) {
			SummaryConditionJs condition = conditions.get(i);

			if (points > condition.getLowerBound() || (points == 0 && points == condition.getLowerBound())) {
				storySummaryText.setHTML(SafeHtmlUtils.fromTrustedString(condition.getText()));
				break;
			}

		}

		super.onLoad();
		
		messagesIterator = messages.iterator();
		
		if (messagesIterator.hasNext()) 
			nextMessage(messagesIterator.next());
		else
			pointsPanel.getElement().getStyle().clearDisplay();
	}
	
	private void nextMessage(StorySubResultJs result) {
		emoji.setUrl(UriUtils.fromTrustedString("images/emoji/" + result.getEmoji() + ".svg"));
		singleSummaryPanel.getElement().getStyle().clearDisplay();
		singleSummaryText.setHTML(result.getText());
	}

}
