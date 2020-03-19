package org.me.controller.activity;

import javafx.fxml.Initializable;
import org.me.controller.ContollerStack;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
	
	public SettingsController() {
		ContollerStack.register(SettingsController.class, this);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
}
