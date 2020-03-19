package org.me.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnsupportedLanguageException extends Exception {
	
	private String langCause;
	private List<String> supported;
	
	public UnsupportedLanguageException(String lang, List<String> supported) {
		super();
		this.langCause = lang;
		if (supported != null)
			this.supported = new ArrayList<>(supported);
		else
			this.supported = new ArrayList<>(0);
	}
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("Use unsupported lang '")
				.append(langCause)
				.append("' instead of [");
		Iterator<String> it = supported.iterator();
		while (it.hasNext())
			sb.append("'")
					.append(it.next())
					.append("'")
					.append(it.hasNext() ? ", " : "");
		return sb.toString();
	}
}
