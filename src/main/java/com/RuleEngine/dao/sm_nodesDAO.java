package com.RuleEngine.dao;

import java.util.List;

import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segments;

public interface sm_nodesDAO {

	public List<sm_nodes> getSm_nodeFromId(Long nodeId);
	public List<sm_nodes> getSm_nodesFromLink(Long linkId);
	public List<sm_nodes> getSm_nodesFromSegment(sm_segments segment);
	public List<sm_nodes> getSm_nodesFromLinkProximity(Long linkId,Integer distance);	
}