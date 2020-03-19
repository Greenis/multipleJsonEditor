package org.me.controller;

import javafx.fxml.Initializable;

public interface Controllable<T extends Initializable> {
	
	void setParentController(T controller);
	
	T getParentController();

}
