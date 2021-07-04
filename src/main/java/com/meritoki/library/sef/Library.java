package com.meritoki.library.sef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import com.meritoki.library.sef.model.format.Format;
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
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) {

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
		List<Excel> excelList = batch.excels;
		NumberFormat formatter = new DecimalFormat("#0.00");   
		for (Excel excel : excelList) {
			XSSFWorkbook workbook = getWorkbook(excel.fileName);
			if (workbook != null) {
				logger.info("process(batch) workbook=" + excel.fileName);
				List<SpreadSheet> spreadSheetList = excel.spreadSheets;
				for (SpreadSheet spreadsheet : spreadSheetList) {
					Frame frame = new Frame();
					frame.attribute = spreadsheet.attribute;
					XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(spreadsheet.index);
					logger.info("process(batch) sheet=" + sheet.getSheetName());
					List<Selector> selectorList = spreadsheet.selectors;
					for (Selector selector : selectorList) {
						logger.info("process(batch) selector=" + selector);
						Object[] valueArray = selector.getValueArray();
						if (valueArray != null) {
							List<Integer> rowList = (List<Integer>) valueArray[0];
							List<Integer> columnList = (List<Integer>) valueArray[1];
							for (int i = 0; i < rowList.size(); i++) {
								int rowIndex = rowList.get(i);
								XSSFRow row = sheet.getRow(rowIndex);
								if (row != null) {
									for (int j = 0; j < columnList.size(); j++) {
										int columnIndex = columnList.get(j);
										XSSFCell cell = row.getCell(columnIndex);
										if (cell != null) {
											Input input = new Input();
											List<Input> inputList = frame.inputMap.get(rowIndex);
											if (inputList == null) {
												inputList = new ArrayList<>();
											}
											String value;
											switch (cell.getCellType()) {
											case BLANK: {
//												System.out.println(ANSI_RED + "BLANK"+ANSI_RESET);
												logger.warn("process(batch) BLANK value[" + rowIndex + "," + columnIndex
														+ "]");
												logger.warn("process(batch) selector=" + selector);
												logger.warn("process(batch) sheet=" + sheet.getSheetName());
												logger.warn("process(batch) workbook=" + excel.fileName);
												break;
											}
											case NUMERIC: {
												value = cell.getRawValue();
												logger.info("process(batch) NUMERIC value[" + rowIndex + ","
														+ columnIndex + "]=" + value);
												input.map.put("variable", selector.variable);
												input.map.put("value", value);
												input.map.put("statistic", selector.statistic);
												input.map.put("units", selector.units);
												inputList.add(input);
												break;
											}
											case BOOLEAN:
												logger.warn("process(batch) BOOLEAN not supported");
												break;
											case ERROR:
												logger.warn("process(batch) ERROR not supported");
												break;
											case FORMULA:
												logger.warn("process(batch) FORMULA not supported");
												break;
											case STRING:
												value = cell.getStringCellValue();
												value = value.trim();
												logger.info("process(batch) STRING value[" + rowIndex + ","
														+ columnIndex + "]=" + value);
												inputList.addAll(getInputList(selector, value));
												break;
											case _NONE:
												logger.warn("process(batch) _NONE not supported");
												break;
											default:
												logger.warn("process(batch) not supported");
												break;
											}
											frame.inputMap.put(rowIndex, inputList);
										} else {
											logger.warn("process(batch) cell is null");
										}
									}
								} else {
									logger.warn("process(batch) row is null");
								}
							}
						}
					}
					model.frameList.add(frame);
				}
			}
		}

		File output = new File(model.batch.outputPath);
		if (!output.exists()) {
			output.mkdirs();
		}
		List<Format> formatList = model.getFormatList();
		for (Format format : formatList) {
			String fileName = model.batch.getFileNamePrefix() + "_" + format.table.getName() + ".tsv";
			NodeController.save(model.batch.outputPath, fileName, format.getStringList());
		}
	}

	public static List<Input> getInputList(Selector selector, String value) {
		List<Input> inputList = new ArrayList<>();
		if (!model.batch.exclude.contains(value) && value.length() > 0) {
			if (selector.delimeter != null) {
				String[] vArray = value.split(selector.delimeter);
				String[] hourArray = (selector.hour != null) ? selector.hour.split(",") : new String[0];
				String[] minuteArray = (selector.minute != null && !selector.minute.equals("null"))
						? selector.minute.split(",")
						: new String[0];
				for (int p = 0; p < vArray.length; p++) {
					Input input = new Input();
					value = vArray[p].trim();
					input.map.put("meta", "Orig=" + value);
					value = getAlias(selector.variable, value);
					input.map.put("variable", selector.variable);
					input.map.put("value", value);
					input.map.put("statistic", selector.statistic);
					input.map.put("units", selector.units);
					if (p < hourArray.length) {
						input.map.put("hour", hourArray[p]);
					}
					if (p < minuteArray.length) {
						input.map.put("minute", minuteArray[p]);
					}
					inputList.add(input);
				}
			} else {
				Input input = new Input();
				value = getAlias(selector.variable, value);
				input.map.put("variable", selector.variable);
				input.map.put("value", value);
				input.map.put("statistic", selector.statistic);
				input.map.put("units", selector.units);
				inputList.add(input);
			}
		}
		return inputList;
	}

	public static String[] directions = { "N", "NhE", "NbE", "NbEhe", "NNE", "NNEhE", "NEbN", "NEhN", "NE", "NEhE",
			"NEbE", "NEbEhE", "ENE", "EbNhN", "EbN", "EhN", "E", "EhS", "EbS", "EbShS", "ESE", "SEbEhE", "SEbE", "SEhE",
			"SE", "SEhS", "SEbS", "SSEhE", "SSE", "SbEhE", "SbE", "ShE", "S", "ShW", "SbW", "SbWhW", "SSW", "SSWhW",
			"SWbS", "SWhS", "SW", "SWhW", "SWbW", "SWbWhW", "WSW", "WbShS", "WbS", "WhS", "W", "WhN", "WbN", "WbNhN",
			"WNW", "NWbWhW", "NWbW", "NWhW", "NW", "NWhN", "NWbN", "NNWhW", "NNW", "NbWhW", "NbW", "NhW" };

	public static String getAlias(String variable, String key) {
		String value = null;
		Map<String, String> aliasMap = model.batch.alias.get(variable);
		if (aliasMap != null) {
			value = aliasMap.get(key);
		}

		if (value == null) {
			List<String> directionsList = Arrays.asList(directions);
			if (directionsList.contains(key)) {
				int index = directionsList.indexOf(key);
				NumberFormat formatter = new DecimalFormat("#0.00");   
				value = formatter.format(5.625 * index);
			}
		}
		return (value != null) ? value : key;
	}

	public static XSSFWorkbook getWorkbook(String fileName) {
		logger.info("getWorkbook(" + fileName + ")");
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
