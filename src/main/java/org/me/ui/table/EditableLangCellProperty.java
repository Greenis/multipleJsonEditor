package org.me.ui.table;

import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class EditableLangCellProperty extends StringProperty {

	private Line src;
	private String lang;
	
	private final List<ChangeListener> listeners_01;
	private final List<InvalidationListener> listeners_02;

	public EditableLangCellProperty(Line src, String lang) {
		this.src = src;
		this.lang = lang;

		listeners_01 = new ArrayList<>();
		listeners_02 = new ArrayList<>();
	}

	@Override
	public void bind(ObservableValue<? extends String> observable) {

	}
	@Override
	public void unbind() {

	}
	@Override
	public boolean isBound() {
		return false;
	}

	@Override
	public Object getBean() {
		return null;
	}
	@Override
	public String getName() {
		return String.format("%s[%s]", src.getPath(), lang);
	}

	@Override
	public String get() {
		return src.getLang(lang);
	}
	@Override
	public void set(String value) {
		String old = get();
		src.setLang(lang, value);

		notifyListeners1(old, value);
		notifyListeners2();
	}

	@Override
	public void addListener(ChangeListener<? super String> listener) {
		listeners_01.add(listener);
	}
	@Override
	public void removeListener(ChangeListener<? super String> listener) {
		listeners_01.remove(listener);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		listeners_02.add(listener);
	}
	@Override
	public void removeListener(InvalidationListener listener) {
		listeners_02.remove(listener);
	}

	private void notifyListeners1(String _old, String _new) {
		for (ChangeListener<? super String> listener : listeners_01)
			listener.changed(this, _old, _new);
	}

	private void notifyListeners2() {
		for (InvalidationListener listener : listeners_02)
			listener.invalidated(this);
	}

}
