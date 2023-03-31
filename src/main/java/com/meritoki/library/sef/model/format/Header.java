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
package com.meritoki.library.sef.model.format;

import java.util.ArrayList;
import java.util.List;

public class Header {
	
	public String sef;
	public String id;//station
	public String name;//station
	public String latitude;//station
	public String longitude;//station
	public String altitude;//station
	public String source;//source
	public String link;//source
	public String variable;//data
	public String statistic;//data
	public String units;//data
	public String meta;//meta
	
	public List<String> getStringList() {
		List<String> stringList = new ArrayList<>();
		stringList.add("SEF\t"+this.sef);
		stringList.add("ID\t"+this.id);
		stringList.add("Name\t"+this.name);
		stringList.add("Lat\t"+this.latitude);
		stringList.add("Lon\t"+this.longitude);
		stringList.add("Alt\t"+this.altitude);
		stringList.add("Source\t"+this.source);
		stringList.add("Link\t"+this.link);
		stringList.add("Vbl\t"+this.variable);
		stringList.add("Stat\t"+this.statistic);
		stringList.add("Units\t"+this.units);
		stringList.add("Meta\t"+this.meta);
		return stringList;
	}

}
