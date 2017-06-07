package com.RuleEngine.model;

import javax.persistence.*;

import java.util.Set;
import org.hibernate.annotations.Type;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "planet_osm_nodes")
public class planet_osm_nodes {
	
    @Id
    @GeneratedValue
    private Long id;
       
    @Column (name = "tags")
    @Type(type = "com.RuleEngine.types.StringArrayType")
    private String[] tags;
    
    @Column (name = "geom")
    private Point geom;
    
    @OneToMany(mappedBy="osm_node",cascade=CascadeType.ALL)
    private Set<sm_nodes> sm_nodes;
    @OneToMany(mappedBy="osm_start_node",cascade=CascadeType.ALL)
    private Set<sm_links> osm_start_nodes;
    @OneToMany(mappedBy="osm_end_node",cascade=CascadeType.ALL)
    private Set<sm_links> osm_end_nodes;

    public planet_osm_nodes() {}
    

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String[] getTags() {
		return tags;
	}


	public void setTags(String[] tags) {
		this.tags = tags;
	}


	public Point getGeom() {
		return geom;
	}


	public void setGeom(Point geom) {
		this.geom = geom;
	}


	public Set<sm_nodes> getSm_nodes() {
		return sm_nodes;
	}


	public void setSm_nodes(Set<sm_nodes> sm_nodes) {
		this.sm_nodes = sm_nodes;
	}


	public Set<sm_links> getOsm_start_nodes() {
		return osm_start_nodes;
	}


	public void setOsm_start_nodes(Set<sm_links> osm_start_nodes) {
		this.osm_start_nodes = osm_start_nodes;
	}


	public Set<sm_links> getOsm_end_nodes() {
		return osm_end_nodes;
	}


	public void setOsm_end_nodes(Set<sm_links> osm_end_nodes) {
		this.osm_end_nodes = osm_end_nodes;
	}


	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}
