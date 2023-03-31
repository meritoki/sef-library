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

import com.meritoki.library.sef.controller.node.NodeController;

public class Format {
	public String filePath = ".";
	public String fileName = "Untitled.tsv";
	public Header header = new Header();
	public Table table = new Table();
	
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	
	public List<String> getStringList() {
		List<String> stringList = new ArrayList<>();
		stringList.addAll(this.header.getStringList());
		stringList.add(Data.getHeaderString());
		stringList.addAll(this.table.getDataStringList());
		return stringList;
	}
	
	public void write() {
		if(this.filePath != null && this.fileName != null) {
			List<String> stringList = this.getStringList();
			NodeController.save(this.filePath, this.fileName, stringList);
		} else {
			System.err.println("filePath or fileName is null");
		}
	}
}
