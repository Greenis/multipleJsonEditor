package org.me.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.me.controller.ContollerStack;
import org.me.controller.Controllable;
import org.me.controller.GlobalController;
import org.me.service.menu.MenuActiveService;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable, Controllable<GlobalController> {
	
	private GlobalController parentController;
	private MenuActiveService manager;
	
	@FXML
	MenuBar root;
	
	@FXML
	private Menu file;
	@FXML
	private FileController fileController;
	
	@FXML
	private Menu command;
	@FXML
	private CommandController commandController;
	
	@FXML
	private Menu tools;
	@FXML
	private ToolController toolsController;
	
	public MenuController() {
		ContollerStack.register(MenuController.class, this);
		setParentController(ContollerStack.instance(GlobalController.class));
		this.manager = new MenuActiveService();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@Override
	public void setParentController(GlobalController controller) {
		this.parentController = controller;
	}
	@Override
	public GlobalController getParentController() {
		return parentController;
	}

	public MenuActiveService getManager() {
		return manager;
	}
}
