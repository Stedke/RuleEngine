package com.RuleEngine.service;

import java.util.List;

import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;

public interface PreloadService {

	public void preloadData(Long linkId);
	public List<sm_link_properties> getSm_link_properties();
	public void setSm_link_properties(List<sm_link_properties> sm_link_properties);
	public List<sm_dictionary> getSm_dictionary();
	public void setSm_dictionary(List<sm_dictionary> sm_dictionary);
}
