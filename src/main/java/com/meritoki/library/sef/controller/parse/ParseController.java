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
