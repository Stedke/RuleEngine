package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.RuleEngine.model.Tuple;
import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.RuleEngine.model.sm_linkAreasData;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RuleServiceImpl implements RuleService {

	public ruleData ruleData = new ruleData();
	private List<sm_linkAreasData> sm_linkAreas= new ArrayList<sm_linkAreasData>();
	
	@Autowired
	private KieContainer kieContainer;
	
	public void fireRules(){
        KieSession kieSession = kieContainer.newKieSession();//"ksession-rules");
        //kieSession.insert(sm_nodes.get(0));
        //kieSession.fireAllRules();
        kieSession.dispose();
	}

	@Override
	public void transferData(ruleData ruleData) {
		List<sm_dictionary> d_temp = this.ruleData.getSm_dictionary();
		List<sm_nodes> n_temp = this.ruleData.getSm_nodes();
		List<sm_node_properties> np_temp = this.ruleData.getSm_node_properties();
		List<sm_segments> s_temp = this.ruleData.getSm_segments();
		List<sm_segment_properties> sp_temp = this.ruleData.getSm_segment_properties();
		List<sm_link_properties> lp_temp = this.ruleData.getSm_link_properties();
		
		d_temp.addAll(ruleData.getSm_dictionary());
		n_temp.addAll(ruleData.getSm_nodes());
		np_temp.addAll(ruleData.getSm_node_properties());
		s_temp.addAll(ruleData.getSm_segments());
		sp_temp.addAll(ruleData.getSm_segment_properties());
		lp_temp.addAll(ruleData.getSm_link_properties());
		
		this.ruleData.setSm_link(new sm_links(ruleData.getSm_link()));
		this.ruleData.setSm_dictionary(d_temp);
		this.ruleData.setSm_nodes(n_temp);
		this.ruleData.setSm_node_properties(np_temp);
		this.ruleData.setSm_segments(s_temp);
		this.ruleData.setSm_segment_properties(sp_temp);
		this.ruleData.setSm_link_properties(lp_temp);
	}
	
	@Override
	public void divideSm_link(){
		sm_linkAreas.clear();
		List<Tuple<Coordinate,Long>> sm_nodesAreas = new ArrayList<Tuple<Coordinate,Long>>();
		List<Tuple<Coordinate,Long>> sm_segmentsAreas = new ArrayList<Tuple<Coordinate,Long>>();
	
		for(sm_nodes node : this.ruleData.getSm_nodes()){
			DistanceOp distanceOp = new DistanceOp(node.getGeom(),this.ruleData.getSm_link().getGeom());
			Coordinate[] closestPoints =  distanceOp.closestPoints();
			sm_nodesAreas.add(new Tuple<Coordinate,Long>(closestPoints[1],node.getId()));
		}
		
		
		for(sm_segments segment : this.ruleData.getSm_segments()){
			if(segment.getGeom().intersects(this.ruleData.getSm_link().getGeom())){//not working for intersect
				Envelope envelope = segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getEnvelopeInternal();
				if(envelope.getHeight() > envelope.getWidth()){
					Coordinate maxY = new Coordinate();
					Coordinate minY = new Coordinate();
					for(Coordinate coord : segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getCoordinates()){
						if(coord.y == envelope.getMaxY()){
							maxY.setCoordinate(coord);
						}
						if(coord.y == envelope.getMinY()){
							minY.setCoordinate(coord);
						}
					}
					sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(maxY),segment.getId()));
					sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(minY),segment.getId()));
				}else{
					Coordinate maxX = new Coordinate();
					Coordinate minX = new Coordinate();
					for(Coordinate coord : segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getCoordinates()){
						if(coord.x == envelope.getMaxX()){
							maxX.setCoordinate(coord);
						}
						if(coord.x == envelope.getMinX()){
							minX.setCoordinate(coord);
						}
					}
					sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(maxX),segment.getId()));
					sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(minX),segment.getId()));
				}
			}else{

				DistanceOp distanceOp = new DistanceOp(segment.getGeom().getStartPoint(),this.ruleData.getSm_link().getGeom());
				Coordinate[] distance = distanceOp.closestPoints();
				sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance[1],segment.getId()));

				DistanceOp distanceOp1 = new DistanceOp(segment.getGeom().getEndPoint(),this.ruleData.getSm_link().getGeom());
				Coordinate[] distance1 = distanceOp1.closestPoints();
				sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance1[1],segment.getId()));
			}
		}		
		
		Point sPoint = ruleData.getSm_link().getGeom().getStartPoint();
		
		while(!sm_nodesAreas.isEmpty() && !sm_segmentsAreas.isEmpty()){
			
			double minDistance_nodes = 0.0;
			double minDistance_segments = 0.0;
			Tuple<Coordinate,Long> min_nodes = new Tuple<Coordinate,Long>(new Coordinate(0,0),new Long(0));
			Tuple<Coordinate,Long> min_segments = new Tuple<Coordinate,Long>(new Coordinate(0,0),new Long(0));
		
			for(Tuple<Coordinate,Long> elem : sm_nodesAreas){
				Point point = new GeometryFactory().createPoint(elem.x);
				double dist = sPoint.distance(point);
				if(dist < minDistance_nodes){
					minDistance_nodes = dist;
					min_nodes = elem;
				}
			}
			for(Tuple<Coordinate,Long> elem : sm_segmentsAreas){
				Point point = new GeometryFactory().createPoint(elem.x);
				double dist = sPoint.distance(point);
				if(dist < minDistance_segments){
					minDistance_segments = dist;
					min_segments = elem;
				}
			}
			if(minDistance_nodes < minDistance_segments){
				sm_linkAreasData data = new sm_linkAreasData();
				Point point = new GeometryFactory().createPoint(min_nodes.x);
				data.setStart_point(sPoint);
				data.setEnd_point(point);
				sm_nodes node = new sm_nodes();
				for(sm_nodes nodes : ruleData.getSm_nodes()){
					if(nodes.getId() == min_nodes.y){
						node = nodes;
					}
				}
				List<sm_nodes> temp = new ArrayList<sm_nodes>();
				temp.add(node);
				data.setSm_nodes(temp);
			
				List<Tuple<Long,Long>> idCount = new ArrayList<Tuple<Long,Long>>();
			
				for(Tuple<Coordinate,Long> seg : sm_segmentsAreas){
					boolean found = false;
					for(Tuple<Long,Long> elem : idCount){
						if(seg.y == elem.x){
							elem.y = new Long(2);
							found = true;
						}
					}
					if(!found){
						idCount.add(new Tuple<Long,Long>(seg.y,new Long(1)));
					}
				}
				for(Tuple<Long,Long> elem : idCount){
					if(elem.y.intValue() == 1){
						sm_segments segment = new sm_segments();
						for(sm_segments segments : ruleData.getSm_segments()){
							if(segments.getId() == elem.x){
								segment = segments;
							}
						}

						List<sm_segments> s_temp = data.getSm_segments();
						s_temp.add(segment);
						data.setSm_segments(s_temp);
					}
				}
				
				if(sm_nodesAreas.size() == 1 && sm_segmentsAreas.isEmpty()){
					data.setEnd_point(ruleData.getSm_link().getGeom().getEndPoint());
				}
			
				sPoint = point;
				sm_nodesAreas.remove(min_nodes);
				
				sm_linkAreas.add(data);

			}else{
			
				sm_linkAreasData data = new sm_linkAreasData();
				Point point = new GeometryFactory().createPoint(min_segments.x);
				data.setStart_point(sPoint);
				data.setEnd_point(point);
			
				List<Tuple<Long,Long>> idCount = new ArrayList<Tuple<Long,Long>>();
			
				for(Tuple<Coordinate,Long> seg : sm_segmentsAreas){
					boolean found = false;
					for(Tuple<Long,Long> elem : idCount){
						if(seg.y == elem.x){
							elem.y = new Long(2);
							found = true;
						}
					}
					if(!found){
						idCount.add(new Tuple<Long,Long>(seg.y,new Long(1)));
					}
				}
				for(Tuple<Long,Long> elem : idCount){
					if((elem.y.intValue() == 1 && elem.x.intValue() != min_segments.y.intValue())
							|| (elem.y.intValue() == 2 && elem.x.intValue() == min_segments.y.intValue())){
						sm_segments segment = new sm_segments();
						for(sm_segments segments : ruleData.getSm_segments()){
							if(segments.getId() == elem.x){
								segment = segments;
							}
						}

						List<sm_segments> s_temp = data.getSm_segments();
						s_temp.add(segment);
						data.setSm_segments(s_temp);
					}
				}
				
				if(sm_nodesAreas.isEmpty() && sm_segmentsAreas.size() == 1){
					data.setEnd_point(ruleData.getSm_link().getGeom().getEndPoint());
				}
			
				sPoint = point;
				sm_segmentsAreas.remove(min_segments);
				
				sm_linkAreas.add(data);
			}		
		}
		
		for(sm_linkAreasData data : sm_linkAreas){
			for(sm_node_properties prop : ruleData.getSm_node_properties()){
				for(sm_nodes node : data.getSm_nodes()){
					if(prop.getNode_id().getId().intValue() == node.getId().intValue()){
						List<sm_node_properties> np_temp = data.getSm_node_properties();
						np_temp.add(prop);
						data.setSm_node_properties(np_temp);
					}
				}
			}
			for(sm_segment_properties prop : ruleData.getSm_segment_properties()){
				for(sm_segments segment : data.getSm_segments()){
					if(prop.getSegment_id().getId().intValue() == segment.getId().intValue()){
						List<sm_segment_properties> sp_temp = data.getSm_segment_properties();
						sp_temp.add(prop);
						data.setSm_segment_properties(sp_temp);
					}
				}
			}
			data.setSm_link_properties(ruleData.getSm_link_properties());	
		}
	}
}
