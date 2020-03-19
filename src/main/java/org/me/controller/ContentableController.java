package org.me.controller;

import org.me.entity.Project;

import java.io.IOException;

public interface ContentableController {
	
	/**
	 * Заполнение формы данными.
	 * @param project
	 */
	void open(Project project);

	Project project();
	
	/**
	 * Сохранение проекта.
	 * @throws IOException
	 */
	void save() throws IOException;

	void close();
	
	/**
	 * Вызывается {@link GlobalController} при выборе пользовалес данной вкладки.
	 */
	void choose();

}
