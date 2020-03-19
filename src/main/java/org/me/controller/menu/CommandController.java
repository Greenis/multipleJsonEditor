package org.me.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.me.controller.ContollerStack;
import org.me.controller.Controllable;
import org.me.controller.activity.JsonEditorController;
import org.me.service.LanguageService;
import org.me.service.menu.MenuActiveService;
import org.me.service.menu.MenuService;

import java.net.URL;
import java.util.ResourceBundle;

public class CommandController implements Initializable, Controllable<MenuController> {
	
	private MenuController cParent;
	
	@FXML Menu root;
	@FXML MenuItem addColumn;
	@FXML MenuItem addGroup;
	@FXML MenuItem addWord;
	@FXML MenuItem delRow;
	@FXML MenuItem moveUp;
	@FXML MenuItem moveDown;
	
	public CommandController() {
		ContollerStack.register(CommandController.class, this);
		setParentController(ContollerStack.instance(MenuController.class));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LanguageService langs = cParent.getParentController().getLanguage();
		MenuActiveService mm = cParent.getManager();

		root.textProperty().bindBidirectional(langs.property(MenuService.Command.__NAME__.token()));
		addColumn.textProperty().bindBidirectional(langs.property(MenuService.Command.ADD_COLUMN.token()));
		addGroup.textProperty().bindBidirectional(langs.property(MenuService.Command.ADD_GROUP.token()));
		addWord.textProperty().bindBidirectional(langs.property(MenuService.Command.ADD_WORD.token()));
		delRow.textProperty().bindBidirectional(langs.property(MenuService.Command.REMOVE_ROW.token()));
		moveUp.textProperty().bindBidirectional(langs.property(MenuService.Command.MOVE_UP.token()));
		moveDown.textProperty().bindBidirectional(langs.property(MenuService.Command.MOVE_UP.token()));

		root.disableProperty().bindBidirectional(mm.property(MenuService.Command.__NAME__));
		addColumn.disableProperty().bindBidirectional(mm.property(MenuService.Command.ADD_COLUMN));
		addGroup.disableProperty().bindBidirectional(mm.property(MenuService.Command.ADD_GROUP));
		addWord.disableProperty().bindBidirectional(mm.property(MenuService.Command.ADD_WORD));
		delRow.disableProperty().bindBidirectional(mm.property(MenuService.Command.REMOVE_ROW));
		moveUp.disableProperty().bindBidirectional(mm.property(MenuService.Command.MOVE_UP));
		moveDown.disableProperty().bindBidirectional(mm.property(MenuService.Command.MOVE_UP));
	}
	
	public void addGroup() {
		((JsonEditorController) cParent.getParentController().getCurrentContent()).addGroup();
	}
	
	public void addNode() {
		((JsonEditorController) cParent.getParentController().getCurrentContent()).addNode();
	}
	
	public void addColumn() {
		((JsonEditorController) cParent.getParentController().getCurrentContent()).addColumn();
	}
	
	public void removeRow() {
		((JsonEditorController) cParent.getParentController().getCurrentContent()).removeRow();
	}
	
	public void moveUp() {
		((JsonEditorController) cParent.getParentController().getCurrentContent()).moveUp();
	}
	
	public void moveDown() {
		((JsonEditorController) cParent.getParentController().getCurrentContent()).moveDown();
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
