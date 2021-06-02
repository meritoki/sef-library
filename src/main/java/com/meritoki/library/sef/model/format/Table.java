package com.meritoki.library.sef.model.format;

import java.util.ArrayList;
import java.util.List;

public class Table {
	
	public List<Data> dataList = new ArrayList<>();
	
	//be able to sort dataList by time data.
	
	public List<String> getDataStringList() {
		List<String> dataStringList = new ArrayList<>();
		for(Data data: dataList) {
			dataStringList.add(data.getTabString());
		}
		return dataStringList;
	}

}
