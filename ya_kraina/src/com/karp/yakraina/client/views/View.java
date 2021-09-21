package com.karp.yakraina.client.views;

import com.denormans.facebookgwt.gwtutil.client.js.JSFunction;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.user.client.ui.Composite;
import com.karp.yakraina.client.model.session.GameSession;

public abstract class View extends Composite {

	protected abstract String getName();

	protected abstract void onShow();

	private native JSFunction waitForResource(final LinkElement css) /*-{
		var self = this;
		css.onload = function() {
			self.@com.karp.yakraina.client.views.View::onCSSLoaded()();
		};
	}-*/;

	private void onCSSLoaded() {
		setVisible(true);
		onShow();
	}

	@Override
	protected void onLoad() {
		setVisible(false);
		
		if (!GameSession.get().isCssAdded(getName())) {

		LinkElement css = Document.get().createLinkElement();
		
		css.setType("text/css");
		css.setRel("stylesheet");
		css.setHref("css-views/" + getName() + ".css");
		
		waitForResource(css);
		
		Document.get().getHead().appendChild(css);
		} else {
			onCSSLoaded();
		}

		super.onLoad();
	}
}
