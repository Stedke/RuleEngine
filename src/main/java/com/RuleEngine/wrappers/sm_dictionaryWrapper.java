package com.RuleEngine.wrappers;

public class sm_dictionaryWrapper {
    
	private String id = new String();
    private String name = new String();
    private String description = new String();
    private String key = new String();
    private String datatypes = new String();
    private String required = new String();
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDatatypes() {
		return datatypes;
	}
	public void setDatatypes(String datatypes) {
		this.datatypes = datatypes;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
}
