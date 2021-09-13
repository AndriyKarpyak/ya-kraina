package com.karp.yakraina.client.views;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ViewWrapper extends Composite implements HasWidgets {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, ViewWrapper> {
	}

	@UiField
	Panel root;

	public ViewWrapper() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void add(Widget w) {
		root.add(w);
	}

	@Override
	public void clear() {
		root.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return root.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return root.remove(w);
	}

}
