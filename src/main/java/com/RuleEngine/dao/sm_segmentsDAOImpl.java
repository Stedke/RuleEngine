package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.RuleEngine.model.sm_segments;

@Repository
public class sm_segmentsDAOImpl implements sm_segmentsDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<sm_segments> getSm_segments(Long linkId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_segments AS segments WHERE segments.link_id = ");
		stringBuilder.append(linkId.toString()); 
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_segments.class).list();
	}

}
