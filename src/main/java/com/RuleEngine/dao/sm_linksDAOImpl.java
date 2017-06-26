package com.RuleEngine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_links;

@Repository
public class sm_linksDAOImpl implements sm_linksDAO  {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<sm_links> getSm_links(ArrayList<String> linksArea) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_links AS links WHERE ST_Intersects(links.geom, ST_GeomFromText('POLYGON((");
		stringBuilder.append(linksArea.get(0));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(1));
		stringBuilder.append(", ");
		stringBuilder.append(linksArea.get(2));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(3));
		stringBuilder.append(", ");
		stringBuilder.append(linksArea.get(4));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(5));
		stringBuilder.append(", ");
		stringBuilder.append(linksArea.get(6));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(7));
		stringBuilder.append(", ");
		stringBuilder.append(linksArea.get(0));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(1));
		stringBuilder.append("))'))");
		String polygon = stringBuilder.toString();
		System.out.println(polygon);
		return getCurrentSession().createSQLQuery(polygon).addEntity(sm_links.class).list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<sm_links> getChosenSm_link(Integer linkId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_links AS links WHERE links.id = ");
		stringBuilder.append(linkId.toString());
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_links.class).list();
	}
}
