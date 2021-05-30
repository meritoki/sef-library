package com.meritoki.library.sef.controller.node;

import java.io.File;

import com.meritoki.library.sef.model.batch.Batch;
import com.meritoki.library.sef.model.excel.Excel;



public class NodeController extends com.meritoki.library.controller.node.NodeController {
	
	public static Excel openExcel(String filePath, String fileName) {
		return openExcel(new java.io.File(filePath + "/" + fileName));
	}

	public static Excel openExcel(File file) {
		Excel Excel = (Excel) NodeController.openJson(file, Excel.class);
		logger.info("openExcel(" + file + ") Excel=" + Excel);
		return Excel;
	}
	
	public static Batch openBatch(String filePath, String fileName) {
		return openBatch(new java.io.File(filePath + "/" + fileName));
	}

	public static Batch openBatch(File file) {
		Batch Batch = (Batch) NodeController.openJson(file, Batch.class);
		logger.info("openBatch(" + file + ") Batch=" + Batch);
		return Batch;
	}

}
