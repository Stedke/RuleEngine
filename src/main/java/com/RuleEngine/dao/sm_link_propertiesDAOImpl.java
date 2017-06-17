package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_link_properties;

@Repository
public class sm_link_propertiesDAOImpl implements sm_link_propertiesDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<sm_link_properties> getSm_link_properties(Long linkId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("from sm_link_properties where link_id=");
		stringBuilder.append(linkId.toString());
		return getCurrentSession().createQuery(stringBuilder.toString()).list();
	}
}
