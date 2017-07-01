package com.RuleEngine.service;

import java.util.List;

import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;

public interface PreloadService {

	public void preloadData(sm_links sm_link);
	public List<sm_link_properties> getSm_link_properties();
	public void setSm_link_properties(List<sm_link_properties> sm_link_properties);
	public List<sm_dictionary> getSm_dictionary();
	public void setSm_dictionary(List<sm_dictionary> sm_dictionary);
	public List<sm_segment_properties> getSm_segment_properties();
	public void setSm_segment_properties(List<sm_segment_properties> sm_segment_properties);
	public List<sm_node_properties> getSm_node_properties();
	public void setSm_node_properties(List<sm_node_properties> sm_node_properties);
	public List<sm_segments> getSm_segments();
	public void setSm_segments(List<sm_segments> sm_segments);
	public List<sm_nodes> getSm_nodes();
	public void setSm_nodes(List<sm_nodes> sm_nodes);
	public ruleData transferData();
}
