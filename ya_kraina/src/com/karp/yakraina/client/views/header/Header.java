package com.karp.yakraina.client.views.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.karp.yakraina.client.events.ColorThemeChangeEvent;
import com.karp.yakraina.client.events.ShowNextViewEvent;
import com.karp.yakraina.client.views.AboutProjectView;
import com.karp.yakraina.client.views.AboutGameView;
import com.karp.yakraina.client.views.WelcomeView;

public class Header extends Composite {

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	public Header() {
		initWidget(uiBinder.createAndBindUi(this));

		ColorThemeChangeEvent.register(new ColorThemeChangeEvent.Handler() {

			@Override
			public void onColorThemeChanged(ColorThemeChangeEvent event) {

				String themeName = event.getThemeName();

				if (themeName.equals("themeDark"))
					logo.setUrl(UriUtils.fromTrustedString("images/ya_kraina_header_logo_white.svg").asString());
				else if (themeName.equals("themeLight"))
					logo.setUrl(UriUtils.fromTrustedString("images/ya_kraina_header_logo_dark.svg").asString());
			}
		});
	}

	@UiField
	Image logo;

//	@UiField
//	InlineLabel menuHome;

	@UiField
	InlineLabel menuAboutGame;

	@UiField
	InlineLabel menuAboutUs;

	@Override
	protected void onLoad() {
		super.onLoad();

	}

	@UiHandler("logo")
	public void onLogoClicked(ClickEvent event) {
		ShowNextViewEvent.fire(new WelcomeView());
	}

//	@UiHandler("menuHome")
//	public void onMenuHomeClicked(ClickEvent event) {
//		ShowNextViewEvent.fire(new WelcomeView());
//	}

	@UiHandler("menuAboutGame")
	public void onMenuAboutGameClicked(ClickEvent event) {
		ShowNextViewEvent.fire(new AboutGameView());
	}

	@UiHandler("menuAboutUs")
	public void onMenuAboutUsClicked(ClickEvent event) {
		ShowNextViewEvent.fire(new AboutProjectView());
	}
}
