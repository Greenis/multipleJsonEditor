package org.me.entity;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.me.ui.table.Line;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonManager {

	private ScriptEngine jsvm;
	private Line root;

	public JsonManager() {
		reset();
	}

	public void reset() {
		jsvm = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			jsvm.eval("var dict = {}");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		root = new Line("/", null, true, jsvm);
	}

	public ScriptEngine getJSEngine() {
		return jsvm;
	}

	public List<String> getLangs() {
		try {
			List<String> result = new ArrayList<>();
			for (Object obj: ((ScriptObjectMirror) jsvm.eval("Object.keys(dict)")).values())
				result.add((String) obj);
			return result;
		} catch (ScriptException e) {
			e.printStackTrace();
			return new ArrayList<>(0);
		}
	}

	public Line getRoot() {
		return root;
	}

	public void deleteRow(String path) {
		for (String lang : getLangs()) {
			try {
				jsvm.eval(String.format("delete dict['%s'].%s", lang, path));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void parseFiles() {
		for (String lang : getLangs()) {
			try {
				ScriptObjectMirror flatDict = (ScriptObjectMirror) jsvm.eval(String.format("dict['%s']", lang));
				for (Map.Entry<String, Object> elO : flatDict.entrySet())
					rParse(root, (AbstractMap.SimpleImmutableEntry<String, Object>) elO);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
	}

	private void rParse(Line parrent, AbstractMap.SimpleImmutableEntry<String, Object> token) {
		if (token.getValue() instanceof String)
			parrent.getChildWords().putIfAbsent(token.getKey(), new Line(token.getKey(), parrent, false, jsvm));
		else if (token.getValue() instanceof ScriptObjectMirror)
			for (Map.Entry<String, Object> elO : ((ScriptObjectMirror) token.getValue()).entrySet()) {
				AbstractMap.SimpleImmutableEntry<String, Object> el = (AbstractMap.SimpleImmutableEntry<String, Object>) elO;
				parrent.getChildGroups().putIfAbsent(token.getKey(), new Line(token.getKey(), parrent, true, jsvm));
				rParse(parrent.getChildGroups().get(token.getKey()), el);
			}
	}

}
