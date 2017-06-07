package com.RuleEngine.model;

import javax.persistence.*;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "sm_nodes")
public class sm_nodes {
	
    @Id
    @GeneratedValue
    private Long id;
        
    @Column(name="geom")
    private Point geom;
   
    @ManyToOne
    @JoinColumn(name = "osm_node")
    private planet_osm_nodes osm_node;
    
    @OneToMany(mappedBy="start_node",cascade=CascadeType.ALL)
    private Set<sm_segments> start_node;
    @OneToMany(mappedBy="end_node",cascade=CascadeType.ALL)
    private Set<sm_segments> end_node;
    @OneToMany(mappedBy="node_id",cascade=CascadeType.ALL)
    private Set<sm_node_properties> node_id;

    public sm_nodes() {};
       
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Point getGeom() {
		return geom;
	}

	public void setGeom(Point geom) {
		this.geom = geom;
	}

	public planet_osm_nodes getOsm_node() {
		return osm_node;
	}

	public void setOsm_node(planet_osm_nodes osm_node) {
		this.osm_node = osm_node;
	}

	public Set<sm_segments> getStart_node() {
		return start_node;
	}

	public void setStart_node(Set<sm_segments> start_node) {
		this.start_node = start_node;
	}

	public Set<sm_segments> getEnd_node() {
		return end_node;
	}

	public void setEnd_node(Set<sm_segments> end_node) {
		this.end_node = end_node;
	}

	public Set<sm_node_properties> getNode_id() {
		return node_id;
	}

	public void setNode_id(Set<sm_node_properties> node_id) {
		this.node_id = node_id;
	}

	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}

