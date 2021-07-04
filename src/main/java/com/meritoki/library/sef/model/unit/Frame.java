package com.meritoki.library.sef.model.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.meritoki.library.sef.model.format.Data;

public class Frame {
	public Map<String,String> attribute;
	public Map<Integer,List<Input>> inputMap = new HashMap<>();
	public Map<String,List<Data>> dataMap = new HashMap<>();
	
	//Get Data List by variable
	public void initDataMap() {
		for(Entry<Integer,List<Input>> inputEntry:inputMap.entrySet()) {
			Time time = new Time();
			if(attribute != null) {
				String year = attribute.get("year");
				String month = attribute.get("month");
				String day = attribute.get("day");
				time.year = (year != null)?Integer.parseInt(year):null;
				time.month = (month != null)?Integer.parseInt(month):null;
				time.day = (day != null)?Integer.parseInt(day):null;
			}
			List<Input> inputList = inputEntry.getValue();
			LinkedList<Input> inputStack = new LinkedList<>();
			for(int i=0;i<inputList.size();i++) {
				Input input = inputList.get(i);
				String variable = input.map.get("variable");
				if(variable.equals("time")) {
					String value = input.map.get("value");
					if(value != null && !value.isEmpty()) {
					String units = input.map.get("units");
					switch(units) {
					case "year":{
						time.year = Integer.parseInt(value);
						break;
					}
					case "month":{
						time.month = Integer.parseInt(value);
						break;
					}
					case "day":{
						time.day = Integer.parseInt(value);
						break;
					}
					case "hour":{
						time.hour = Integer.parseInt(value);
						break;
					}
					case "minute":{
						time.minute = Integer.parseInt(value);
						break;
					}
					}
					}
				} else {
					inputStack.push(input);
				}
			}
			
			while(inputStack.size() > 0) {
				Input input = inputStack.pop();
				Data data = new Data();
				data.year = time.year;
				data.month = time.month;
				data.day = time.day;
				String hour = input.map.get("hour");
				String minute = input.map.get("minute");
				if(hour != null && !hour.isEmpty() && !hour.equals("null")) {
					data.hour = Integer.parseInt(hour);
				} else {
					data.hour = time.hour;
				}
				if(minute != null && !minute.isEmpty() && !minute.equals("null")) {
					data.minute = Integer.parseInt(minute);
				} else {
					data.minute = time.minute;
				}
				data.variable = input.map.get("variable");
				data.value = input.map.get("value");
				data.units = input.map.get("units");
				data.statistic = input.map.get("statistic");
				data.meta = input.map.get("meta");
				List<Data> dataList = this.dataMap.get(data.variable);
				if(dataList == null) {
					dataList = new ArrayList<>();
				}
				dataList.add(data);
				this.dataMap.put(data.variable,dataList);
			}
		}
	}
}
