package org.me.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.me.App;
import org.me.FileUtils;
import org.me.controller.ContollerStack;
import org.me.controller.Controllable;
import org.me.controller.activity.JsonEditorController;
import org.me.service.LanguageService;
import org.me.service.menu.MenuActiveService;
import org.me.service.menu.MenuService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileController implements Initializable, Controllable<MenuController> {
	
	private MenuController cParent;

	@FXML Menu root;
	@FXML MenuItem openNew;
	@FXML MenuItem createNew;
	@FXML MenuItem save;
	@FXML MenuItem saveAs;
	@FXML MenuItem export;
	@FXML MenuItem asArchive;
	@FXML MenuItem asFiles;
	@FXML MenuItem settings;
	@FXML MenuItem exit;
	
	public FileController() {
		ContollerStack.register(FileController.class, this);
		setParentController(ContollerStack.instance(MenuController.class));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LanguageService language = cParent.getParentController().getLanguage();
		MenuActiveService mm = cParent.getManager();

		root.textProperty().bindBidirectional(language.property(MenuService.File.__NAME__.token()));
		openNew.textProperty().bindBidirectional(language.property(MenuService.File.OPEN.token()));
		createNew.textProperty().bindBidirectional(language.property(MenuService.File.NEW.token()));
		save.textProperty().bindBidirectional(language.property(MenuService.File.SAVE.token()));
		saveAs.textProperty().bindBidirectional(language.property(MenuService.File.SAVE_AS.token()));
		export.textProperty().bindBidirectional(language.property(MenuService.File.EXPORT.token()));
		asArchive.textProperty().bindBidirectional(language.property(MenuService.File.EXPORT_ARCHIVE.token()));
		asFiles.textProperty().bindBidirectional(language.property(MenuService.File.EXPORT_FILES.token()));
		settings.textProperty().bindBidirectional(language.property(MenuService.File.SETTINGS.token()));
		exit.textProperty().bindBidirectional(language.property(MenuService.File.EXIT.token()));

		root.disableProperty().bindBidirectional(mm.property(MenuService.File.__NAME__));
		openNew.disableProperty().bindBidirectional(mm.property(MenuService.File.OPEN));
		createNew.disableProperty().bindBidirectional(mm.property(MenuService.File.NEW));
		save.disableProperty().bindBidirectional(mm.property(MenuService.File.SAVE));
		saveAs.disableProperty().bindBidirectional(mm.property(MenuService.File.SAVE_AS));
		export.disableProperty().bindBidirectional(mm.property(MenuService.File.EXPORT));
		asArchive.disableProperty().bindBidirectional(mm.property(MenuService.File.EXPORT_ARCHIVE));
		asFiles.disableProperty().bindBidirectional(mm.property(MenuService.File.EXPORT_FILES));
		settings.disableProperty().bindBidirectional(mm.property(MenuService.File.SETTINGS));
		exit.disableProperty().bindBidirectional(mm.property(MenuService.File.EXIT));
	}
	
	public void save() {
		try {
			cParent.getParentController().getCurrentContent().save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {
		cParent.getParentController().openProject();
	}
	
	public void create() {
	
	}
	
	public void saveAs() {
		final DirectoryChooser dc = new DirectoryChooser();
		LanguageService lang = cParent.getParentController().getLanguage();
		dc.titleProperty().bindBidirectional(lang.property("window.saveAs.title"));
		dc.setInitialDirectory(new File("C:\\"));
		File dest = dc.showDialog(App.getStage());
		if (dest != null)
			FileUtils.saveFiles(dest, ((JsonEditorController) cParent.getParentController().getCurrentContent()).getData());
	}
	
	public void openSettings() {
		Stage settingModal = new Stage();
		FXMLLoader loader = new FXMLLoader();
		try {
			Parent settingsContent = loader.load(getClass().getResourceAsStream("/view/activity/settings.fxml"));
			settingModal.setScene(new Scene(settingsContent));
			settingModal.initModality(Modality.APPLICATION_MODAL);
			settingModal.initOwner(App.getStage());
			settingModal.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
	
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
