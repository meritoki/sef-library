package com.meritoki.library.sef.model.format;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Table {
	
	public List<Data> dataList = new ArrayList<>();
	
	//be able to sort dataList by time data.
	
	@JsonIgnore
	public List<String> getDataStringList() {
		LinkedList<String> dataStringList = new LinkedList<>();
		for(Data data: dataList) {
			dataStringList.addLast(data.getTabString());
		}
		return dataStringList;
	}
	
	@JsonIgnore
	public String getStartDate() {
		Data data = dataList.get(0);
		return ((data.year!=null)?Integer.toString(data.year):"")+((data.month!=null)?Integer.toString(data.month):"")+((data.day!=null)?Integer.toString(data.day):"");
	}
	
	@JsonIgnore
	public String getEndDate() {
		int size = dataList.size();
		Data data = dataList.get(size-1);
		return ((data.year!=null)?Integer.toString(data.year):"")+((data.month!=null)?Integer.toString(data.month):"")+((data.day!=null)?Integer.toString(data.day):"");
	}
	
	@JsonIgnore
	public String getVariable() {
		Data data = dataList.get(0);
		return data.variable;
	}
	
	@JsonIgnore
	public String getName() {
		return this.getStartDate()+"_"+this.getEndDate()+"_"+this.getVariable();
	}

}
