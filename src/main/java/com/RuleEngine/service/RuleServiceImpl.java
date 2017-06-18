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

	public ruleData ruleData;
	
	
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
			this.ruleData = ruleData;
	}
}
