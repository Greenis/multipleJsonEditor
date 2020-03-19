package org.me.controller.activity;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.me.controller.ContentableController;
import org.me.controller.ContollerStack;
import org.me.controller.Controllable;
import org.me.controller.GlobalController;
import org.me.entity.JsonManager;
import org.me.ui.table.Line;
import org.me.entity.Project;
import org.me.entity.ProjectFile;
import org.me.serialize.JsonReader;
import org.me.serialize.JsonWriter;
import org.me.service.menu.MenuActiveService;
import org.me.service.menu.MenuService;
import org.me.ui.table.EditableLangCell;
import org.me.ui.table.EditableLangCellProperty;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.ZipOutputStream;

public class JsonEditorController implements Initializable, Controllable<GlobalController>, ContentableController {
	
	private GlobalController cParent;
	private Project project;
	private JsonManager data;

	@FXML
	TreeTableView table;
	
	private final Property<Boolean> changes;
	private final Property<Boolean> selected;
	
	private Image attribute = new Image(getClass().getResourceAsStream("/icons/pencil.png"));
	private TreeItem<Line> rootItem;
	
	public JsonEditorController() {
		setParentController(ContollerStack.instance(GlobalController.class));
		changes = new SimpleBooleanProperty(false);
		selected = new SimpleBooleanProperty(false);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@Override
	public void setParentController(GlobalController controller) {
		this.cParent = controller;
	}
	
	@Override
	public GlobalController getParentController() {
		return cParent;
	}
	
	private void rebuildTable() {
		highlightWord(rootItem);
		
		table.setEditable(true);
		table.setShowRoot(false);
		TreeTableColumn<Line, String> nodeColumn = new TreeTableColumn<>();
		nodeColumn.setSortable(false);
		nodeColumn.textProperty().bindBidirectional(cParent.getLanguage().property("editor.json.node"));
		nodeColumn.setPrefWidth(200);
		table.getColumns().clear();
		table.getColumns().add(nodeColumn);
		for (String lang : data.getLangs())
			addColumn(lang);
		
		nodeColumn.setCellFactory(tc -> new EditableLangCell());
		nodeColumn.setCellValueFactory(p -> new EditableLangCellProperty(p.getValue().getValue(), "__name__"));
		
		table.setRoot(rootItem);
	}
	
	private void addColumn(String name) {
		TreeTableColumn<Line, String> col = new TreeTableColumn<>(name);
		
		col.setSortable(false);
		col.setCellValueFactory(p ->
				p.getValue().getValue().isGroup()
						? new ReadOnlyStringWrapper("-")
						: new EditableLangCellProperty(p.getValue().getValue(), name));
		col.setCellFactory(tc -> new EditableLangCell());
		col.setOnEditCommit(event -> event.getRowValue().getValue().setLang(event.getTableColumn().getText(), event.getNewValue()));
		col.setPrefWidth(120);
		table.getColumns().add(col);
	}
	
	private void highlightWord(TreeItem<Line> vertex) {
		if (!vertex.getValue().isGroup())
			vertex.setGraphic(new ImageView(attribute));
		else
			for (TreeItem<Line> child: vertex.getChildren())
				highlightWord(child);
	}

	@Override
	public void choose() {
		MenuActiveService mm = cParent.getMenuActiveService();
//		mm.disable(MenuService.Tools.GENERATE_JAVA);
//		mm.disable(MenuService.Tools.GENERATE_JAVASCRIPT);
//		mm.disable(MenuService.Tools.GENERATE_TYPESCRIPT);
		
		mm.enable(changes.getValue());
		mm.enable(MenuService.File.SAVE_AS);
		mm.enable(MenuService.File.EXPORT);
		
		mm.disable(MenuService.Command.MOVE_DOWN);
		mm.disable(MenuService.Command.MOVE_UP);
		mm.enable(MenuService.Command.ADD_COLUMN);
		mm.enable(MenuService.Command.ADD_GROUP);
		mm.enable(MenuService.Command.ADD_WORD);
		mm.enable(selected.getValue());
	}
	
	@Override
	public void open(Project project) {
		this.project = project;
		this.data = new JsonManager();
		this.rootItem = new TreeItem<>(new Line("/", null, true, data.getJSEngine()));

		try {
			for (ProjectFile file: project.getFiles()) {
				new JsonReader(rootItem, data, file.getName(), file.getInputStream()).read();
			}
			cParent.getMenuActiveService().disable(MenuService.Command.MOVE_DOWN);
			cParent.getMenuActiveService().disable(MenuService.Command.MOVE_UP);
			cParent.getMenuActiveService().disable(MenuService.File.SAVE);
			rebuildTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Project project() {
		return null;
	}
	
	@Override
	public void save() throws IOException {
		if (!project.isArchive()) {
			for (ProjectFile file : project.getFiles()) {
				OutputStream out = file.getOutputStream();
				new JsonWriter(rootItem, file.getName(), out).write();
				out.flush();
				out.close();
			}
		} else {
			FileOutputStream zip = new FileOutputStream(project.getProjectFile());
			ZipOutputStream out = new ZipOutputStream(zip);
			for (ProjectFile file: project.getFiles()) {
				JsonWriter writer = new JsonWriter(rootItem, file.getName(), out);
				out.putNextEntry(file.getZipEntry());
				writer.write();
				out.closeEntry();
			}
			out.flush();
			out.close();
		}
	}
	
	@Override
	public void close() {
	}
	
	public JsonManager getData() {
		return data;
	}
	
	public Image getAttribute() {
		return attribute;
	}
	
	public void addGroup() {
		TreeTableView.TreeTableViewSelectionModel sm = table.getSelectionModel();
		TreeItem<Line> item;
		TreeItem<Line> parrent;
		if (sm.isEmpty()) {
			item = rootItem;
		} else {
			int ind = sm.getSelectedIndex();
			item = (TreeItem<Line>) sm.getModelItem(ind);
		}
		parrent = item.getParent();
		
		if (item.getValue().isGroup())
			_add(item, new Line("NewGroup", item.getValue(), true, data.getJSEngine()));
		else
			_add(parrent, new Line("NewGroup", parrent.getValue(), true, data.getJSEngine()));
	}
	
	public void addNode() {
		TreeTableView.TreeTableViewSelectionModel sm = table.getSelectionModel();
		TreeItem<Line> item;
		TreeItem<Line> parrent;
		if (sm.isEmpty()) {
			item = rootItem;
		} else {
			int ind = sm.getSelectedIndex();
			item = (TreeItem<Line>) sm.getModelItem(ind);
		}
		parrent = item.getParent();
		
		if (item.getValue().isGroup())
			_add(item, new Line("NewItem", item.getValue(), false, data.getJSEngine()));
		else
			_add(parrent, new Line("NewItem", parrent.getValue(), false, data.getJSEngine()));
	}
	
	public void addColumn() {
	
	}
	
	public void removeRow() {
		TreeTableView.TreeTableViewSelectionModel sm = table.getSelectionModel();
		
		if (sm.isEmpty())
			return;
		int ind = sm.getSelectedIndex();
		TreeItem<Line> item = (TreeItem<Line>) sm.getModelItem(ind);
		TreeItem<Line> parrent = item.getParent();
		
		data.deleteRow(item.getValue().getPath());
		
		parrent.getChildren().remove(item);
	}
	
	public void moveUp() {
	
	}
	
	public void moveDown() {
	
	}
	
	private void _add(TreeItem<Line> node, Line added) {
		TreeItem<Line> _gNode = new TreeItem<>(added);
		if (!added.isGroup())
			_gNode.setGraphic(new ImageView(attribute));
		node.getChildren().add(_gNode);
		if (added.isGroup()) {
			node.getValue().getChildGroups().putIfAbsent(added.getName(), added);
			added.update();
		}
		else
			node.getValue().getChildWords().putIfAbsent(added.getName(), added);
	}
	
}
