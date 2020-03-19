package org.me;

import org.me.entity.JsonManager;

import javax.script.ScriptException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static void readDirectory(JsonManager data, File dir) {
		App.getStage().setTitle(String.format("%s: \"%s\"", App.title, dir.getAbsolutePath()));
		try {
			data.reset();
			List<String> langs = new ArrayList<>();
			data.getJSEngine().eval("var dict = {};");
			for (File langFile : dir.listFiles()) {
				if (langFile.getName().toLowerCase().endsWith(".json")) {
					String lang = langFile.getName().substring(0, langFile.getName().length() - 5);

					StringBuilder sb = new StringBuilder();
					BufferedReader in = new BufferedReader(new FileReader(langFile));
					String line;
					while ((line = in.readLine()) != null) {
						sb.append(line);
						sb.append('\n');
					}
					in.close();

					try {
						data.getJSEngine().eval(String.format("dict['%s'] = %s", lang, sb.toString()));
					} catch (ScriptException e1) {
						e1.printStackTrace();
					}
					langs.add(lang);
				}
			}
			data.getLangs().clear();
			data.getLangs().addAll(langs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			data.parseFiles();
		}
	}

	public static void saveFiles(File dir, JsonManager data) {
		for (String lang : data.getLangs()) {
			try {
				BufferedWriter br = new BufferedWriter(new FileWriter(new File(dir, lang + ".json")));
				br.write((String) data.getJSEngine().eval(String.format("JSON.stringify(dict['%s'], null, '\t')", lang)));
				br.flush();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
