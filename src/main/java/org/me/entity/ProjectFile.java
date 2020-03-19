package org.me.entity;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ProjectFile {

	private String name;
	private String path;
	private final Project project;

	public ProjectFile(Project project, String name, String path) {
		this.project = project;
		this.name = name;
		this.path = path;
	}

	public Project getProject() {
		return project;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public InputStream getInputStream() throws FileNotFoundException {
		if (project.isArchive()) {
			try {
				ZipFile zip = new ZipFile(project.getProjectFile());
				ZipEntry entry = zip.getEntry(path);
				return zip.getInputStream(entry);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			File file = new File(project.getProjectFile().getParentFile(), path);
			if (file.isAbsolute())
				return new FileInputStream(file);
			return new FileInputStream(new File(project.getProjectFile().getParentFile(), path));
		}
	}
	
	public OutputStream getOutputStream() throws IOException {
		File file = new File(path);
		if (file.isAbsolute())
			return new FileOutputStream(file);
		return new FileOutputStream(new File(project.getProjectFile().getParentFile(), path));
	}
	
	public ZipEntry getZipEntry() {
		try {
			ZipFile zip = new ZipFile(project.getProjectFile());
			return zip.getEntry(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
