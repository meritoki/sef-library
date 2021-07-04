 package com.meritoki.library.sef.controller.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseController {
	
	public static boolean validateIndex(String index) {
		String regex = "[0-9]+(?:-[0-9]+)?(,[0-9]+(?:-[0-9]+)?)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(index);
		return matcher.find();
	}
	
	public static List<Integer> parseIndex(String index) {
		List<Integer> indexList = new ArrayList<>();
		if(validateIndex(index)) {
			String[] commaArray = index.split(",");
			if(commaArray.length > 0) {
				for(String comma:commaArray) {
					if(comma.contains("-")) {
						indexList.addAll(parseRange(comma));
					} else {
						indexList.add(parseInteger(comma));
					}
				}
			}
		} else {
			System.err.println("invalid index: "+index);
		}
//		System.out.println("parseIndex("+index+") indexList="+indexList);
		return indexList;
	}
	
	public static List<Integer> parseRange(String range) {
		List<Integer> integerList = new ArrayList<>();
		String[] rangeArray = range.split("-");
		if(rangeArray.length == 2) {
			int a = parseInteger(rangeArray[0]);
			int b = parseInteger(rangeArray[1]);
			for(int i = a; i <= b; i++) {
				integerList.add(i);
			}
		}
		return integerList;
	}
	
	public static Integer parseInteger(String integer) {
		return Integer.parseInt(integer);
	}

}
