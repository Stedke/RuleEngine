package com.RuleEngine.service;

import com.RuleEngine.model.ruleData;

public interface ValidateService {

	public void fireValidateRules();
	public boolean checkIfDataOk();
	public ruleData transferData();
	public void setData(ruleData ruleData);
	public void missingDataInserted(ruleData dataInserted);
	public ruleData getMissingRuleData();
	public void setMissingRuleData(ruleData missingRuleData);
}
