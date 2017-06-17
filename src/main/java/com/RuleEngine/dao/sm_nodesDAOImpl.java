package com.RuleEngine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segments;

@Repository
public class sm_nodesDAOImpl implements sm_nodesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<sm_nodes> getSm_nodeFromId(Long nodeId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_nodes AS nodes WHERE nodes.id = ");
		stringBuilder.append(nodeId.toString()); 
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_nodes.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<sm_nodes> getSm_nodesFromLink(Long linkId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_nodes AS nodes WHERE ST_Intersects(nodes.geom, ");
		stringBuilder.append("(SELECT geom FROM sm_links AS links WHERE links.id = ");
		stringBuilder.append(linkId.toString());
		stringBuilder.append("))");
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_nodes.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<sm_nodes> getSm_nodesFromSegment(sm_segments segment) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_nodes AS nodes WHERE ST_Intersects(nodes.geom, '");
		stringBuilder.append(segment.getGeom().toString());
		stringBuilder.append("')");
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_nodes.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<sm_nodes> getSm_nodesFromLinkProximity(Long linkId, Integer distance) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM sm_nodes AS nodes WHERE ST_DWithin(nodes.geom, ");
		stringBuilder.append("(SELECT geom FROM sm_links AS links WHERE links.id = ");
		stringBuilder.append(linkId.toString());
		stringBuilder.append("), ");
		stringBuilder.append(distance.toString());
		stringBuilder.append(")");
		return getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_nodes.class).list();
	}
}

