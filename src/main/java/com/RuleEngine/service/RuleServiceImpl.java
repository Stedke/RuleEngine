package com.RuleEngine.service;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RuleServiceImpl implements RuleService {

	public ruleData ruleData = new ruleData();
	
	
	@Autowired
	private KieContainer kieContainer;
	
	public void testDrools(){
        KieSession kieSession = kieContainer.newKieSession();//"ksession-rules");
        //kieSession.insert(sm_nodes.get(0));
        //kieSession.fireAllRules();
        kieSession.dispose();
	}

	@Override
	public void transferData(ruleData ruleData) {
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
}
