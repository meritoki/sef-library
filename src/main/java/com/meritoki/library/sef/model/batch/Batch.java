package com.meritoki.library.sef.model.batch;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meritoki.library.sef.model.excel.Excel;

public class Batch {
	@JsonProperty
	List<Excel> excelList = new ArrayList<>();
}
