package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RuleEngine.dao.sm_link_propertiesDAO;
import com.RuleEngine.dao.sm_segment_propertiesDAO;
import com.RuleEngine.dao.sm_dictionaryDAO;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_dictionary;

@Service
public class PreloadServiceImpl implements PreloadService {

	@Autowired
	private sm_link_propertiesDAO sm_link_propertiesDAO;
	@Autowired
	private sm_segment_propertiesDAO sm_segment_propertiesDAO;
	@Autowired
	private sm_dictionaryDAO sm_dictionaryDAO;
	
	private List<sm_link_properties> sm_link_properties;
	private List<sm_segment_properties> sm_segment_properties;
	private List<sm_dictionary> sm_dictionary = new ArrayList<sm_dictionary>();

	@Transactional
	public void preloadData(Long linkId){
		sm_link_properties = sm_link_propertiesDAO.getSm_link_properties(linkId);
		sm_segment_properties = sm_segment_propertiesDAO.getSm_segment_properties(linkId);
		HashMap<Long,Long> dictionary_id = new HashMap<Long,Long>();
		
	       for(sm_link_properties links_properties : sm_link_properties) {
	    	   dictionary_id.put(links_properties.getDictionary_id().getId(), links_properties.getDictionary_id().getId());
	       }
	       for(sm_segment_properties segment_properties : sm_segment_properties) {
	    	   dictionary_id.put(segment_properties.getDictionary_id().getId(), segment_properties.getDictionary_id().getId());
	       }
	       
	       for(Long d_id : dictionary_id.keySet()) {
	    	   sm_dictionary.add(sm_dictionaryDAO.getSm_dictionary(d_id));
	       }
	       
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
}
