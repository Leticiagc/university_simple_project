package com.ufcg.university.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MetricsParser {
	
	public MetricsParser() throws IOException {
		parser();
	}

	private void parser() throws IOException {
		File file = new File("./metrics.txt");
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String st;
	    while ((st = br.readLine()) != null) {
	    	if (!st.startsWith("#")) {
	    		//getKey(st);
	    		//String value = getValue(st);
	    		//String attr = getAttr(st);
	    		//System.out.println(key + " - " + attr + " - " + value);
	    		myRegex(st);
	    	}
	    }
	}

	private void myRegex(String st) {
		String key = "", attr = "", value = "";
		int state = 0;
		for (String c: st.split("")) {
			if (state == 0) {
				if (!c.equals("{") && !c.equals(" ")) {
					key += c;
				} else if (c.equals("{")) {
					attr += c;
					state = 1;
				} else {
					state = 2;
				}
			} else if (state == 1) {
				if (c.equals("}")) {
					state = 2;
				}
				attr += c;
			} else {
				value += c;
			}
		}
		System.out.println(key + " - " + attr + " - " + value);
	}

	private String getKey(String st) {
		return null;
	}

	private String getAttr(String st) {
		return null;
	}

	private String getValue(String st) {
		return null;
	}
}
