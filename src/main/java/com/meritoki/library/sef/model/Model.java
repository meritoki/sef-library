package com.meritoki.library.sef.model;

import java.util.ArrayList;
import java.util.List;

import com.meritoki.library.controller.node.NodeController;
import com.meritoki.library.sef.model.batch.Batch;
import com.meritoki.library.sef.model.excel.Attribute;
import com.meritoki.library.sef.model.excel.Excel;
import com.meritoki.library.sef.model.excel.Selector;
import com.meritoki.library.sef.model.excel.SpreadSheet;
import com.meritoki.library.sef.model.unit.Frame;


public class Model {
	public Batch batch;
	public List<Frame> frameList = new ArrayList<>();
	
	public static void main(String[] args) {
		Attribute attribute = new Attribute();
		attribute.map.put("key","value");
		Selector selector = new Selector();
		SpreadSheet sheet = new SpreadSheet();
		sheet.attribute = attribute;
		sheet.selectorList.add(selector);
		Excel excel = new Excel();
		excel.spreadSheetList.add(sheet);
		Batch batch = new Batch();
		batch.excelList.add(excel);
		NodeController.saveJson("./", "example.json", batch);
	}
}
