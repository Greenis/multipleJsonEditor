package org.me.controller;

import javafx.fxml.Initializable;

import java.util.HashMap;
import java.util.Map;

public class ContollerStack {
	
	private static final Map<Class<? extends Initializable>, Initializable> instances = new HashMap<>();
	
	public static <T extends Initializable> void register(Class<T> type, T instance) {
		instances.put(type, instance);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Initializable> T instance(Class<T> type) {
		return (T) instances.get(type);
	}
	
}
