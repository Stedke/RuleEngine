package com.RuleEngine.dao;

import java.util.List;

import com.RuleEngine.model.sm_segment_properties;

public interface sm_segment_propertiesDAO {
	public List<sm_segment_properties> getSm_segment_properties(Long segmentId);
}
