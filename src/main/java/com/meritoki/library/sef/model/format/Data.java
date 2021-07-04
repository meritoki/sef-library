package com.meritoki.library.sef.model.format;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.meritoki.library.sef.model.unit.Solar;

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
	public String meta;
	
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
	
	public void applySolar(Solar solar) {
		//UTC=localtime-(longitude/15);
		if(this.hour != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR,year);
			calendar.set(Calendar.MONTH,month-1);
			calendar.set(Calendar.DATE,day);
			calendar.set(Calendar.HOUR,hour);
			calendar.set(Calendar.MINUTE,(minute != null)?minute:0);
			calendar.set(Calendar.SECOND,0);
			this.meta = ((this.meta.isEmpty())?"":this.meta+",")+"orig.time="+format.format(calendar.getTime());
			calendar.add(Calendar.HOUR, -(int)(solar.longitude/15));
			this.year = calendar.get(Calendar.YEAR);
			this.month = calendar.get(Calendar.MONTH)+1;
			this.day = calendar.get(Calendar.DATE);
			this.hour = calendar.get(Calendar.HOUR);
			this.minute = calendar.get(Calendar.MINUTE);
		}
	}
	
	public boolean isNumeric() {
	    if (this.value == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(value);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
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
