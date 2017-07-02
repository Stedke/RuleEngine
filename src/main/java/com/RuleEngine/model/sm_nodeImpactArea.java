package com.RuleEngine.model;

import java.util.ArrayList;
import java.util.List;

public class sm_nodeImpactArea {

	List<Tuple<String,sm_nodeImpact>> listOfNodes = new ArrayList<Tuple<String,sm_nodeImpact>>();

	public void addNewNode(String dictionaryName,sm_nodeImpactEnum impact, Double value){
		listOfNodes.add(new Tuple<String,sm_nodeImpact>(new String(dictionaryName),new sm_nodeImpact(impact,value)));
	}
	
	public List<Tuple<String, sm_nodeImpact>> getListOfNodes() {
		return listOfNodes;
	}
	public void setListOfNodes(List<Tuple<String, sm_nodeImpact>> listOfNodes) {
		this.listOfNodes = listOfNodes;
	}
}
