package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_nodes;

@Repository
public class sm_nodesDAOImpl implements sm_nodesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<sm_nodes> getSm_nodes() {
		return getCurrentSession().createQuery("from sm_nodes").list();
	}

}

