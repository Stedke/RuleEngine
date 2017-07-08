package com.RuleEngine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.RuleEngine.model.Tuple;
import com.RuleEngine.model.missingItemsList;
import com.RuleEngine.model.ruleData;
import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_nodeImpact;
import com.RuleEngine.model.sm_nodeImpactArea;
import com.RuleEngine.model.sm_nodeImpactEnum;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
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
	
	private KieContainer kieContainerImpact;
	private KieScanner kieScannerImpact;
	
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
	public void updateAllNodesImpacts(){
		KieServices ks = KieServices.Factory.get();
		kieContainerImpact = ks.newKieContainer(ks.newReleaseId("com.RuleEngine", "ImpactRuleEngine", "1.0.0"));
    	kieScannerImpact = ks.newKieScanner(kieContainerImpact);
    	kieScannerImpact.start(150000);
    	
    	sm_nodeImpactArea impact = new sm_nodeImpactArea();
    	
		KieSession kieSession = kieContainerImpact.newKieSession();
		kieSession.setGlobal("sm_nodeImpactAreaList", impact);
		
		for(sm_dictionary dictionary : ruleData.getSm_dictionary() ){
			kieSession.insert(dictionary);
		}
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		logger.debug("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		
		List<sm_nodes> nodesToRemove = new ArrayList<sm_nodes>();
		List<sm_dictionary> dictionariesToAdd = new ArrayList<sm_dictionary>();
		List<sm_segments> segmentsToAdd = new ArrayList<sm_segments>();
		List<sm_segment_properties> segmentPropertiesToAdd = new ArrayList<sm_segment_properties>();
		
		Long sm_nodesId = getNextSm_nodesId();
		Long sm_segmentsId = getNextSm_segmentsId();
		Long sm_segment_propertiesId = getNextSm_segment_propertiesId();
		Long sm_dictionaryId = getNextSm_dictionaryId();
		
		for(Tuple<String, sm_nodeImpact> elem : impact.getListOfNodes()){
			logger.debug("For Tuple with dictionary: "+elem.x);
			sm_dictionary dictionary = new sm_dictionary();
			List<sm_node_properties> props = new ArrayList<sm_node_properties>();
			
			for(sm_dictionary dic : ruleData.getSm_dictionary()){
				if(dic.getName().equals(elem.x)){
					dictionary = dic;
				}
			}
			logger.debug("Dictionary name: "+dictionary.getName());
			for(sm_node_properties prop : ruleData.getSm_node_properties()){
				if(prop.getDictionary_id().getName().equals(dictionary.getName())){
					props.add(prop);
					logger.debug("props added with: "+prop.getId().toString());
				}
			}			
			for(sm_node_properties prop : props){
				for(sm_nodes node : ruleData.getSm_nodes()){
					if(prop.getNode_id().getId().equals(node.getId())){
						if(elem.y.getImpact().compareTo(sm_nodeImpactEnum.TO) == 0){
							Geometry geom = node.getGeom().buffer(elem.y.getValue().doubleValue());
							logger.debug("geom buffer: "+geom.toString());
							
							if(geom.getBoundary().intersects(ruleData.getSm_link().getGeom())){

								//TO part of the node 		
								DistanceOp distanceOp = new DistanceOp(geom.getBoundary().intersection(ruleData.getSm_link().getGeom()),
																		ruleData.getSm_link().getGeom().getStartPoint());
								Coordinate[] closestPoint = distanceOp.closestPoints();
								
								DistanceOp distanceOp1 = new DistanceOp(node.getGeom(),
																		ruleData.getSm_link().getGeom());
								Coordinate[] closestPoint1 = distanceOp1.closestPoints();
								
								if((geom.getBoundary().intersection(ruleData.getSm_link().getGeom()).getNumPoints() == 1 && 
										(closestPoint[0].distance(closestPoint[1]) > closestPoint1[1].distance(closestPoint[1])))){
									closestPoint[0] = closestPoint1[1];  
								}
								
								logger.debug("closest start point: "+closestPoint[0].toString());
								logger.debug("closest to node: "+closestPoint1[1].toString());
								
								sm_segments sm = new sm_segments();
								sm_segment_properties sm_props = new sm_segment_properties();
								
						        CoordinateSequence cs = new GeometryFactory().getCoordinateSequenceFactory().create(2, 2);
						        cs.setOrdinate(0, 0, closestPoint[0].x);
						        cs.setOrdinate(0, 1, closestPoint[0].y);
						        cs.setOrdinate(1, 0, closestPoint1[1].x);
						        cs.setOrdinate(1, 1, closestPoint1[1].y);						    	 
								LineString line = new GeometryFactory().createLineString(cs);
								
								logger.debug("lineString: "+line.toString());
								
								sm_props.setId(new Long(sm_segment_propertiesId));
								sm_segment_propertiesId++;
								sm_props.setTags(prop.getTags());
								sm_props.setDictionary_id(prop.getDictionary_id());
								sm_props.setDescription(prop.getDescription());
								
								sm.setId(new Long(sm_segmentsId));
								sm_segmentsId++;
								sm.setLink_id(ruleData.getSm_link());
								sm.setGeom(line);
								
								sm_props.setSegment_id(sm);
								
								segmentsToAdd.add(sm);
								segmentPropertiesToAdd.add(sm_props);
								nodesToRemove.add(node);
								
								//Set sm_segment property to TO
								sm_segment_properties sm_props1 = new sm_segment_properties();
								sm_props1.setId(new Long(sm_segment_propertiesId));
								sm_segment_propertiesId++;
								String[] newTags = {"TO"}; 
								sm_props1.setTags(newTags);
								sm_props1.setDescription(new String("Node impact TO"));
								sm_props1.setSegment_id(sm);
								
								//Add new dictionary if needed or set dictionary
								boolean found = false;
								for(sm_dictionary dt : dictionariesToAdd){
									if(dt.getName().equals("TO_FROM_UNTIL")){
										sm_props1.setDictionary_id(dt);
										found = true;
										logger.debug("Dictionary found");
										break;
									}
								}
								if(!found){
									sm_dictionary d = new sm_dictionary();
									d.setDatatypes(new String[]{"String","String","String"});
									d.setDescription("Node impact");
									d.setId(new Long(sm_dictionaryId));
									sm_dictionaryId++;
									d.setKey(new String[]{"TO","FROM","UNTIL"});
									d.setName("TO_FROM_UNTIL");
									d.setRequired(new Boolean[]{false,false,false});
									dictionariesToAdd.add(d);
									
									for(sm_dictionary dt : dictionariesToAdd){
										if(dt.getName().equals("TO_FROM_UNTIL")){
											sm_props1.setDictionary_id(dt);
											logger.debug("Setting dictionary: "+dt.getName());
											break;
										}
									}
								}
								
								//set sm_segment_property affects segment
								for(sm_segments s : ruleData.getSm_segments()){
									if(s.getStart_node().getGeom().equals(node.getGeom()) || s.getStart_node().getGeom().equals(node.getGeom())){
										sm_segment_properties sm_props4 = new sm_segment_properties();
										sm_props4.setId(new Long(sm_segment_propertiesId));
										sm_segment_propertiesId++;
										
										sm_props4.setTags(new String[]{s.getId().toString()});
										sm_props4.setDescription(new String("Impacts segment"));
										sm_props4.setSegment_id(sm);
										//Add new dictionary if needed or set dictionary
										boolean found3 = false;
										for(sm_dictionary dt : dictionariesToAdd){
											if(dt.getName().equals("Impacts segment")){
												sm_props4.setDictionary_id(dt);
												found3 = true;
												logger.debug("Dictionary found");
												break;
											}
										}
										if(!found3){
											sm_dictionary d = new sm_dictionary();
											d.setDatatypes(new String[]{"String"});
											d.setDescription("Impacts segment");
											d.setId(new Long(sm_dictionaryId));
											sm_dictionaryId++;
											d.setKey(new String[]{"ID"});
											d.setName("Impacts segment");
											d.setRequired(new Boolean[]{false});
											dictionariesToAdd.add(d);
											
											for(sm_dictionary dt : dictionariesToAdd){
												if(dt.getName().equals("Impacts segment")){
													sm_props4.setDictionary_id(dt);
													logger.debug("Setting dictionary: "+dt.getName());
													break;
												}
											}
										}
										segmentPropertiesToAdd.add(sm_props4);
									}
								}

								segmentPropertiesToAdd.add(sm_props1);
								
								//FROM part of the node
								
								DistanceOp distanceOp2 = new DistanceOp(geom.getBoundary().intersection(ruleData.getSm_link().getGeom()),
										ruleData.getSm_link().getGeom().getEndPoint());
								Coordinate[] closestPoint2 = distanceOp2.closestPoints();

								DistanceOp distanceOp3 = new DistanceOp(node.getGeom(),
										ruleData.getSm_link().getGeom());
								Coordinate[] closestPoint3 = distanceOp3.closestPoints();
								
								if((geom.getBoundary().intersection(ruleData.getSm_link().getGeom()).getNumPoints() == 1) && 
										(closestPoint2[0].distance(closestPoint2[1]) > closestPoint3[1].distance(closestPoint2[1]))){
									closestPoint2[0] = closestPoint3[1];  
								}

								logger.debug("closest end point: "+closestPoint2[0].toString());
								logger.debug("closest to node: "+closestPoint3[1].toString());

								sm_segments sm1 = new sm_segments();
								sm_segment_properties sm_props2 = new sm_segment_properties();

								CoordinateSequence cs1 = new GeometryFactory().getCoordinateSequenceFactory().create(2, 2);
								cs1.setOrdinate(0, 0, closestPoint2[0].x);
								cs1.setOrdinate(0, 1, closestPoint2[0].y);
								cs1.setOrdinate(1, 0, closestPoint3[1].x);
								cs1.setOrdinate(1, 1, closestPoint3[1].y);						    	 
								LineString line1 = new GeometryFactory().createLineString(cs1);

								logger.debug("lineString: "+line1.toString());

								sm_props2.setId(new Long(sm_segment_propertiesId));
								sm_segment_propertiesId++;
								sm_props2.setTags(prop.getTags());
								sm_props2.setDictionary_id(prop.getDictionary_id());
								sm_props2.setDescription(prop.getDescription());
								

								sm1.setId(new Long(sm_segmentsId));
								sm_segmentsId++;
								sm1.setLink_id(ruleData.getSm_link());
								sm1.setGeom(line1);

								sm_props2.setSegment_id(sm1);

								segmentsToAdd.add(sm1);
								segmentPropertiesToAdd.add(sm_props2);

								//Set sm_segment property to FROM
								sm_segment_properties sm_props3 = new sm_segment_properties();
								sm_props3.setId(new Long(sm_segment_propertiesId));
								sm_segment_propertiesId++;
								String[] newTags1 = {"TO1"}; 
								sm_props3.setTags(newTags1);
								sm_props3.setDescription(new String("Node impact TO1"));
								sm_props3.setSegment_id(sm1);

								//Add new dictionary if needed or set dictionary
								boolean found1 = false;
								for(sm_dictionary dt : dictionariesToAdd){
									if(dt.getName().equals("TO_FROM_UNTIL")){
										sm_props3.setDictionary_id(dt);
										found1 = true;
										logger.debug("Dictionary found");
										break;
									}
								}
								if(!found1){
									sm_dictionary d = new sm_dictionary();
									d.setDatatypes(new String[]{"String","String","String"});
									d.setDescription("Node impact");
									d.setId(getNextSm_dictionaryId());
									d.setKey(new String[]{"TO","FROM","UNTIL"});
									d.setName("TO_FROM_UNTIL");
									d.setRequired(new Boolean[]{false,false,false});
									dictionariesToAdd.add(d);
	
									for(sm_dictionary dt : dictionariesToAdd){
										if(dt.getName().equals("TO_FROM_UNTIL")){
											sm_props3.setDictionary_id(dt);
											logger.debug("Setting dictionary: "+dt.getName());
											break;
										}
									}
								}
								
								//set sm_segment_property affects segment
								for(sm_segments s : ruleData.getSm_segments()){
									if(s.getStart_node().getGeom().equals(node.getGeom()) || s.getStart_node().getGeom().equals(node.getGeom())){
										sm_segment_properties sm_props5 = new sm_segment_properties();
										sm_props5.setId(new Long(sm_segment_propertiesId));
										sm_segment_propertiesId++;
										sm_props5.setTags(new String[]{s.getId().toString()});
										sm_props5.setDescription(new String("Impacts segment"));
										sm_props5.setSegment_id(sm1);
										//Add new dictionary if needed or set dictionary
										boolean found4 = false;
										for(sm_dictionary dt : dictionariesToAdd){
											if(dt.getName().equals("Impacts segment")){
												sm_props5.setDictionary_id(dt);
												found4 = true;
												logger.debug("Dictionary found");
												break;
											}
										}
										if(!found4){
											sm_dictionary d = new sm_dictionary();
											d.setDatatypes(new String[]{"String"});
											d.setDescription("Impacts segment");
											d.setId(new Long(sm_dictionaryId));
											sm_dictionaryId++;
											d.setKey(new String[]{"ID"});
											d.setName("Impacts segment");
											d.setRequired(new Boolean[]{false});
											dictionariesToAdd.add(d);
											
											for(sm_dictionary dt : dictionariesToAdd){
												if(dt.getName().equals("Impacts segment")){
													sm_props5.setDictionary_id(dt);
													logger.debug("Setting dictionary: "+dt.getName());
													break;
												}
											}
										}
										segmentPropertiesToAdd.add(sm_props5);
									}
								}
	
								segmentPropertiesToAdd.add(sm_props3);
								
								//Copy all other nodes properties
								for(sm_node_properties it : ruleData.getSm_node_properties()){							
								if(it.getNode_id().getId().equals(node.getId()) && (!it.getId().equals(prop.getId()))){
										sm_segment_properties gg = new sm_segment_properties();
										gg.setDescription(it.getDescription());
										gg.setDictionary_id(it.getDictionary_id());
										gg.setId(new Long(sm_segment_propertiesId));
										sm_segment_propertiesId++;
										gg.setSegment_id(sm);
										gg.setTags(it.getTags());
										
										sm_segment_properties gg1 = new sm_segment_properties();
										gg1.setDescription(it.getDescription());
										gg1.setDictionary_id(it.getDictionary_id());
										gg1.setId(new Long(sm_segment_propertiesId));
										sm_segment_propertiesId++;
										gg1.setSegment_id(sm1);
										gg1.setTags(it.getTags());

										segmentPropertiesToAdd.add(gg);
										segmentPropertiesToAdd.add(gg1);
									}
								}
							}
							
						} else if(elem.y.getImpact().compareTo(sm_nodeImpactEnum.FROM) == 0){
							
						}else if(elem.y.getImpact().compareTo(sm_nodeImpactEnum.UNTIL) == 0){
							
						}else if(elem.y.getImpact().compareTo(sm_nodeImpactEnum.AREA) == 0){
							
						}
					}
				}
			}
		}
		
		logger.debug("Print all elems:");
		for(sm_segments seg : segmentsToAdd){
			logger.debug("Seg id: "+ seg.getId().toString());
			logger.debug("Seg geom: "+seg.getGeom().toString());
		}
		for(sm_segment_properties seg : segmentPropertiesToAdd){
			logger.debug("SegProp id: "+ seg.getId().toString());
			logger.debug("SegProp dict name: "+seg.getDictionary_id().getName());
			logger.debug("SegProp description: "+seg.getDescription());
			logger.debug("SegProp tags: "+seg.getTags().toString());
			logger.debug("SegProp seg id: "+seg.getSegment_id().getId().toString());
		}
		for(sm_nodes node : nodesToRemove){
			logger.debug("Node id: "+node.getId().toString());
		}
		for(sm_dictionary seg : dictionariesToAdd){
			logger.debug("Dictionary id: "+ seg.getId().toString());
			logger.debug("Dictionary name: "+seg.getName());
		}
		
		List<sm_nodes> n_temp = ruleData.getSm_nodes();
		List<sm_segments> s_temp = ruleData.getSm_segments();
		List<sm_segment_properties> sp_temp = ruleData.getSm_segment_properties();
		List<sm_dictionary> d_temp = ruleData.getSm_dictionary();
	
		n_temp.removeAll(nodesToRemove);
		s_temp.addAll(segmentsToAdd);
		sp_temp.addAll(segmentPropertiesToAdd);
		d_temp.addAll(dictionariesToAdd);
		
		ruleData.setSm_nodes(n_temp);
		ruleData.setSm_segments(s_temp);
		ruleData.setSm_segment_properties(sp_temp);
		ruleData.setSm_dictionary(d_temp);
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
			if(segment.getGeom().intersects(this.ruleData.getSm_link().getGeom())){
				logger.debug("Intersects!");
				Envelope envelope = segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getEnvelopeInternal();
				logger.debug("Envelope: "+ envelope.toString());
				boolean heightValid = false;
				Coordinate maxY = new Coordinate();
				Coordinate minY = new Coordinate();
				Coordinate maxX = new Coordinate();
				Coordinate minX = new Coordinate();
				if(envelope.getHeight() > envelope.getWidth()){
					for(Coordinate coord : segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getCoordinates()){
						if(coord.y == envelope.getMaxY()){
							maxY.setCoordinate(coord);
						}
						if(coord.y == envelope.getMinY()){
							minY.setCoordinate(coord);
						}
					}
					heightValid = true;
					logger.debug("maxY: "+ maxY.toString());
					logger.debug("minY: "+ minY.toString());
				}else{

					for(Coordinate coord : segment.getGeom().intersection(this.ruleData.getSm_link().getGeom()).getCoordinates()){
						if(coord.x == envelope.getMaxX()){
							maxX.setCoordinate(coord);
						}
						if(coord.x == envelope.getMinX()){
							minX.setCoordinate(coord);
						}
					}
					logger.debug("maxX: "+ maxX.toString());
					logger.debug("minX: "+ minX.toString());
				}
				
				logger.debug("Check start and end point");
				DistanceOp distanceOp = new DistanceOp(segment.getGeom().getStartPoint(),this.ruleData.getSm_link().getGeom());
				Coordinate[] distance = distanceOp.closestPoints();			
				logger.debug("first: "+distance[1].toString());
				
				DistanceOp distanceOp1 = new DistanceOp(segment.getGeom().getEndPoint(),this.ruleData.getSm_link().getGeom());
				Coordinate[] distance1 = distanceOp1.closestPoints();			
				logger.debug("second: "+distance1[1].toString());
				
				double dstH = 0;
				double dst = distance[1].distance(distance1[1]);
				logger.debug("dst: "+Double.toString(dst));
				
				if(heightValid){
					dstH = maxY.distance(minY);
					logger.debug("dstH: "+Double.toString(dstH));		
					
					if(dstH < dst){
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance[1],segment.getId()));
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance1[1],segment.getId()));
					}else{
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(maxY),segment.getId()));
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(minY),segment.getId()));
					}
				}else{
					dstH = maxX.distance(minX);
					logger.debug("dstH: "+Double.toString(dstH));
					
					if(dstH < dst){
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance[1],segment.getId()));
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(distance1[1],segment.getId()));
					}else{
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(maxX),segment.getId()));
						sm_segmentsAreas.add(new Tuple<Coordinate,Long>(new Coordinate(minX),segment.getId()));
					}
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
	
	private Long getNextSm_nodesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_nodes node : ruleData.getSm_nodes()){
			temp.add(node.getId());
		}
		return (Collections.max(temp)+1);
	}

	private Long getNextSm_segmentsId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_segments segment : ruleData.getSm_segments()){
			temp.add(segment.getId());
		}
		return (Collections.max(temp)+1);
	}

	private Long getNextSm_node_propertiesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_node_properties prop : ruleData.getSm_node_properties()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}

	private Long getNextSm_segment_propertiesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_segment_properties prop : ruleData.getSm_segment_properties()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}

	private Long getNextSm_link_propertiesId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_link_properties prop : ruleData.getSm_link_properties()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}
	
	private Long getNextSm_dictionaryId() {
		List<Long> temp = new ArrayList<Long>();
		for(sm_dictionary prop : ruleData.getSm_dictionary()){
			temp.add(prop.getId());
		}
		return (Collections.max(temp)+1);
	}
}
