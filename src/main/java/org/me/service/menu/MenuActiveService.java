package org.me.service.menu;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashMap;
import java.util.Map;

public class MenuActiveService {

	private Map<MenuService.File, Property<Boolean>> file;
	private Map<MenuService.Command, Property<Boolean>> command;
	private Map<MenuService.Tools, Property<Boolean>> tools;

	public MenuActiveService() {
		file = new HashMap<>();
		command = new HashMap<>();
		tools = new HashMap<>();

		for (MenuService.File file : MenuService.File.values())
			this.file.put(file, new SimpleBooleanProperty(false));
		for (MenuService.Command command: MenuService.Command.values())
			this.command.put(command, new SimpleBooleanProperty(false));
		for (MenuService.Tools tools: MenuService.Tools.values())
			this.tools.put(tools, new SimpleBooleanProperty(false));
		_dev();
	}

	public <T> Property<Boolean> property(T value) {
		if (value == null)
			return null;
		if (value instanceof MenuService.File)
			return file.get(value);
		if (value instanceof MenuService.Command)
			return command.get(value);
		if (value instanceof MenuService.Tools)
			return tools.get(value);
		if (value instanceof String) {
		
		}
		return null;
	}

	public <T> void enable(T value) {
		change(value, Boolean.FALSE);
	}

	public <T> void disable(T value) {
		change(value, Boolean.TRUE);
	}

	private <T> void change(T key, Boolean value) {
		if (key == null)
			return;
		if (key instanceof MenuService.File)
			file.get(key).setValue(value);
		if (key instanceof MenuService.Command)
			command.get(key).setValue(value);
		if (key instanceof MenuService.Tools)
			tools.get(key).setValue(value);
	}

	private void _dev() {
		disable(MenuService.Tools.__NAME__);
		disable(MenuService.Tools.GENERATE_JAVA);
		disable(MenuService.Tools.GENERATE_JAVASCRIPT);
		disable(MenuService.Tools.GENERATE_TYPESCRIPT);
	}
	
}
