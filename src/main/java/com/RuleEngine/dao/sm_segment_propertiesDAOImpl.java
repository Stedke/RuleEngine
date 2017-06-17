package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.RuleEngine.model.sm_segment_properties;

@Repository
public class sm_segment_propertiesDAOImpl implements sm_segment_propertiesDAO{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<sm_segment_properties> getSm_segment_properties(Long linkId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_segment_properties AS properties WHERE properties.segment_id = (SELECT id FROM sm_segments AS segments WHERE segments.link_id = ");
		stringBuilder.append(linkId.toString());
		stringBuilder.append(")");	 
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_segment_properties.class).list();
	}

}
