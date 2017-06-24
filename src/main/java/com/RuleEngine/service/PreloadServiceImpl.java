package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RuleEngine.dao.sm_link_propertiesDAO;
import com.RuleEngine.dao.sm_segment_propertiesDAO;
import com.RuleEngine.dao.sm_node_propertiesDAO;
import com.RuleEngine.dao.sm_dictionaryDAO;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_segments;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.dao.sm_segmentsDAO;
import com.RuleEngine.dao.sm_nodesDAO;

@Service
public class PreloadServiceImpl implements PreloadService {

	@Autowired
	private sm_link_propertiesDAO sm_link_propertiesDAO;
	@Autowired
	private sm_segment_propertiesDAO sm_segment_propertiesDAO;
	@Autowired
	private sm_node_propertiesDAO sm_node_propertiesDAO;
	@Autowired
	private sm_dictionaryDAO sm_dictionaryDAO;
	@Autowired
	private sm_segmentsDAO sm_segmentsDAO;
	@Autowired
	private sm_nodesDAO sm_nodesDAO;
	
	private List<sm_link_properties> sm_link_properties;
	private List<sm_segments> sm_segments;
	private List<sm_nodes> sm_nodes = new ArrayList<sm_nodes>();
	private List<sm_segment_properties> sm_segment_properties = new ArrayList<sm_segment_properties>();
	private List<sm_node_properties> sm_node_properties = new ArrayList<sm_node_properties>();
	private List<sm_dictionary> sm_dictionary = new ArrayList<sm_dictionary>();
	private Long linkId;

	@Transactional
	public void preloadData(Long linkId){
		this.linkId = linkId;
		
		sm_link_properties = sm_link_propertiesDAO.getSm_link_properties(linkId);
		
		sm_segments = sm_segmentsDAO.getSm_segments(linkId);
		
		sm_nodes = sm_nodesDAO.getSm_nodesFromLink(linkId);
		sm_nodes.addAll(sm_nodesDAO.getSm_nodesFromLinkProximity(linkId, new Integer(5)));
		
		for(sm_segments segments : sm_segments){
			sm_segment_properties.addAll(sm_segment_propertiesDAO.getSm_segment_properties(segments.getId()));
			sm_nodes.addAll(sm_nodesDAO.getSm_nodesFromSegment(segments));
			sm_nodes.addAll(sm_nodesDAO.getSm_nodeFromId(segments.getStart_node().getId()));
			sm_nodes.addAll(sm_nodesDAO.getSm_nodeFromId(segments.getEnd_node().getId()));
		}
		
		Set<sm_nodes> temp = new HashSet<sm_nodes>(sm_nodes);
		sm_nodes =  new ArrayList<sm_nodes>(temp);
		
		for(sm_nodes nodes : sm_nodes){
			sm_node_properties.addAll(sm_node_propertiesDAO.getSm_node_properties(nodes.getId()));
		}
		
		HashMap<Long,Long> dictionary_id = new HashMap<Long,Long>();
		
	       for(sm_link_properties links_properties : sm_link_properties) {
	    	   dictionary_id.put(links_properties.getDictionary_id().getId(), links_properties.getDictionary_id().getId());
	       }
	       for(sm_segment_properties segment_properties : sm_segment_properties) {
	    	   dictionary_id.put(segment_properties.getDictionary_id().getId(), segment_properties.getDictionary_id().getId());
	       }
	       for(sm_node_properties node_properties : sm_node_properties) {
	    	   dictionary_id.put(node_properties.getDictionary_id().getId(), node_properties.getDictionary_id().getId());
	       }
	       
	       for(Long d_id : dictionary_id.keySet()) {
	    	   sm_dictionary.add(sm_dictionaryDAO.getSm_dictionary(d_id));
	       }
	}
	
	@Override
	public ruleData transferData() {
		ruleData temp = new ruleData();
		temp.setSm_dictionary(sm_dictionary);
		temp.setSm_link_properties(sm_link_properties);
		temp.setSm_node_properties(sm_node_properties);
		temp.setSm_nodes(sm_nodes);
		temp.setSm_segment_properties(sm_segment_properties);
		temp.setSm_segments(sm_segments);
		temp.setLinkId(linkId);
		return temp;
	}

	public List<sm_link_properties> getSm_link_properties() {
		return sm_link_properties;
	}

	public void setSm_link_properties(List<sm_link_properties> sm_link_properties) {
		this.sm_link_properties = sm_link_properties;
	}

	public List<sm_dictionary> getSm_dictionary() {
		return sm_dictionary;
	}

	public void setSm_dictionary(List<sm_dictionary> sm_dictionary) {
		this.sm_dictionary = sm_dictionary;
	}

	public List<sm_segment_properties> getSm_segment_properties() {
		return sm_segment_properties;
	}

	public void setSm_segment_properties(List<sm_segment_properties> sm_segment_properties) {
		this.sm_segment_properties = sm_segment_properties;
	}

	public List<sm_node_properties> getSm_node_properties() {
		return sm_node_properties;
	}

	public void setSm_node_properties(List<sm_node_properties> sm_node_properties) {
		this.sm_node_properties = sm_node_properties;
	}

	public List<sm_segments> getSm_segments() {
		return sm_segments;
	}

	public void setSm_segments(List<sm_segments> sm_segments) {
		this.sm_segments = sm_segments;
	}

	public List<sm_nodes> getSm_nodes() {
		return sm_nodes;
	}

	public void setSm_nodes(List<sm_nodes> sm_nodes) {
		this.sm_nodes = sm_nodes;
	}
}
