package com.meritoki.library.sef.model.unit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {
	
	@JsonProperty
	public String name;
	@JsonProperty
	public String id;
	@JsonProperty
	public String code;
	@JsonProperty
	public Double latitude;
	@JsonProperty
	public Double longitude;
	@JsonProperty
	public Double altitude;

}
