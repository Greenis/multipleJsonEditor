package org.me.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TreeItem;
import org.me.entity.JsonManager;
import org.me.ui.table.Line;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class JsonReader extends JsonDeserializer<OrderedJson> {
	
	private ObjectMapper mapper;
	
	private TreeItem<Line> tree;
	private JsonManager data;
	private String lang;
	
	private InputStream stream;
	private String file;
	
	protected JsonReader() {
	}
	
	public JsonReader(TreeItem<Line> tree, JsonManager data, String lang, String file) {
		this.tree = tree;
		this.data = data;
		this.lang = lang;
		this.file = file;
		this.stream = null;
		mapper = new ObjectMapper();
		init();
	}
	
	public JsonReader(TreeItem<Line> tree, JsonManager data, String lang, InputStream stream) {
		this.tree = tree;
		this.data = data;
		this.lang = lang;
		this.file = null;
		this.stream = stream;
		mapper = new ObjectMapper();
		init();
	}
	
	private void init() {
		mapper.setInjectableValues(new InjectableValues.Std()
			.addValue("this", this));
		
		mapper.reader(new InjectableValues.Std()
				.addValue("this", this))
					.withType(OrderedJson.class);
	}
	
	@Override
	public OrderedJson deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonReader _this = (JsonReader) ctxt.findInjectableValue("this", null, null);
		if ((_this != null) && (ctxt.getAttribute("recursion") == null)) {
			ctxt.setAttribute("recursion", true);
			_this.deserialize(p, ctxt);
			ctxt.setAttribute("recursion", null);
		} else {
//			p.nextToken();// Скобка объекта
			readMap(p, data.getRoot(), tree);
		}
		return null;
	}
	
	public void read() throws IOException {
		if (file != null)
			mapper.readValue(new File(file), OrderedJson.class);
		else
			mapper.readValue(stream, OrderedJson.class);
	}
	
	private void readMap(JsonParser p, Line line, TreeItem<Line> tree) throws IOException {
		createOrValidate(data, lang);
		while (p.nextToken() != JsonToken.END_OBJECT) {
			String name = p.getText();
			
			JsonToken valueToken = p.nextToken();
			if (valueToken == JsonToken.START_OBJECT) {
				Line last = line.getChildGroups().get(name);
				FilteredList<TreeItem<Line>> filtered = tree.getChildren().filtered(v -> v.getValue().getName().equals(name));
				TreeItem<Line> node = filtered.size() > 0 ? filtered.get(0) : null;
				if (last == null) {
					last = new Line(name, line, true, line.getJsvm());
					createOrValidate(data, lang, last.getPath().split("."));
					line.getChildGroups().put(name, last);
					node = new TreeItem<>(last);
					tree.getChildren().add(node);
				}
				readMap(p, last, node);
			} else {
				Line last = line.getChildWords().get(name);
				if (last == null) {
					last = new Line(name, line, false, line.getJsvm());
					line.getChildWords().put(name, last);
					tree.getChildren().add(new TreeItem<>(last));
				}
				String value = p.getText();
				last.setLang(lang, value);
				
			}
		}
	}
	
	private void createOrValidate(JsonManager data, String lang, String ... path) {
		StringBuilder _sPath = new StringBuilder();
		if (path == null)
			path = new String[0];
		try {
			if (!(boolean) data.getJSEngine().eval(String.format("dict['%s'] !== undefined", lang)))
				data.getJSEngine().eval(String.format("dict['%s'] = {}", lang));
			for (int i = 0; i < path.length; i++) {
				_sPath.append('.').append(path[i]);
				if (!(boolean) data.getJSEngine().eval(String.format("dict['%s']%s !== undefined", lang, _sPath.toString())))
					data.getJSEngine().eval(String.format("dict['%s']%s = {}", lang, _sPath.toString()));
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
}
