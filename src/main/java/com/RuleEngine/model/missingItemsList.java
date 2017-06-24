package com.RuleEngine.model;

import java.util.ArrayList;
import java.util.List;

public class missingItemsList {
	List<String> missingIds = new ArrayList<String>();

	public void insertMissingElementsName(String name){
		missingIds.add(name);
	}

	public List<String> getMissingIds() {
		return missingIds;
	}

	public void setMissingIds(List<String> missingIds) {
		this.missingIds = missingIds;
	}

}
