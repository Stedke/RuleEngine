package com.RuleEngine.model;

public class sm_nodeImpact {

	sm_nodeImpactEnum impact = sm_nodeImpactEnum.AREA;
	Double value = new Double(0);
	
	public sm_nodeImpact(sm_nodeImpactEnum impact, Double value){
		this.impact = impact;
		this.value = new Double(value);
	}
	
	public sm_nodeImpactEnum getImpact() {
		return impact;
	}
	public void setImpact(sm_nodeImpactEnum impact) {
		this.impact = impact;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
