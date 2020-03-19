package org.me.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import org.me.controller.ContollerStack;
import org.me.controller.Controllable;
import org.me.service.LanguageService;
import org.me.service.menu.MenuActiveService;
import org.me.service.menu.MenuService;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolController implements Initializable, Controllable<MenuController> {
	
	private MenuController cParent;
	
	@FXML Menu root;
	@FXML javafx.scene.control.MenuItem typescript;
	@FXML javafx.scene.control.MenuItem javascript;
	@FXML javafx.scene.control.MenuItem java;
	
	public ToolController() {
		ContollerStack.register(ToolController.class, this);
		setParentController(ContollerStack.instance(MenuController.class));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LanguageService langs = cParent.getParentController().getLanguage();
		MenuActiveService mm = cParent.getManager();

		root.textProperty().bindBidirectional(langs.property(MenuService.Tools.__NAME__.token()));
		typescript.textProperty().bindBidirectional(langs.property(MenuService.Tools.GENERATE_TYPESCRIPT.token()));
		javascript.textProperty().bindBidirectional(langs.property(MenuService.Tools.GENERATE_JAVASCRIPT.token()));
		java.textProperty().bindBidirectional(langs.property(MenuService.Tools.GENERATE_JAVA.token()));

//		root.disableProperty().bindBidirectional(mm.property(MenuService.Tools.__NAME__));
		typescript.disableProperty().bindBidirectional(mm.property(MenuService.Tools.GENERATE_TYPESCRIPT));
		javascript.disableProperty().bindBidirectional(mm.property(MenuService.Tools.GENERATE_JAVASCRIPT));
		java.disableProperty().bindBidirectional(mm.property(MenuService.Tools.GENERATE_JAVA));
	}
	
	@Override
	public void setParentController(MenuController controller) {
		this.cParent = controller;
	}
	
	@Override
	public MenuController getParentController() {
		return cParent;
	}

}
