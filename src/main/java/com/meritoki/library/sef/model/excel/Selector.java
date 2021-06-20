package com.meritoki.library.sef.model.excel;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.meritoki.library.sef.controller.parse.ParseController;

public class Selector {
	
	static Logger logger = LogManager.getLogger(Selector.class.getName());
	@JsonProperty
	public String variable;//https://datarescue.climate.copernicus.eu/node/84
	@JsonProperty
	public String value;//0-1,2,3-4,5:0,1-2,3,4-5 rows:columns
	@JsonProperty
	public String statistic = "point";
	@JsonProperty
	public String units;
	@JsonProperty
	public String delimeter;
	@JsonProperty
	public String hour;
	@JsonProperty
	public String minute;
	
	@JsonIgnore
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
	
	@JsonIgnore
	@Override
	public String toString() {
		String string = "";
		ObjectWriter ow = new ObjectMapper().writer();//.withDefaultPrettyPrinter();
		
		try {
			string = ow.writeValueAsString(this);
		} catch (IOException ex) {
			logger.error("IOException " + ex.getMessage());
		}
		
		return string;
	}
}
