package org.me.ui.table;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.KeyCode;

public class EditableLangCell extends TreeTableCell<Line, String> {

	private TextField textField;
	private String oldValue;

	public EditableLangCell() {}
	
	@Override
	public void startEdit() {
		super.startEdit();
		oldValue = getText();
		if (textField == null)
			createTextField();

		setGraphic(textField);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		textField.selectAll();
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null)
					textField.setText(getString());
				setGraphic(textField);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			} else {
				setText(getString());
				setContentDisplay(ContentDisplay.TEXT_ONLY);
			}
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		if (textField != null)
			textField.setText(oldValue);
		setText(String.valueOf(getItem()));
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
		textField.setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ENTER)
				commitEdit(textField.getText());
			else if (t.getCode() == KeyCode.ESCAPE)
				cancelEdit();
		});
		textField.requestFocus();
	}

	private String getString() {
		return getItem() == null ? "" : getItem();
	}

}
