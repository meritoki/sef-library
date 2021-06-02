package com.meritoki.library.sef.model.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meritoki.library.sef.controller.parse.ParseController;

public class Selector {
	
	@JsonProperty
	public String variable;//https://datarescue.climate.copernicus.eu/node/84
	@JsonProperty
	public String value;//0-1,2,3-4,5:0,1-2,3,4-5 rows:columns
	@JsonProperty
	public String statistic = "point";
	@JsonProperty
	public String units;
	
	public Object[] getValueArray() {
		Object[] valueArray = new Object[2];
		if(this.value != null && this.value.contains(":")) {
			String[] colonArray = this.value.split(":");
			if(colonArray.length == 2) {
				valueArray[0] = ParseController.parseIndex(colonArray[0]);
				valueArray[1] = ParseController.parseIndex(colonArray[1]);
			}
		}
		return valueArray;
	}
	
	
}
