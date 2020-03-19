package org.me.service.menu;

public class MenuService {

	public enum File {
		__NAME__("menu.file.name"),
		OPEN("menu.file.open"),
		NEW("menu.file.new"),
		SAVE("menu.file.save"),
		SAVE_AS("menu.file.saveAs"),
		EXPORT("menu.file.export.name"),
		EXPORT_ARCHIVE("menu.file.export.asArchive"),
		EXPORT_FILES("menu.file.export.asFiles"),
		SETTINGS("menu.file.settings"),
		EXIT("menu.file.exit");

		private final String token;

		File(String token) {
			this.token = token;
		}

		public String token() {
			return token;
		}
	}

	public enum Command {
		__NAME__("menu.command.name"),
		ADD_COLUMN("menu.command.addColumn"),
		ADD_GROUP("menu.command.addGroup"),
		ADD_WORD("menu.command.addWord"),
		REMOVE_ROW("menu.command.delRow"),
		MOVE_UP("menu.command.moveUp"),
		MOVE_DOWN("menu.command.moveDown");

		private final String token;

		Command(String token) {
			this.token = token;
		}

		public String token() {
			return token;
		}
	}

	public enum Tools {
		__NAME__("menu.tools.name"),
		GENERATE("menu.tools.generate.name"),
		GENERATE_TYPESCRIPT("menu.tools.generate.typescript"),
		GENERATE_JAVASCRIPT("menu.tools.generate.javascript"),
		GENERATE_JAVA("menu.tools.generate.java");

		private final String token;

		Tools(String token) {
			this.token = token;
		}

		public String token() {
			return token;
		}
	}

}
