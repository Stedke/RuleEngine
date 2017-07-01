package com.RuleEngine.model;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

public class sm_linkAreasData {
	private Double velocity;
	private Point start_point;
	private Point end_point;
	private List<sm_segments> sm_segments;
	private List<sm_segment_properties> sm_segment_properties;
	private List<sm_nodes> sm_nodes;
	private List<sm_node_properties> sm_node_properties;
	private List<sm_link_properties> sm_link_properties;
	
	public Point getStart_point() {
		return start_point;
	}
	public void setStart_point(Point start_point) {
		this.start_point = start_point;
	}
	public Point getEnd_point() {
		return end_point;
	}
	public void setEnd_point(Point end_point) {
		this.end_point = end_point;
	}
	public Double getVelocity() {
		return velocity;
	}
	public void setVelocity(Double velocity) {
		this.velocity = velocity;
	}
	public List<sm_segments> getSm_segments() {
		return sm_segments;
	}
	public void setSm_segments(List<sm_segments> sm_segments) {
		this.sm_segments = sm_segments;
	}
	public List<sm_segment_properties> getSm_segment_properties() {
		return sm_segment_properties;
	}
	public void setSm_segment_properties(List<sm_segment_properties> sm_segment_properties) {
		this.sm_segment_properties = sm_segment_properties;
	}
	public List<sm_nodes> getSm_nodes() {
		return sm_nodes;
	}
	public void setSm_nodes(List<sm_nodes> sm_nodes) {
		this.sm_nodes = sm_nodes;
	}
	public List<sm_node_properties> getSm_node_properties() {
		return sm_node_properties;
	}
	public void setSm_node_properties(List<sm_node_properties> sm_node_properties) {
		this.sm_node_properties = sm_node_properties;
	}
	public List<sm_link_properties> getSm_link_properties() {
		return sm_link_properties;
	}
	public void setSm_link_properties(List<sm_link_properties> sm_link_properties) {
		this.sm_link_properties = sm_link_properties;
	}
}
