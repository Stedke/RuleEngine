package com.RuleEngine.model;

import javax.persistence.*;
import java.util.Set;

import com.vividsolutions.jts.geom.LineString;

@Entity
@Table(name = "sm_segments")
public class sm_segments {
	
    @Id
    @GeneratedValue
    private Long id;
          
    @ManyToOne
    @JoinColumn(name = "link_id")
    private sm_links link_id;
    
    @ManyToOne
    @JoinColumn(name = "start_node")
    private sm_nodes start_node;
    
    @ManyToOne
    @JoinColumn(name = "end_node")
    private sm_nodes end_node;    
    
    @Column(name="geom")
    private LineString geom;
    
    @OneToMany(mappedBy="segment_id",cascade=CascadeType.ALL)
    private Set<sm_segment_properties> segment_id;

    public sm_segments() {};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public sm_links getLink_id() {
		return link_id;
	}

	public void setLink_id(sm_links link_id) {
		this.link_id = link_id;
	}

	public sm_nodes getStart_node() {
		return start_node;
	}

	public void setStart_node(sm_nodes start_node) {
		this.start_node = start_node;
	}

	public sm_nodes getEnd_node() {
		return end_node;
	}

	public void setEnd_node(sm_nodes end_node) {
		this.end_node = end_node;
	}

	public LineString getGeom() {
		return geom;
	}

	public void setGeom(LineString geom) {
		this.geom = geom;
	}

	public Set<sm_segment_properties> getSegment_id() {
		return segment_id;
	}

	public void setSegment_id(Set<sm_segment_properties> segment_id) {
		this.segment_id = segment_id;
	}

	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}

