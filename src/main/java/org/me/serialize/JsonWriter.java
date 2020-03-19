package org.me.serialize;

import javafx.scene.control.TreeItem;
import org.me.ui.table.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;

public class JsonWriter {
	
	private final TreeItem<Line> tree;
	private final String lang;
	private final String file;
	private final OutputStream stream;
	
	public JsonWriter(TreeItem<Line> tree, String lang, String file) {
		this.tree = tree;
		this.lang = lang;
		this.file = file;
		this.stream = null;
	}
	
	public JsonWriter(TreeItem<Line> tree, String lang, OutputStream stream) {
		this.tree = tree;
		this.lang = lang;
		this.file = null;
		this.stream = stream;
	}
	
	public void write() throws FileNotFoundException {
		TabService tabService = new TabService();
		PrintWriter out = null;
		if (file != null)
			out = new PrintWriter(new File(file));
		else if (stream != null)
			out = new PrintWriter(stream);
		if (out == null)
			throw new FileNotFoundException();
		int intend = 1;
		Stack<Iterator<TreeItem<Line>>> stack = new Stack<>();
		
		Iterator<TreeItem<Line>> it = tree.getChildren().iterator();
		
		out.print("{\n");
		while (it.hasNext()) {
			TreeItem<Line> line = it.next();
			
			out.printf("%s\"%s\": ", tabService.getTab(intend), line.getValue().getName());
			if (line.getValue().isGroup()) {
				stack.push(it);
				it = line.getChildren().iterator();
				intend++;
				out.printf("{\n");
			} else
				out.printf("\"%s\"%s\n", line.getValue().getLang(lang), it.hasNext() ? ",": "");
			
			while (!it.hasNext() && !stack.isEmpty()) {
				intend--;
				out.printf("%s}", tabService.getTab(intend));
				it = stack.pop();
				out.printf("%s\n", it.hasNext() ? "," : "");
			}
		}
		out.print("}\n");
		out.flush();
		out.close();
	}
	
}
