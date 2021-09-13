package com.karp.yakraina.client.widgets;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.auth.events.HasFBStatusChangeHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FBLoginButton extends Composite implements HasFBStatusChangeHandler {

	private static FBLoginButtonUiBinder uiBinder = GWT.create(FBLoginButtonUiBinder.class);

	interface FBLoginButtonUiBinder extends UiBinder<Widget, FBLoginButton> {
	}

	public FBLoginButton() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addFBStatusChangeHandler(FBStatusChangeHandler handler) {
		return FBGWT.Auth.addFBStatusChangeHandler(handler);
	}

}
