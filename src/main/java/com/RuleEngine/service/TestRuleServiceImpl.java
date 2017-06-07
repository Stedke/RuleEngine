package com.RuleEngine.service;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.RuleEngine.model.sm_nodes;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TestRuleServiceImpl implements TestRuleService {

	public List<sm_nodes> sm_nodes;
	
	@Autowired
	private KieContainer kieContainer;
	
	public void testDrools(){
        KieSession kieSession = kieContainer.newKieSession();//"ksession-rules");
        kieSession.insert(sm_nodes.get(0));
        kieSession.fireAllRules();
        kieSession.dispose();
	}

	public void setSm_nodes(List<sm_nodes> sm_nodes){
		this.sm_nodes = sm_nodes;
	}
}
