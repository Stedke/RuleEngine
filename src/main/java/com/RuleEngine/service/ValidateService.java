package com.RuleEngine.service;

import java.util.List;

import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_nodes;

public interface ValidateService {

	public void fireValidateRules();
	public boolean checkIfDataOk();
	public ruleData transferData();
	public void setData(ruleData ruleData);
	public void missingDataInserted(ruleData dataInserted);
	public ruleData getMissingRuleData();
	public void setMissingRuleData(ruleData missingRuleData);
	public Long getNextSm_dictionaryId();
	public Long getNextSm_nodesId(); 
	public Long getNextSm_segmentsId(); 
	public Long getNextSm_node_propertiesId(); 
	public Long getNextSm_segment_propertiesId(); 
	public Long getNextSm_link_propertiesId();
	public sm_nodes getSm_nodes(Long id);
	public sm_dictionary getSm_dictionary(Long id);
	public sm_links getSm_link();
}
