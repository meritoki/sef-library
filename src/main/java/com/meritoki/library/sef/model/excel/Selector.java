package com.meritoki.library.sef.model.excel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Selector {
	
	@JsonProperty
	public String variable;//https://datarescue.climate.copernicus.eu/node/84
	@JsonProperty
	public String value;
	@JsonProperty
	public String statistic;
	@JsonProperty
	public String units;
}
