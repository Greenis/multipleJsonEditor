package org.me.ui.table;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.*;

public class Line {

	private boolean isGroup;

	private final Line parrent;
	private final ScriptEngine jsvm;

	private String name;

	private final Map<String, Line> childWords;
	private final Map<String, Line> childGroups;

	public Line(String name, Line parrent, boolean isGroup, ScriptEngine jsvm) {
		this.name = name;
		this.parrent = parrent;
		this.isGroup = isGroup;
		this.jsvm = jsvm;
		childWords = new HashMap<>();
		childGroups = new HashMap<>();
	}

	public boolean isGroup() {
		return isGroup;
	}

	public String getName() {
		return name;
	}

	public Map<String, Line> getChildGroups() {
		return childGroups;
	}
	public Map<String, Line> getChildWords() {
		return childWords;
	}

	public String getLang(String lang) {
		try {
			Object r;
			if (!lang.equals("__name__"))
				r = jsvm.eval(String.format("dict['%s'].%s", lang, getPath()));
			else
				r = name;
			if (r instanceof String)
				return (String) r;
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}
	public void setLang(String lang, String value) {
		if (value == null)
			return;
		try {
			if (!lang.equals("__name__"))
				jsvm.eval(String.format("dict['%s'].%s = '%s'", lang, getPath(), value.replaceAll("'", "\\\\'")));
			else
				setKey(value);
		} catch (ScriptException e) {
			try {
				if (lang.equals("__name__")) {
					List<String> langs = new ArrayList<>();
					for (Object __lang : ((ScriptObjectMirror) jsvm.eval("Object.keys(dict)")).values())
						langs.add((String) __lang);
					for (String __lang : langs)
						_constructPath(__lang);
				} else
					_constructPath(lang);
				if (!lang.equals("__name__"))
					jsvm.eval(String.format("dict['%s'].%s = '%s'", lang, getPath(), value.replaceAll("'", "\\\\'")));
				else
					setKey(value);
			} catch (ScriptException e1) {
				e.printStackTrace();
				e1.printStackTrace();
			}
		}
	}

	private void _constructPath(String lang) throws ScriptException {
		for (Line _partPath : getPathAsLine())
			if ((boolean) jsvm.eval(
					String.format("typeof dict['%s'].%s === 'undefined'", lang, _partPath.getPath()))
					&& _partPath.isGroup) {
				jsvm.eval(String.format("dict['%s'].%s = {}", lang, _partPath.getPath()));
			}
	}

	private void setKey(String newName) throws ScriptException {
		boolean found = false;
		for (String l : getChildWords().keySet())
			if (l.equals(newName)) found = true;
		if (!found)
			for (String l : getChildWords().keySet())
				if (l.equals(newName)) found = true;
		if (!found) {
			List<String> langs = new ArrayList<>();
			for (Object lang : ((ScriptObjectMirror) jsvm.eval("Object.keys(dict)")).values())
				langs.add((String) lang);
			for (String lang : langs) {
				String _path = getPath();
				String newPath = (!parrent.getPath().equals("") ? (parrent.getPath() + ".") : "") + newName;
				String copy = (String) jsvm.eval(String.format("JSON.stringify(dict['%s'].%s)", lang, _path));
				if (copy != null)
					jsvm.eval(String.format("delete dict['%s'].%s", lang, _path));
				else
					copy = isGroup ? "undefined" : "undefined";
				jsvm.eval(String.format("dict['%s'].%s = %s", lang, newPath, copy));
			}
			this.name = newName;
		}
	}

	public void update() {
		if (isGroup) {

		}
	}

	public String getPath() {
		Line cur = this;
		Stack<String> st = new Stack<>();
		while (cur.parrent != null) {
			st.push(cur.name);
			cur = cur.parrent;
		}
		if (!st.isEmpty() && st.peek().equals("/"))
			st.pop();
		StringBuilder sb = new StringBuilder();
		while (!st.isEmpty())
			sb.append(st.pop()).append(".");
		if (sb.length() != 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public ScriptEngine getJsvm() {
		return jsvm;
	}
	
	private List<Line> getPathAsLine() {
		Stack<Line> st = new Stack<>();
		Line cur = this;
		while (cur.parrent != null) {
			st.push(cur);
			cur = cur.parrent;
		}
		List<Line> result = new ArrayList<>(st.size());
		while (!st.isEmpty())
			result.add(st.pop());
		return result;
	}

	@Override
	public int hashCode() {
		return name.hashCode() * Boolean.valueOf(isGroup).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Line))
			return false;
		Line _o = (Line) obj;
		if (!name.equals(_o.name))
			return false;
		return isGroup == _o.isGroup;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
