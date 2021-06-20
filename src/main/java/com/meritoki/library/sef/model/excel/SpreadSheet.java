package com.meritoki.library.sef.model.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpreadSheet {
	@JsonProperty
	public int index;
	@JsonProperty
	public Map<String,String> attribute = new HashMap<>();
	@JsonProperty
	public List<Selector> selectors = new ArrayList<>();
}
