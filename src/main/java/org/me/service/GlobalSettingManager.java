package org.me.service;

import org.me.entity.Setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GlobalSettingManager {
	
	private static final String CONTAINER = ".pe";
	private static final String MAIN = ".settings";
	
	private Setting application;
	
	public Setting getSettings() {
		return application;
	}
	
	public GlobalSettingManager read() throws IOException {
		Properties props = new Properties();
		String _p = File.pathSeparator;
		props.load(new FileInputStream(System.getenv("user.dir") + _p + CONTAINER + _p + MAIN));
		
		return this;
	}
}
