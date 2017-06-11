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

	@Transactional
	public void getSm_links(ArrayList<String> linksArea) {
		sm_Links = sm_linksDAO.getSm_links(linksArea);
		
	       for(sm_links link : sm_Links) {
	           System.out.println(link.toString());
	       }
	}
	@Override
	public List<sm_links> getChosenSm_Links() {
		return sm_Links;
	}
	@Override
	public void setChosenSm_linkId(Integer chosenSm_linkId) {
		this.chosenSm_linkId = chosenSm_linkId;
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println(chosenSm_linkId.toString());
	}
}
