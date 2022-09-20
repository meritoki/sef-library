package com.meritoki.library.sef.model.unit;

public class Time {
	
	public Integer year = null;
	public Integer month= null;
	public Integer day= null;
	public Integer hour= null;
	public Integer minute= null;
	public String period= null;
	
	@Override
	public String toString() {
		return year+"/"+month+"/"+day;
	}

}
