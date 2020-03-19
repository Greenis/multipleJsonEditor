package org.me.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Project {

	private File projectFile;
	private boolean pathChanged;

	private String path;
	private String name;
	private boolean archive;

	private List<ProjectFile> filesPath;

	public Project() {
		setPath(null);
		setName("");
		isArchive(false);
		projectFile = null;
		filesPath = new ArrayList<>();
		pathChanged = false;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
		this.pathChanged = true;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public File getProjectFile() throws FileNotFoundException {
		if (!pathChanged)
			return projectFile;
		if (path != null) {
			projectFile = new File(path);
			if (!projectFile.exists())
				throw new FileNotFoundException(path);
		} else
			projectFile = null;
		return projectFile;
	}

	public boolean isArchive() {
		return archive;
	}
	public void isArchive(boolean archive) {
		this.archive = archive;
	}

	public List<ProjectFile> getFiles() {
		return filesPath;
	}

	public class GlobalProject {
		
//		private String 
		
	}
}
