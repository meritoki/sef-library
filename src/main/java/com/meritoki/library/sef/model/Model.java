package com.meritoki.library.sef.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.meritoki.library.controller.node.NodeController;
import com.meritoki.library.sef.model.batch.Batch;
import com.meritoki.library.sef.model.excel.Excel;
import com.meritoki.library.sef.model.excel.Selector;
import com.meritoki.library.sef.model.excel.SpreadSheet;
import com.meritoki.library.sef.model.format.Data;
import com.meritoki.library.sef.model.format.Format;
import com.meritoki.library.sef.model.unit.Frame;


public class Model {
	public Batch batch;
	public List<Frame> frameList = new ArrayList<>();
	public Map<String,Format> formatMap = new HashMap<>();
	
	public static void main(String[] args) {
		Selector selector = new Selector();
		SpreadSheet sheet = new SpreadSheet();
		sheet.attribute.put("key","value");
		sheet.selectors.add(selector);
		Excel excel = new Excel();
		excel.spreadSheets.add(sheet);
		Batch batch = new Batch();
		batch.excels.add(excel);
		batch.meta.put("key","value");
		NodeController.saveJson("./", "default.json", batch);
	}
	
	public List<Format> getFormatList() {
		for(Frame frame: this.frameList) {
			frame.initDataMap();
			for(Entry<String,List<Data>> dataEntry:frame.dataMap.entrySet()) {
				String variable = dataEntry.getKey();
				List<Data> dataList = dataEntry.getValue();
				if(dataList.size() > 0) {
					Collections.reverse(dataList);
					Data data = dataList.get(0);
					Format format = this.formatMap.get(variable);
					if(format == null) {
						format = new Format();
					}
					format.header.variable = data.variable;
					format.header.statistic = data.statistic;
					format.header.units = data.units;
					format.table.dataList.addAll(dataList);
					this.formatMap.put(variable, format);
				}
			}

		}
		for(Entry<String,Format> formatEntry:this.formatMap.entrySet()) {
			Format format = formatEntry.getValue();
			format.header.sef = batch.sef;
			format.header.id = batch.station.id;
			format.header.name = batch.station.name;
			format.header.latitude = Double.toString(batch.station.latitude);
			format.header.longitude = Double.toString(batch.station.longitude);
			format.header.altitude = Double.toString(batch.station.altitude);
			format.header.source = batch.source.name;
			format.header.link = batch.source.link;
			format.header.meta = batch.getMetaString();
		}
		return new ArrayList<Format>(this.formatMap.values());
	}
}
