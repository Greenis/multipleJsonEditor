package org.me.serialize;

import java.util.TreeMap;

public class TabService {
	
	private static final TreeMap<Integer, String> tabs;
	
	static {
		tabs = new TreeMap<>();
		tabs.put(-8, "");
		tabs.put(-7, "");
		tabs.put(-6, "");
		tabs.put(-5, "");
		tabs.put(-4, "");
		tabs.put(-3, "");
		tabs.put(-2, "");
		tabs.put(-1, "");
		tabs.put(0,  "");
		tabs.put(1,  "\t");
		tabs.put(2,  "\t\t");
		tabs.put(3,  "\t\t\t");
		tabs.put(4,  "\t\t\t\t");
		tabs.put(5,  "\t\t\t\t\t");
		tabs.put(6,  "\t\t\t\t\t\t");
		tabs.put(7,  "\t\t\t\t\t\t\t");
		tabs.put(8,  "\t\t\t\t\t\t\t\t");
		tabs.put(9,  "\t\t\t\t\t\t\t\t\t");
		tabs.put(10, "\t\t\t\t\t\t\t\t\t\t");
		tabs.put(11, "\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(12, "\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(13, "\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(14, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(15, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(16, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(17, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(18, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(19, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(20, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(21, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(22, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(23, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(24, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(25, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(26, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(27, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(28, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(29, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(30, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(31, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		tabs.put(32, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
	}
	
	public synchronized String getTab(int intend) {
		String result = tabs.get(intend);
		if (result == null) {
			result = tabs.floorEntry(intend).getValue();
			for (int i = tabs.floorEntry(intend).getKey(); i < intend; i++) {
				result += "\t";
				tabs.putIfAbsent(i, result);
			}
		}
		return result;
	}
	
}
