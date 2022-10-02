package com.meritoki.library.sef.model.format;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meritoki.library.sef.model.unit.Solar;

public class Table {

	static Logger logger = LogManager.getLogger(Table.class);
	public List<Data> dataList = new ArrayList<>();
	
	@JsonIgnore
	public void applySolar(Solar solar) {
		for(Data data:dataList) {
			data.applySolar(solar);
		}
	}

	@JsonIgnore
	public List<String> getDataStringList() {
		LinkedList<String> dataStringList = new LinkedList<>();
		Comparator<Data> compareData = Comparator.comparing(Data::getYear)
									.thenComparingInt(Data::getMonth)
									.thenComparingInt(Data::getDay)
									.thenComparingInt(Data::getHour)
									.thenComparingInt(Data::getMinute);
		this.dataList.sort(compareData);
		for (Data data : dataList) {
			String dataString = data.getTabString();
			if (data.isNumeric()) {
				logger.info(dataString);
				dataStringList.addLast(dataString);
			} else {
				logger.error(dataString+" NOT NUMERIC");
			}
		}
		return dataStringList;
	}

	@JsonIgnore
	public String getStartDate() {
		Data data = dataList.get(0);
		return ((data.year != null) ? Integer.toString(data.year) : "")
				+ ((data.month != null) ? String.format("%02d", data.month) : "")
				+ ((data.day != null) ? String.format("%02d", data.day) : "");
	}

	@JsonIgnore
	public String getEndDate() {
		int size = dataList.size();
		Data data = dataList.get(size - 1);
		return ((data.year != null) ? Integer.toString(data.year) : "")
				+ ((data.month != null) ? String.format("%02d", data.month) : "")
				+ ((data.day != null) ? String.format("%02d", data.day) : "");
	}

	@JsonIgnore
	public String getVariable() {
		Data data = dataList.get(0);
		return data.variable;
	}

	@JsonIgnore
	public String getName() {
		return this.getStartDate() + "_" + this.getEndDate() + "_" + this.getVariable();
	}

}
