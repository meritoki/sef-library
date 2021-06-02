package com.meritoki.library.sef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.meritoki.library.sef.controller.node.NodeController;
import com.meritoki.library.sef.model.Model;
import com.meritoki.library.sef.model.batch.Batch;
import com.meritoki.library.sef.model.excel.Excel;
import com.meritoki.library.sef.model.excel.Selector;
import com.meritoki.library.sef.model.excel.SpreadSheet;
import com.meritoki.library.sef.model.unit.Frame;
import com.meritoki.library.sef.model.unit.Input;

/**
 * Hello world!
 *
 */
public class Library {
	static Logger logger = LogManager.getLogger(Library.class.getName());
	public static String versionNumber = "0.1.202105";
	public static String vendor = "Meritoki";
	public static String about = "Version " + versionNumber + " Copyright " + vendor + " 2020-2021";
	public static Option helpOption = new Option("h", "help", false, "Print usage information");
	public static Option versionOption = new Option("v", "version", false, "Print version information");
	public static Option batchPathOption = Option.builder("b").longOpt("batch").desc("Option to input batch path")
			.hasArg().build();
	
	public static volatile Model model = new Model();
	public static String batchName;
	public static String batchPath;
	
    public static void main( String[] args ) {
    	
    	Options options = new Options();
		options.addOption(helpOption);
		options.addOption(versionOption);
		options.addOption(batchPathOption);
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine commandLine = parser.parse(options, args);
			if (commandLine.hasOption("help")) {
				logger.info("main(args) help");
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("sef", options, true);
			} else if (commandLine.hasOption("version")) {
				System.out.println(about);
			} else {
				if (commandLine.hasOption("batch")) {
					batchPath = commandLine.getOptionValue("batch");
					logger.info("main(args) batch=" + batchPath);
				}
			}
		} catch (org.apache.commons.cli.ParseException ex) {
			logger.error(ex);
		}
		
		try {
			model.batch = null;
			if (batchPath != null) {
				model.batch = getBatch(batchPath);
			}
			
			if (model.batch != null) {
				System.out.println("Batch: " + batchPath);
				process(model.batch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	public static Batch getBatch(String batchPath) {
		return NodeController.openBatch(new File(batchPath));
	}
	
	public static void process(Batch batch) {
		List<Excel> excelList = batch.excelList;
		for(Excel excel: excelList) {
			XSSFWorkbook workbook = getWorkbook(excel.fileName);
			if(workbook != null) {
				List<SpreadSheet> spreadSheetList = excel.spreadSheetList;
				for(SpreadSheet spreadsheet: spreadSheetList) {
					Frame frame = new Frame();
					XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(spreadsheet.index);
					List<Selector> selectorList = spreadsheet.selectorList;
					for(Selector selector: selectorList) {
						Object[] valueArray = selector.getValueArray();
						if(valueArray != null) {
							List<Integer> rowList = (List<Integer>) valueArray[0];
							List<Integer> columnList = (List<Integer>) valueArray[1];
							for(int i = 0;i<rowList.size();i++) {
								int rowIndex = rowList.get(i);
								XSSFRow row = sheet.getRow(rowIndex);
								if(row != null) {
									for(int j = 0;j<columnList.size();j++) {
										int columnIndex = columnList.get(j);
										XSSFCell cell = row.getCell(columnIndex);
										if(cell != null) {
											System.out.println();
											Input input = new Input();
											input.map.put("variable",selector.variable);
											input.map.put("value",cell.getRawValue());
											input.map.put("statistic",selector.statistic);
											input.map.put("units",selector.units);
											List<Input> inputList = frame.inputMap.get(rowIndex);
											if(inputList == null) {
												inputList = new ArrayList<>();
											}
											inputList.add(input);
											frame.inputMap.put(rowIndex,inputList);
										}else {
											System.err.println("cell is null");
										}
									}
								} else {
									System.err.println("row is null");
								}
							}
						}
					}
					model.frameList.add(frame);
				}
			}
		}
	}
	
	public static XSSFWorkbook getWorkbook(String fileName) {
        FileInputStream inputStream;
        XSSFWorkbook workbook = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return workbook;
	}
}
