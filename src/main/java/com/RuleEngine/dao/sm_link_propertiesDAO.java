package com.RuleEngine.dao;

import java.util.List;

import com.RuleEngine.model.sm_link_properties;

public interface sm_link_propertiesDAO {
	public List<sm_link_properties> getSm_link_properties(Long linkId);
}
