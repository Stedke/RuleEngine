package com.RuleEngine.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_links;
import com.vividsolutions.jts.geom.Polygon;

@Repository
public class sm_linksDAOImpl implements sm_linksDAO  {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<sm_links> getSm_links(ArrayList<String> linksArea) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_links WHERE ST_Intersects(geom, ST_GeomFromText('POLYGON(");
		stringBuilder.append(linksArea.get(0));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(1));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(2));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(3));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(4));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(5));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(6));
		stringBuilder.append(" ");
		stringBuilder.append(linksArea.get(7));
		stringBuilder.append(")'))");
		String polygon = stringBuilder.toString();
		System.out.println(polygon);
		return getCurrentSession().createQuery(polygon).list();
	}
}
