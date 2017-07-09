package com.RuleEngine.service;

import java.util.List;

import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_linkAreasData;

public interface RuleService {
	public void fireRules();
	public void transferData(ruleData ruleData);
	public void divideSm_link();
	public void updateAllNodesImpacts();
	public List<sm_linkAreasData> getSm_linkAreas();
	public void setSm_linkAreas(List<sm_linkAreasData> sm_linkAreas);
}
