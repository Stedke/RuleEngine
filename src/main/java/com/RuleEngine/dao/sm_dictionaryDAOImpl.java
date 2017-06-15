package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_links;

@Repository
public class sm_dictionaryDAOImpl implements sm_dictionaryDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public sm_dictionary getSm_dictionary(Long dictionaryId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_dictionary AS dictionary WHERE dictionary.id = ");
		stringBuilder.append(dictionaryId.toString());
		
		String query = stringBuilder.toString();
		System.out.println(query);
		
		List<sm_dictionary> temp = getCurrentSession().createSQLQuery(query).addEntity(sm_dictionary.class).list();
		return temp.get(0);
	}
}
