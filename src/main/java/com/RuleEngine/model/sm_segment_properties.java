package com.RuleEngine.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "sm_segment_properties")
public class sm_segment_properties {
	
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="segment_id")
    private sm_segments segment_id;
       
    @Column(name="tags")
    @Type(type = "com.RuleEngine.types.StringArrayType")
    private String[] tags;
    
    @Column(name="description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    private sm_dictionary dictionary_id;

    public sm_segment_properties() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public sm_segments getSegment_id() {
		return segment_id;
	}

	public void setSegment_id(sm_segments segment_id) {
		this.segment_id = segment_id;
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
