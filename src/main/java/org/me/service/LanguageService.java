package org.me.service;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import org.me.entity.JsonManager;

import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LanguageService {
	
	private static final List<String> SUPPORTED = Arrays.asList("ru");
	
	private String lang;
	private JsonManager dict;
	private Map<String, Property<String>> properties;
	
	public LanguageService() {
		lang = "ru";
		dict = new JsonManager();
		properties = new HashMap<>();
		try {
			dict.getJSEngine().eval("var dict = {}");
			for (String lang: SUPPORTED) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(getClass().getResourceAsStream(String.format("/lang/%s.json", lang))));
				StringBuilder sb = new StringBuilder();
				String line;
				try {
					while ((line = br.readLine()) != null)
						sb.append(line);
					dict.getJSEngine().eval(String.format("dict['%s'] = %s", lang, sb.toString()));
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (ScriptException e) {
			e.printStackTrace();
			// It's impossible
		}
	}
	
	public String token(String tokenKey) {
		try {
			return (String) dict.getJSEngine().eval(String.format("dict['%s'].%s", lang, tokenKey));
		} catch (ScriptException e) {
			e.printStackTrace();
			return tokenKey;
		}
	}

	public Property<String> property(String token) {
		Property<String> found = properties.get(token);
		if (found == null) {
			found = new SimpleStringProperty(token(token));
			properties.put(token, found);
		}
		return found;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) throws UnsupportedLanguageException {
		if (!SUPPORTED.contains(lang))
			throw new UnsupportedLanguageException(lang, SUPPORTED);
		this.lang = lang;
		for (Map.Entry<String, Property<String>> entry: properties.entrySet())
			entry.getValue().setValue(token(entry.getKey()));
	}
	
}
