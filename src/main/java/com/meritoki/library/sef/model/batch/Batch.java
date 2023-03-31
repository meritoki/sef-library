/*
 * Copyright 2021-2023 Joaquin Osvaldo Rodriguez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meritoki.library.sef.model.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meritoki.library.sef.model.excel.Excel;
import com.meritoki.library.sef.model.unit.Solar;
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
	public Solar solar = new Solar();
	@JsonProperty
	public List<String> exclude = new ArrayList<>();
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
