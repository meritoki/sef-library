package com.meritoki.library.sef.model.format;

import java.util.ArrayList;
import java.util.List;

public class Header {
	
	public String sef;
	public String id;
	public String name;
	public String latitude;
	public String longitude;
	public String altitude;
	public String source;
	public String link;
	public String variable;
	public String statistic;
	public String units;
	public String meta;
	
	public List<String> getStringList() {
		List<String> stringList = new ArrayList<>();
		return stringList;
	}

}
