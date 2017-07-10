package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;
import com.RuleEngine.model.missingItemsList;

@Service
public class ValidateServiceImpl implements ValidateService{

	private ruleData ruleData = new ruleData();
    private ruleData missingRuleData = new ruleData();
	
	private KieContainer kieContainer;
	private KieScanner kieScanner;
	
	@Autowired
	Sm_linksService Sm_linksService;
	
	@Override
	public void cleanup(){
		ruleData = null;
		ruleData = new ruleData();
		missingRuleData = null;
		missingRuleData = new ruleData();
		if(kieScanner != null){
			kieScanner.stop();
			kieScanner.shutdown();
		}
		if(kieContainer != null){
			kieContainer.dispose();	
		}
	}
 
	@Override
	public void fireValidateRules() {
		KieServices ks = KieServices.Factory.get();
		kieContainer = ks.newKieContainer(ks.newReleaseId("com.RuleEngine", "ValidateRuleEngine", "1.0.0"));
    	kieScanner = ks.newKieScanner(kieContainer);
    	kieScanner.start(130000);
    	
    	missingItemsList missingItemsList = new missingItemsList();
    	
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.setGlobal("missingIdsList", missingItemsList);
		
		for(sm_dictionary dictionary : ruleData.getSm_dictionary() ){
			kieSession.insert(dictionary);
		}
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
 		List<sm_dictionary> d_temp = new ArrayList<sm_dictionary>();
		for(String name : missingItemsList.getMissingIds()){
			sm_dictionary temp = new sm_dictionary();
			temp.setName(name);
			temp.setDescription("");
			String[] t = new String[]{""};
			temp.setKey(t);
			temp.setId(new Long(0));
			d_temp.add(temp);
		}
		this.missingRuleData.setSm_dictionary(d_temp);
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
		
		this.ruleData.setSm_link(new sm_links(ruleData.getSm_link()));
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
		
		boolean changed_d = d_temp.removeAll(dataInserted.getSm_dictionary()); 	
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
		
		dataInserted.setSm_link(this.ruleData.getSm_link());
		
		if(changed_d 
				|| !dataInserted.getSm_link_properties().isEmpty()
				|| !dataInserted.getSm_node_properties().isEmpty()
				|| !dataInserted.getSm_nodes().isEmpty()
				|| !dataInserted.getSm_segment_properties().isEmpty()
				|| !dataInserted.getSm_segments().isEmpty())
			setData(dataInserted);
	}

	public ruleData getMissingRuleData() {
		return missingRuleData;
	}

	public void setMissingRuleData(ruleData missingRuleData) {
		this.missingRuleData = missingRuleData;
	}

	@Override
	public Long getNextSm_dictionaryId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_dictionary dictionary : ruleData.getSm_dictionary()){
			temp.add(dictionary.getId());
		}
		return (Collections.max(temp)+1);
	}

	@Override
	public Long getNextSm_nodesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_nodes node : ruleData.getSm_nodes()){
			temp.add(node.getId());
		}
		return (Collections.max(temp)+1);
	}

	@Override
	public Long getNextSm_segmentsId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_segments segment : ruleData.getSm_segments()){
			temp.add(segment.getId());
		}
		return (Collections.max(temp)+1);
	}

	@Override
	public Long getNextSm_node_propertiesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_node_properties prop : ruleData.getSm_node_properties()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}

	@Override
	public Long getNextSm_segment_propertiesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_segment_properties prop : ruleData.getSm_segment_properties()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}

	@Override
	public Long getNextSm_link_propertiesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_link_properties prop : ruleData.getSm_link_properties()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}

	@Override
	public sm_nodes getSm_nodes(Long id) {
		for(sm_nodes node : ruleData.getSm_nodes()){
			if(node.getId() == id){
				return node;
			}
		}
		return null;
	}
	
	@Override
	public sm_dictionary getSm_dictionary(Long id) {
		for(sm_dictionary dictionary : ruleData.getSm_dictionary()){ 
			if(dictionary.getId() == id){
				return dictionary;
			}
		}
		return null;
	}

	@Override
	public sm_links getSm_link() {
		return Sm_linksService.getChosenSm_link();
	}

	@Override
	public sm_segments getSm_segments(Long id) {
		for(sm_segments segment : ruleData.getSm_segments()){
			if(segment.getId() == id){
				return segment;
			}
		}
		return null;
	}
}
