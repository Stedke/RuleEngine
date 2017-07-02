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

import org.apache.log4j.Logger;

@Service
public class RuleServiceImpl implements RuleService {

	private static final Logger logger = Logger.getLogger(RuleServiceImpl.class);
	
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
		logger.debug("AAAAAAAAAAAAAAAAAAAAAAAA");
		sm_linkAreas.clear();
		List<Tuple<Coordinate,Long>> sm_nodesAreas = new ArrayList<Tuple<Coordinate,Long>>();
		List<Tuple<Coordinate,Long>> sm_segmentsAreas = new ArrayList<Tuple<Coordinate,Long>>();
	
		for(sm_nodes node : this.ruleData.getSm_nodes()){
			DistanceOp distanceOp = new DistanceOp(node.getGeom(),this.ruleData.getSm_link().getGeom());
			Coordinate[] closestPoints =  distanceOp.closestPoints();
			sm_nodesAreas.add(new Tuple<Coordinate,Long>(closestPoints[1],node.getId()));
			logger.debug("sm_nodesAreas added: "+closestPoints[1].toString());
		}
		
		
		for(sm_segments segment : this.ruleData.getSm_segments()){
			logger.debug("check sm_segment: "+segment.getGeom().toString());
			if(segment.getGeom().intersects(this.ruleData.getSm_link().getGeom())){//not working for intersect
				logger.debug("Intersects!");
				Envelope envelope = segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getEnvelopeInternal();
				logger.debug("Envelope: "+ envelope.toString());
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
					logger.debug("sm_segmentsAreas maxY: "+ maxY.toString());
					logger.debug("sm_segmentsAreas minY: "+ minY.toString());
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
					logger.debug("sm_segmentsAreas maxX: "+ maxX.toString());
					logger.debug("sm_segmentsAreas minX: "+ minX.toString());
				}
			}else{
				logger.debug("Does not Intersect");
				DistanceOp distanceOp = new DistanceOp(segment.getGeom().getStartPoint(),this.ruleData.getSm_link().getGeom());
				Coordinate[] distance = distanceOp.closestPoints();
				sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance[1],segment.getId()));
				logger.debug("sm_segmentsAreas first: "+distance[1].toString());
				
				DistanceOp distanceOp1 = new DistanceOp(segment.getGeom().getEndPoint(),this.ruleData.getSm_link().getGeom());
				Coordinate[] distance1 = distanceOp1.closestPoints();
				sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance1[1],segment.getId()));
				logger.debug("sm_segmentsAreas second: "+distance1[1].toString());
			}
		}		
		
		logger.debug("all divide points added");
		
		Point sPoint = ruleData.getSm_link().getGeom().getStartPoint();
		logger.debug("initial sPoint: "+sPoint.toString());
		
		while(!sm_nodesAreas.isEmpty() || !sm_segmentsAreas.isEmpty()){
			
			double minDistance_nodes = Double.MAX_VALUE;
			double minDistance_segments = Double.MAX_VALUE;
			Tuple<Coordinate,Long> min_nodes = new Tuple<Coordinate,Long>(new Coordinate(0,0),new Long(0));
			Tuple<Coordinate,Long> min_segments = new Tuple<Coordinate,Long>(new Coordinate(0,0),new Long(0));
		
			
			
			for(Tuple<Coordinate,Long> elem : sm_nodesAreas){
				logger.debug("For nodes: "+elem.x.toString());
				Point point = new GeometryFactory().createPoint(elem.x);
				
				logger.debug("Distance between:");
				logger.debug(point.toString());
				logger.debug(sPoint.toString());
				
				double dist = sPoint.distance(point);
				
				logger.debug("distance: "+Double.toString(dist));
				logger.debug("minDistance_nodes: "+Double.toString(minDistance_nodes));
				if(dist < minDistance_nodes){
					minDistance_nodes = dist;
					min_nodes = elem;
				}
			}
			for(Tuple<Coordinate,Long> elem : sm_segmentsAreas){
				logger.debug("For segments: "+elem.x.toString());
				Point point = new GeometryFactory().createPoint(elem.x);
				
				logger.debug("Distance between:");
				logger.debug(point.toString());
				logger.debug(sPoint.toString());
				
				double dist = sPoint.distance(point);
				
				logger.debug("distance: "+Double.toString(dist));
				logger.debug("minDistance_segments: "+Double.toString(minDistance_segments));
				
				if(dist < minDistance_segments){
					minDistance_segments = dist;
					min_segments = elem;
				}
			}
			
			logger.debug("final minDistance_nodes: "+Double.toString(minDistance_nodes));
			logger.debug("final minDistance_segments: "+Double.toString(minDistance_segments));
			
			if(minDistance_nodes < minDistance_segments){
				sm_linkAreasData data = new sm_linkAreasData();
				Point point = new GeometryFactory().createPoint(min_nodes.x);
				
				logger.debug("set StartPoint to: "+ sPoint.toString());
				logger.debug("set EndPoint to: "+point.toString());
				
				data.setStart_point(sPoint);
				data.setEnd_point(point);
				sm_nodes node = new sm_nodes();
				for(sm_nodes nodes : ruleData.getSm_nodes()){
					if(nodes.getId() == min_nodes.y){
						node = nodes;
					}
				}
				
				logger.debug("Node with shortest distance: "+node.getGeom().toString());
				
				List<sm_nodes> temp = new ArrayList<sm_nodes>();
				temp.add(node);
				data.setSm_nodes(temp);
			
				List<Tuple<Long,Long>> idCount = new ArrayList<Tuple<Long,Long>>();
			
				for(Tuple<Coordinate,Long> seg : sm_segmentsAreas){
					logger.debug("For: "+seg.x.toString());
					boolean found = false;
					for(Tuple<Long,Long> elem : idCount){
						logger.debug("For id: "+elem.x.toString());
						logger.debug("For Count: "+elem.y.toString());
						if(seg.y == elem.x){
							elem.y = new Long(2);
							found = true;
							logger.debug("Set Count=2, found=true");
						}
					}
					if(!found){
						logger.debug("Not found");
						logger.debug("idCount add id: "+seg.y.toString());
						idCount.add(new Tuple<Long,Long>(seg.y,new Long(1)));
					}
				}
				for(Tuple<Long,Long> elem : idCount){
					logger.debug("For id: "+elem.x.toString());
					logger.debug("For Count: "+elem.y.toString());
					if(elem.y.intValue() == 1){
						sm_segments segment = new sm_segments();
						for(sm_segments segments : ruleData.getSm_segments()){
							logger.debug("For: "+segments.getGeom().toString());
							if(segments.getId() == elem.x){
								segment = segments;
								logger.debug("Setting segment to:"+segment.getGeom().toString());
							}
						}
						
						List<sm_segments> s_temp = new ArrayList<sm_segments>();
						if(!data.getSm_segments().isEmpty()){
							s_temp = data.getSm_segments();
						}

						s_temp.add(segment);
						data.setSm_segments(s_temp);
						logger.debug("Added to data: "+segment.getGeom().toString() );
					}
				}
				
				if(sm_nodesAreas.size() == 1 && sm_segmentsAreas.isEmpty()){
					data.setEnd_point(ruleData.getSm_link().getGeom().getEndPoint());
					logger.debug("Set EndPoint: "+ruleData.getSm_link().getGeom().getEndPoint().toString());
				}
			
				sPoint = point;
				logger.debug("New sPoint: "+point.toString());
				sm_nodesAreas.remove(min_nodes);
				logger.debug("Remove: "+min_nodes.x.toString() );
				
				boolean elemFound = false;
				for(sm_linkAreasData elem : sm_linkAreas){
					logger.debug("For StartPoint: "+elem.getStart_point());
					logger.debug("For EndPoint: "+elem.getEnd_point());
					if(elem.getStart_point().equals(data.getStart_point()) && elem.getEnd_point().equals(data.getEnd_point())){
						elemFound = true;
						elem.addData(data);
						logger.debug("elemFound");
						break;
					}
				}
				if(!elemFound){
					sm_linkAreas.add(data);
					logger.debug("Added StartPoint: "+data.getStart_point());
					logger.debug("Added EndPoint: "+data.getEnd_point());
				}
				
			}else{
			
				sm_linkAreasData data = new sm_linkAreasData();
				Point point = new GeometryFactory().createPoint(min_segments.x);
				
				logger.debug("point: "+point.toString());
				logger.debug("sPoint: "+sPoint.toString());
				
				data.setStart_point(sPoint);
				data.setEnd_point(point);
			
				List<Tuple<Long,Long>> idCount = new ArrayList<Tuple<Long,Long>>();
			
				for(Tuple<Coordinate,Long> seg : sm_segmentsAreas){
					logger.debug("For: "+seg.x.toString());
					boolean found = false;
					for(Tuple<Long,Long> elem : idCount){
						logger.debug("For id: "+elem.x.toString());
						logger.debug("For Count: "+elem.y.toString());
						if(seg.y == elem.x){
							elem.y = new Long(2);
							found = true;
							logger.debug("Set Count=2, found=true");
						}
					}
					if(!found){
						logger.debug("Not found");
						logger.debug("idCount add id: "+seg.y.toString());
						idCount.add(new Tuple<Long,Long>(seg.y,new Long(1)));
					}
				}
				for(Tuple<Long,Long> elem : idCount){
					logger.debug("For id: "+elem.x.toString());
					logger.debug("For Count: "+elem.y.toString());
					if((elem.y.intValue() == 1)
							|| (elem.y.intValue() == 2 && elem.x.intValue() == min_segments.y.intValue())){
						sm_segments segment = new sm_segments();
						for(sm_segments segments : ruleData.getSm_segments()){
							logger.debug("For: "+segments.getGeom().toString());
							if(segments.getId() == elem.x){
								segment = segments;
								logger.debug("Setting segment to:"+segment.getGeom().toString());
							}
						}

						List<sm_segments> s_temp = new ArrayList<sm_segments>();
						if(!data.getSm_segments().isEmpty()){
							s_temp = data.getSm_segments();
						}
						s_temp.add(segment);
						data.setSm_segments(s_temp);
						logger.debug("Added to data: "+segment.getGeom().toString() );
					}
				}
				
				if(sm_nodesAreas.isEmpty() && sm_segmentsAreas.size() == 1){
					data.setEnd_point(ruleData.getSm_link().getGeom().getEndPoint());
					logger.debug("Set EndPoint: "+ruleData.getSm_link().getGeom().getEndPoint().toString());
				}
			
				sPoint = point;
				logger.debug("New sPoint: "+point.toString());
				sm_segmentsAreas.remove(min_segments);
				logger.debug("Remove: "+min_segments.x.toString() );
				
				boolean elemFound = false;
				for(sm_linkAreasData elem : sm_linkAreas){
					logger.debug("For StartPoint: "+elem.getStart_point());
					logger.debug("For EndPoint: "+elem.getEnd_point());
					if(elem.getStart_point().equals(data.getStart_point()) && elem.getEnd_point().equals(data.getEnd_point())){
						elemFound = true;
						elem.addData(data);
						logger.debug("elemFound");
						break;
					}
				}
				if(!elemFound){
					sm_linkAreas.add(data);
					logger.debug("Added StartPoint: "+data.getStart_point());
					logger.debug("Added EndPoint: "+data.getEnd_point());
				}
			}	
		}
		
		logger.debug("properties addition:");
		
		for(sm_linkAreasData data : sm_linkAreas){
			logger.debug("For StartPoint: "+data.getStart_point().toString());
			logger.debug("For EndPoint: "+data.getEnd_point().toString());
			for(sm_node_properties prop : ruleData.getSm_node_properties()){
				logger.debug("For: "+prop.getId().toString());
				for(sm_nodes node : data.getSm_nodes()){
					logger.debug("For: "+node.getGeom().toString());
					if(prop.getNode_id().getId().intValue() == node.getId().intValue()){
						List<sm_node_properties> np_temp = new ArrayList<sm_node_properties>();
						if(!data.getSm_node_properties().isEmpty()){
							np_temp = data.getSm_node_properties();
						}
						np_temp.add(prop);
						data.setSm_node_properties(np_temp);
						logger.debug("Added: "+prop.getId().toString());
					}
				}
			}
			for(sm_segment_properties prop : ruleData.getSm_segment_properties()){
				logger.debug("For: "+prop.getId().toString());
				for(sm_segments segment : data.getSm_segments()){
					logger.debug("For: "+segment.getGeom().toString());
					if(prop.getSegment_id().getId().intValue() == segment.getId().intValue()){
						List<sm_segment_properties> sp_temp = new ArrayList<sm_segment_properties>();
						if(!data.getSm_segment_properties().isEmpty()){
							sp_temp = data.getSm_segment_properties();
						}
						sp_temp.add(prop);
						data.setSm_segment_properties(sp_temp);
						logger.debug("Added: "+prop.getId().toString());
					}
				}
			}
			data.setSm_link_properties(ruleData.getSm_link_properties());	
		}
		
		logger.debug("print all data:");
		for(sm_linkAreasData d : sm_linkAreas){
			logger.debug("StartPoint: "+d.getStart_point());
			logger.debug("EndPoint: "+d.getEnd_point());
			logger.debug("sm_nodes");
			for(sm_nodes n : d.getSm_nodes()){
				logger.debug(n.getGeom().toString());
			}
			logger.debug("sm_node_properties");
			for(sm_node_properties np : d.getSm_node_properties()){
				logger.debug(np.getId().toString());
			}
			logger.debug("sm_segments");
			for(sm_segments s : d.getSm_segments()){
				logger.debug(s.getGeom().toString());
			}
			logger.debug("sm_segment_properties");
			for(sm_segment_properties sp : d.getSm_segment_properties()){
				logger.debug(sp.getId().toString());
			}
			logger.debug("sm_link_properties");
			for(sm_link_properties lp : d.getSm_link_properties()){
				logger.debug(lp.getId().toString());
			}
		}
		
	}
}
