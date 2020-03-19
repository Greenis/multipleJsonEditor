package org.me.controller.activity;

import javafx.fxml.Initializable;
import org.me.controller.ContentableController;
import org.me.controller.ContollerStack;
import org.me.controller.Controllable;
import org.me.controller.GlobalController;
import org.me.entity.Project;
import org.me.service.menu.MenuActiveService;
import org.me.service.menu.MenuService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable, Controllable<GlobalController>, ContentableController {
	
	private GlobalController cParent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cParent = ContollerStack.instance(GlobalController.class);
		choose();
	}
	
	@Override
	public void setParentController(GlobalController controller) {
		this.cParent = controller;
	}
	
	@Override
	public GlobalController getParentController() {
		return cParent;
	}
	
	@Override
	public void choose() {
		MenuActiveService mm = cParent.getMenuActiveService();
		mm.disable(MenuService.Tools.GENERATE_JAVA);
		mm.disable(MenuService.Tools.GENERATE_JAVASCRIPT);
		mm.disable(MenuService.Tools.GENERATE_TYPESCRIPT);
		
		mm.disable(MenuService.File.SAVE);
		mm.disable(MenuService.File.SAVE_AS);
		mm.disable(MenuService.File.EXPORT);
		
		mm.disable(MenuService.Command.MOVE_DOWN);
		mm.disable(MenuService.Command.MOVE_UP);
		mm.disable(MenuService.Command.ADD_COLUMN);
		mm.disable(MenuService.Command.ADD_GROUP);
		mm.disable(MenuService.Command.ADD_WORD);
		mm.disable(MenuService.Command.REMOVE_ROW);
	}
	
	@Override
	public void open(Project project) {

	}

	@Override
	public Project project() {
		return null;
	}
	
	@Override
	public void save() throws IOException {
	
	}
	
	@Override
	public void close() {

	}
}
