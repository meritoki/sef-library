package com.meritoki.library.sef.model.format;

import java.util.ArrayList;
import java.util.List;

import com.meritoki.library.sef.controller.node.NodeController;

public class Format {
	public String filePath;
	public String fileName;
	public Header header;
	public Table table;
	
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
	
	public void write() {
		if(this.filePath != null && this.fileName != null) {
			List<String> stringList = new ArrayList<>();
			stringList.addAll(this.header.getStringList());
			stringList.addAll(this.table.getDataStringList());
			NodeController.save(this.filePath, this.fileName, stringList);
		} else {
			System.err.println("filePath or fileName is null");
		}
	}
}
