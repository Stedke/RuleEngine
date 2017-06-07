package com.RuleEngine.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "sm_node_properties")
public class sm_node_properties {
	
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "node_id")
    private sm_nodes node_id;
       
    @Column(name="tags")
    @Type(type = "com.RuleEngine.types.StringArrayType")
    private String[] tags;
    
    @Column(name="description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name="dictionary_id")
    private sm_dictionary dictionary_id;

    public sm_node_properties() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public sm_nodes getNode_id() {
		return node_id;
	}

	public void setNode_id(sm_nodes node_id) {
		this.node_id = node_id;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public sm_dictionary getDictionary_id() {
		return dictionary_id;
	}

	public void setDictionary_id(sm_dictionary dictionary_id) {
		this.dictionary_id = dictionary_id;
	}

	@Override
    public String toString() {
        return String.format("User id: %d", getId());
    }
}