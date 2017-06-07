package com.RuleEngine.model;

import javax.persistence.*;
import java.util.Set;

import com.vividsolutions.jts.geom.LineString;

@Entity
@Table(name = "sm_links")
public class sm_links {
	
    @Id
    @GeneratedValue
    private Long id;
        
    @ManyToOne
    @JoinColumn(name = "way_id")
    private planet_osm_ways way_id;
    
    @ManyToOne
    @JoinColumn(name = "osm_start_node")
    private planet_osm_nodes osm_start_node;
    
    @ManyToOne
    @JoinColumn(name = "osm_end_node")
    private planet_osm_nodes osm_end_node;
    
    @Column(name="geom")
    private LineString geom;
    
    @OneToMany(mappedBy="link_id",cascade=CascadeType.ALL)
    private Set<sm_segments> link_id;
    @OneToMany(mappedBy="link_id",cascade=CascadeType.ALL)
    private Set<sm_link_properties> link_properties;

    public sm_links() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public planet_osm_ways getWay_id() {
		return way_id;
	}

	public void setWay_id(planet_osm_ways way_id) {
		this.way_id = way_id;
	}

	public planet_osm_nodes getOsm_start_node() {
		return osm_start_node;
	}

	public void setOsm_start_node(planet_osm_nodes osm_start_node) {
		this.osm_start_node = osm_start_node;
	}

	public planet_osm_nodes getOsm_end_node() {
		return osm_end_node;
	}

	public void setOsm_end_node(planet_osm_nodes osm_end_node) {
		this.osm_end_node = osm_end_node;
	}

	public LineString getGeom() {
		return geom;
	}

	public void setGeom(LineString geom) {
		this.geom = geom;
	}

	public Set<sm_segments> getLink_id() {
		return link_id;
	}

	public void setLink_id(Set<sm_segments> link_id) {
		this.link_id = link_id;
	}

	public Set<sm_link_properties> getLink_properties() {
		return link_properties;
	}

	public void setLink_properties(Set<sm_link_properties> link_properties) {
		this.link_properties = link_properties;
	}

	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}
