package com.meritoki.library.sef.model.excel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Excel {
	
	@JsonProperty
	List<Sheet> sheetList = new ArrayList<>();

}
