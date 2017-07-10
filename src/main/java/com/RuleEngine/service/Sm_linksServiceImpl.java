package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RuleEngine.dao.sm_linksDAO;
import com.RuleEngine.model.sm_links;
import com.vividsolutions.jts.geom.Polygon;

@Service
public class Sm_linksServiceImpl implements Sm_linksService{
	@Autowired
	private sm_linksDAO sm_linksDAO;
	
	private List<sm_links> sm_Links;
	
	private Integer chosenSm_linkId;
	
	@Override
	public void cleanup(){
		sm_Links.clear();
		if(chosenSm_linkId != null){
			chosenSm_linkId = null;
		}
	}

	@Transactional
	public void getSm_links(ArrayList<String> linksArea) {
		sm_Links = sm_linksDAO.getSm_links(linksArea);
	}
	@Override
	public List<sm_links> getChosenSm_Links() {
		return sm_Links;
	}
	@Override
	public boolean setChosenSm_linkId(Integer chosenSm_linkId) {
		
		boolean isValid = false;
		for(sm_links link : sm_Links){
			if(link.getId() == chosenSm_linkId.longValue()){	
				isValid=true;
				break;
			}
		}
		
		if(isValid){
			this.chosenSm_linkId = chosenSm_linkId;
	        return true;
		}else
			return false;
	}
	@Transactional
	public sm_links getChosenSm_link() {
		List<sm_links> links = sm_linksDAO.getChosenSm_link(chosenSm_linkId);
		return links.get(0);
	}
}
