/*
 * Copyright 2020-2023 Joaquin Osvaldo Rodriguez
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
package com.meritoki.library.sef.controller.node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	@JsonIgnore
	public static void save(String filePath, String fileName, Object object) {
		logger.info("save(" + filePath + ", " + fileName + ", object)");
		save(new File(filePath + getSeperator() + fileName), object);
	}
	
	@JsonIgnore
	public static void save(File file, Object object) {
		logger.info("save(" + file + ", object)");
		try (PrintWriter writer = new PrintWriter(file)) {
			if (object instanceof StringBuilder)
				writer.write(((StringBuilder) object).toString());
			else if (object instanceof List) {
				List<String> stringList = (List<String>) object;
				for(String s: stringList) {
					writer.write(s+System.lineSeparator());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
