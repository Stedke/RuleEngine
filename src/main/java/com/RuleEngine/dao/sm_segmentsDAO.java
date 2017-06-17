package com.RuleEngine.dao;

import java.util.List;

import com.RuleEngine.model.sm_segments;

public interface sm_segmentsDAO {
	public List<sm_segments> getSm_segments(Long linkId);
}
