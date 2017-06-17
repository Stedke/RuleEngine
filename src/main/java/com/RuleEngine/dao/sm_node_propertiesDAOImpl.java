package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_node_properties;

@Repository
public class sm_node_propertiesDAOImpl implements sm_node_propertiesDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<sm_node_properties> getSm_node_properties(Long nodeId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_node_properties AS properties WHERE properties.node_id = ");
		stringBuilder.append(nodeId.toString()); 
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_node_properties.class).list();
	}
}
