package com.RuleEngine.model;

import javax.persistence.*;
import java.util.Set;


import org.hibernate.annotations.Type;

@Entity
@Table(name = "sm_dictionary")
public class sm_dictionary {
	
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="description")
    private String description;
    
    @Column(name="key")
    @Type(type = "com.RuleEngine.types.StringArrayType")
    private String[] key;

    @Column(name="datatypes")
    @Type(type = "com.RuleEngine.types.StringArrayType")
    private String[] datatypes;
       
    @Column(name="required")
    @Type(type = "com.RuleEngine.types.BooleanArrayType")
    private String[] required;
    
    @OneToMany(mappedBy="dictionary_id",cascade=CascadeType.ALL)
    private Set<sm_link_properties> dictionary_id_links;
    @OneToMany(mappedBy="dictionary_id",cascade=CascadeType.ALL)
    private Set<sm_segment_properties> dictionary_id_segments;
    @OneToMany(mappedBy="dictionary_id",cascade=CascadeType.ALL)
    private Set<sm_node_properties> dictionary_id_nodes;

    public sm_dictionary() {}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String[] getKey() {
		return key;
	}



	public void setKey(String[] key) {
		this.key = key;
	}



	public String[] getDatatypes() {
		return datatypes;
	}



	public void setDatatypes(String[] datatypes) {
		this.datatypes = datatypes;
	}



	public String[] getRequired() {
		return required;
	}



	public void setRequired(String[] required) {
		this.required = required;
	}



	public Set<sm_link_properties> getDictionary_id_links() {
		return dictionary_id_links;
	}



	public void setDictionary_id_links(Set<sm_link_properties> dictionary_id_links) {
		this.dictionary_id_links = dictionary_id_links;
	}



	public Set<sm_segment_properties> getDictionary_id_segments() {
		return dictionary_id_segments;
	}



	public void setDictionary_id_segments(Set<sm_segment_properties> dictionary_id_segments) {
		this.dictionary_id_segments = dictionary_id_segments;
	}



	public Set<sm_node_properties> getDictionary_id_nodes() {
		return dictionary_id_nodes;
	}



	public void setDictionary_id_nodes(Set<sm_node_properties> dictionary_id_nodes) {
		this.dictionary_id_nodes = dictionary_id_nodes;
	}



	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}
