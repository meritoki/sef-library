package com.meritoki.library.sef.model.excel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sheet {
	
	@JsonProperty
	public int index;
	@JsonProperty
	public Attribute attribute = new Attribute();
	@JsonProperty
	public List<Selector> selectorList = new ArrayList<>();

}
