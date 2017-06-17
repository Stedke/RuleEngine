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
		stringBuilder.append("SELECT * FROM sm_segments AS segments WHERE ST_DWithin(segments.geom, ");
		stringBuilder.append("(SELECT geom FROM sm_links AS links WHERE links.id = ");
		stringBuilder.append(linkId.toString());
		stringBuilder.append("), ");
		stringBuilder.append(distance.toString());
		stringBuilder.append(")");
		List<sm_segments> segments = getCurrentSession().createSQLQuery(stringBuilder.toString()).addEntity(sm_segments.class).list();
		
		StringBuilder stringBuilder1 = new StringBuilder();
		stringBuilder1.append("SELECT * FROM sm_links AS links WHERE ST_DWithin(links.geom, ");
		stringBuilder1.append("(SELECT geom FROM sm_links AS links WHERE links.id = ");
		stringBuilder1.append(linkId.toString());
		stringBuilder1.append("), ");
		stringBuilder1.append(distance.toString());
		stringBuilder1.append(")");
		List<sm_links> links = getCurrentSession().createSQLQuery(stringBuilder1.toString()).addEntity(sm_links.class).list();
		
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append("SELECT * FROM sm_nodes AS nodes WHERE ST_DWithin(nodes.geom, ");
		stringBuilder2.append("(SELECT geom FROM sm_links AS links WHERE links.id = ");
		stringBuilder2.append(linkId.toString());
		stringBuilder2.append("), ");
		stringBuilder2.append(distance.toString());
		stringBuilder2.append(")");
		
		for(sm_segments segment : segments){
			stringBuilder2.append(" AND ST_Disjoint(nodes.geom, '");		
			stringBuilder2.append(segment.getGeom().toString());
			stringBuilder2.append("')");
		}
		
		for(sm_links link : links){
			stringBuilder2.append(" AND ST_Disjoint(nodes.geom, '");		
			stringBuilder2.append(link.getGeom().toString());
			stringBuilder2.append("')");
		}
		
		return getCurrentSession().createSQLQuery(stringBuilder2.toString()).addEntity(sm_nodes.class).list();
	}
}

