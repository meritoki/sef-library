package com.meritoki.library.sef.model.unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meritoki.library.sef.model.excel.Attribute;

public class Frame {
	public Attribute attribute;
	public Map<Integer,List<Input>> inputMap = new HashMap<>();
}
