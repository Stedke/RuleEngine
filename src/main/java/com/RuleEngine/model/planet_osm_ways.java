package com.RuleEngine.model;

import javax.persistence.*;
import java.util.Set;


import org.hibernate.annotations.Type;
import com.vividsolutions.jts.geom.LineString;

@Entity
@Table(name = "planet_osm_ways")
public class planet_osm_ways {
	
    @Id
    @GeneratedValue
    private Long id;
       
    @Column(name="tags")
    @Type(type = "com.RuleEngine.types.StringArrayType")
    private String[] tags;
    
    @Column(name="geom")
    private LineString geom;
    @Column(name="nodes")
    @Type(type = "com.RuleEngine.types.BigIntArrayType")
    private Long[] nodes;
    @OneToMany(mappedBy="way_id",cascade=CascadeType.ALL)
    private Set<sm_links> way_id;

    public planet_osm_ways() {}



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

	public LineString getGeom() {
		return geom;
	}

	public void setGeom(LineString geom) {
		this.geom = geom;
	}

	public Set<sm_links> getWay_id() {
		return way_id;
	}

	public void setWay_id(Set<sm_links> way_id) {
		this.way_id = way_id;
	}

	public Long[] getNodes() {
		return nodes;
	}

	public void setNodes(Long[] nodes) {
		this.nodes = nodes;
	}

	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}
