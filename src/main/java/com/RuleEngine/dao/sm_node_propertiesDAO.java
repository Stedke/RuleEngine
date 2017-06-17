package com.RuleEngine.dao;

import java.util.List;

import com.RuleEngine.model.sm_node_properties;

public interface sm_node_propertiesDAO {
	public List<sm_node_properties> getSm_node_properties(Long nodeId);
}
