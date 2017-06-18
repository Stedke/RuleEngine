package com.RuleEngine.model;

import java.util.List;

public class ruleData {

	private List<sm_nodes> sm_nodes;
	private List<sm_segments> sm_segments;
	private List<sm_dictionary> sm_dictionary;
	private List<sm_node_properties> sm_node_properties;
	private List<sm_segment_properties> sm_segment_properties;
	private List<sm_link_properties> sm_link_properties;
	private Long linkId;
	
	public List<sm_nodes> getSm_nodes() {
		return sm_nodes;
	}
	public void setSm_nodes(List<sm_nodes> sm_nodes) {
		this.sm_nodes = sm_nodes;
	}
	public List<sm_segments> getSm_segments() {
		return sm_segments;
	}
	public void setSm_segments(List<sm_segments> sm_segments) {
		this.sm_segments = sm_segments;
	}
	public List<sm_dictionary> getSm_dictionary() {
		return sm_dictionary;
	}
	public void setSm_dictionary(List<sm_dictionary> sm_dictionary) {
		this.sm_dictionary = sm_dictionary;
	}
	public List<sm_node_properties> getSm_node_properties() {
		return sm_node_properties;
	}
	public void setSm_node_properties(List<sm_node_properties> sm_node_properties) {
		this.sm_node_properties = sm_node_properties;
	}
	public List<sm_segment_properties> getSm_segment_properties() {
		return sm_segment_properties;
	}
	public void setSm_segment_properties(List<sm_segment_properties> sm_segment_properties) {
		this.sm_segment_properties = sm_segment_properties;
	}
	public List<sm_link_properties> getSm_link_properties() {
		return sm_link_properties;
	}
	public void setSm_link_properties(List<sm_link_properties> sm_link_properties) {
		this.sm_link_properties = sm_link_properties;
	}
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
}
