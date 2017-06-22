package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;

public class ValidateServiceImpl implements ValidateService{

	private ruleData ruleData = new ruleData();
    private ruleData missingRuleData = new ruleData();
	
	@Autowired
	private KieContainer kieContainer;

	@Override
	public void fireValidateRules() {
		KieSession kieSession = kieContainer.newKieSession();
		//fire validation rules. Different kieContainer might be needed.
		//Validation rule should add element to sm_dictionary missing list
		//(if not sm_dictionary, name=bleble then missing add bleble).
		kieSession.dispose();
	}

	@Override
	public boolean checkIfDataOk() {
		boolean dataOk = true;
		dataOk = missingRuleData.getSm_dictionary().isEmpty();
		dataOk = missingRuleData.getSm_nodes().isEmpty();
		dataOk = missingRuleData.getSm_node_properties().isEmpty();
		dataOk = missingRuleData.getSm_segments().isEmpty();
		dataOk = missingRuleData.getSm_segment_properties().isEmpty();
		dataOk = missingRuleData.getSm_link_properties().isEmpty();
		
		return dataOk;
	}

	@Override
	public ruleData transferData() {
        return ruleData;
	}

	@Override
	public void setData(ruleData ruleData) {
		List<sm_dictionary> d_temp = this.ruleData.getSm_dictionary();
		List<sm_nodes> n_temp = this.ruleData.getSm_nodes();
		List<sm_node_properties> np_temp = this.ruleData.getSm_node_properties();
		List<sm_segments> s_temp = this.ruleData.getSm_segments();
		List<sm_segment_properties> sp_temp = this.ruleData.getSm_segment_properties();
		List<sm_link_properties> lp_temp = this.ruleData.getSm_link_properties();
		
		d_temp.addAll(ruleData.getSm_dictionary());
		n_temp.addAll(ruleData.getSm_nodes());
		np_temp.addAll(ruleData.getSm_node_properties());
		s_temp.addAll(ruleData.getSm_segments());
		sp_temp.addAll(ruleData.getSm_segment_properties());
		lp_temp.addAll(ruleData.getSm_link_properties());
		
		this.ruleData.setLinkId(ruleData.getLinkId());
		this.ruleData.setSm_dictionary(d_temp);
		this.ruleData.setSm_nodes(n_temp);
		this.ruleData.setSm_node_properties(np_temp);
		this.ruleData.setSm_segments(s_temp);
		this.ruleData.setSm_segment_properties(sp_temp);
		this.ruleData.setSm_link_properties(lp_temp);
	}

	@Override
	public void missingDataInserted(ruleData dataInserted) {
		List<sm_dictionary> d_temp = this.missingRuleData.getSm_dictionary();
		List<sm_nodes> n_temp = this.missingRuleData.getSm_nodes();
		List<sm_node_properties> np_temp = this.missingRuleData.getSm_node_properties();
		List<sm_segments> s_temp = this.missingRuleData.getSm_segments();
		List<sm_segment_properties> sp_temp = this.missingRuleData.getSm_segment_properties();
		List<sm_link_properties> lp_temp = this.missingRuleData.getSm_link_properties();
		
		d_temp.removeAll(dataInserted.getSm_dictionary());
		n_temp.removeAll(dataInserted.getSm_nodes());
		np_temp.removeAll(dataInserted.getSm_node_properties());
		s_temp.removeAll(dataInserted.getSm_segments());
		sp_temp.removeAll(dataInserted.getSm_segment_properties());
		lp_temp.removeAll(dataInserted.getSm_link_properties());
		
		this.missingRuleData.setSm_dictionary(d_temp);
		this.missingRuleData.setSm_nodes(n_temp);
		this.missingRuleData.setSm_node_properties(np_temp);
		this.missingRuleData.setSm_segments(s_temp);
		this.missingRuleData.setSm_segment_properties(sp_temp);
		this.missingRuleData.setSm_link_properties(lp_temp);
	}

}
