package org.me.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import org.me.App;
import org.me.controller.activity.JsonEditorController;
import org.me.controller.activity.WelcomeController;
import org.me.controller.menu.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.me.entity.Project;
import org.me.entity.ProjectFile;
import org.me.service.LanguageService;
import org.me.service.menu.MenuActiveService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GlobalController extends Observable implements Initializable {
	
	@FXML
	MenuBar menuBar;
	@FXML
	private MenuController menuBarController;
	
	@FXML
	TabPane content;
	private Map<Tab, ContentableController> contentController;
	
	private LanguageService language;
	
	public GlobalController() {
		ContollerStack.register(GlobalController.class, this);
		language = new LanguageService();
		contentController = new HashMap<>();
	}

	public void initialize(URL location, ResourceBundle resources) {
		content.prefHeightProperty().bind(App.getStage().heightProperty());
		content.getSelectionModel().selectedItemProperty().addListener((tab, oldV, newV) -> contentController.get(tab.getValue()).choose());
		if (true) {
			Tab welcomeTab = new Tab("Добро пожаловать!");
			FXMLLoader loader = new FXMLLoader();
			try {
				Parent welcomeContent = loader.load(getClass().getResourceAsStream("/view/activity/welcome.fxml"));
				welcomeTab.setContent(welcomeContent);
				this.contentController.put(welcomeTab, loader.getController());
				this.content.getTabs().add(welcomeTab);
				this.content.getSelectionModel().select(welcomeTab);
			} catch (Exception e) {
			
			}
		}
	}

	public LanguageService getLanguage() {
		return language;
	}

	public ContentableController getCurrentContent() {
		return contentController.get(content.getSelectionModel().getSelectedItem());
	}
	
	public MenuController getMenuBarController() {
		return menuBarController;
	}
	
	public MenuActiveService getMenuActiveService() {
		return menuBarController.getManager();
	}
	
	public void createProject() {
	
	}
	
	public void openProject() {
		FileChooser dc = new FileChooser();
		dc.titleProperty().bindBidirectional(language.property("window.open.title"));
		dc.setInitialDirectory(new File("D:/natareev-aa/.projects/csm/CSM/src/main/webapp/resources/lang"));
		dc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("File based", ".pejf", ".pexf"));
		dc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Archive based", ".peja", ".pexa"));
		File projectFile = dc.showOpenDialog(App.getStage());
		Project opening = new Project();
		if (projectFile != null) {
			opening.isArchive(projectFile.getName().endsWith("a"));
			Properties props = new Properties();
			ZipFile zip;
			ZipEntry zipFile;
			try {
				if (!opening.isArchive()) {
					props.load(new FileReader(projectFile));
					if (props.getProperty("name") == null)
						throw new InvalidPropertiesFormatException("Project should have property 'name'.");
				} else {
					zip = new ZipFile(projectFile);
					zipFile = zip.getEntry(".pejf");
					props.load(zip.getInputStream(zipFile));
					if (props.getProperty("name") == null)
						throw new InvalidPropertiesFormatException("Project should have property 'name'.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			opening.setName(props.getProperty("name"));
			opening.setPath(projectFile.getAbsolutePath());
			List<String> orderColumn = Arrays.asList(props.getProperty("order").split(","));
			TreeMap<Integer, ProjectFile> _orderExtraMap = new TreeMap<>();
			for (String prop : props.stringPropertyNames())
				if (prop.startsWith("file_")) {
					String type = prop.substring(5);
					_orderExtraMap.put(orderColumn.indexOf(type), new ProjectFile(opening, type, props.getProperty(prop)));
				}
			for (ProjectFile file: _orderExtraMap.values())
				opening.getFiles().add(file);
		}
		if (projectFile.getName().endsWith(".pejf")) {
			Tab newTab = new Tab(opening.getName());
			FXMLLoader loader = new FXMLLoader();
//			loader.setRoot(newTab);
			try {
				Parent content = loader.load(getClass().getResourceAsStream("/view/activity/json-editor.fxml"));
				newTab.setContent(content);
				this.contentController.put(newTab, loader.getController());
				this.content.getTabs().add(newTab);
				this.content.getSelectionModel().select(newTab);
				((JsonEditorController) loader.getController()).open(opening);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
