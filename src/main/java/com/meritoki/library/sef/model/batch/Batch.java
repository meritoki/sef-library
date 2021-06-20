package com.meritoki.library.sef.model.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meritoki.library.sef.model.excel.Excel;
import com.meritoki.library.sef.model.unit.Source;
import com.meritoki.library.sef.model.unit.Station;

public class Batch {
	@JsonProperty
	public String outputPath = ".";
	@JsonProperty
	public String sef = "1.0.0";
	@JsonProperty
	public Source source = new Source();
	@JsonProperty
	public Station station = new Station();
	@JsonProperty
	public Map<String,Map<String,String>> alias = new HashMap<>();
	@JsonProperty
	public Map<String,String> meta = new HashMap<>();
	@JsonProperty
	public List<Excel> excels = new ArrayList<>();
	
	@JsonIgnore
	public String getFileNamePrefix() {
		return this.source.code+((this.station.code != null)?"_"+this.station.code:"");
	}
	
	@JsonIgnore
	public String getMetaString() {
		return "";
	}
}
