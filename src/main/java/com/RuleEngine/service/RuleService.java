package com.RuleEngine.service;

import com.RuleEngine.model.ruleData;

public interface RuleService {
	public void fireRules();
	public void transferData(ruleData ruleData);
	public void divideSm_link();
}
