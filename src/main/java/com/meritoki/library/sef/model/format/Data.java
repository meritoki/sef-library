package com.meritoki.library.sef.model.format;

public class Data {
	
	public Integer year;
	public Integer month;
	public Integer day;
	public Integer hour;
	public Integer minute;
	public String period;
	public String value;
	public String variable;
	public String units;
	public String statistic;
	public Integer meta;
	
	public static String getHeaderString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Year");
		stringBuilder.append("\t");
		stringBuilder.append("Month");
		stringBuilder.append("\t");
		stringBuilder.append("Day");
		stringBuilder.append("\t");
		stringBuilder.append("Hour");
		stringBuilder.append("\t");
		stringBuilder.append("Minute");
		stringBuilder.append("\t");
		stringBuilder.append("Period");
		stringBuilder.append("\t");
		stringBuilder.append("Value");
		stringBuilder.append("\t");
		stringBuilder.append("Meta");
		return stringBuilder.toString();
	}
	
	public String getTabString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append((year != null)?year:"NA");
		stringBuilder.append("\t");
		stringBuilder.append((month != null)?month:"NA");
		stringBuilder.append("\t");
		stringBuilder.append((day != null)?day:"NA");
		stringBuilder.append("\t");
		stringBuilder.append((hour != null)?hour:"NA");
		stringBuilder.append("\t");
		stringBuilder.append((minute != null)?minute:"NA");
		stringBuilder.append("\t");
		stringBuilder.append((period != null)?period:"0");
		stringBuilder.append("\t");
		stringBuilder.append((value != null)?value:"0.00");
		stringBuilder.append("\t");
		stringBuilder.append((meta != null)?meta:"");
		return stringBuilder.toString();
	}

}
