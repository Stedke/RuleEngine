package com.RuleEngine.model;

import java.util.List;

public class chosenSm_link {
	Integer chosenSm_Link;
	List<sm_links> allSm_Links;
	
	public List<sm_links> getAllSm_Links() {
		return allSm_Links;
	}
	public void setAllSm_Links(List<sm_links> allSm_Links) {
		this.allSm_Links = allSm_Links;
	}
	public Integer getChosenSm_Link() {
		return chosenSm_Link;
	}
	public void setChosenSm_Link(Integer chosenSm_Link) {
		this.chosenSm_Link = chosenSm_Link;
	}
}
