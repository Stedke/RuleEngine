package com.RuleEngine.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.model.sm_node_properties;
import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.model.sm_segments;
import com.RuleEngine.service.ValidateService;
import com.RuleEngine.wrappers.sm_dictionaryWrapper;
import com.RuleEngine.wrappers.sm_link_propertiesWrapper;
import com.RuleEngine.wrappers.sm_node_propertiesWrapper;
import com.RuleEngine.wrappers.sm_nodesWrapper;
import com.RuleEngine.wrappers.sm_segment_propertiesWrapper;
import com.RuleEngine.wrappers.sm_segmentsWrapper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import com.RuleEngine.model.ruleData;

@Controller
public class addElementController {
	
	@Autowired
	ValidateService ValidateService;
	
	@RequestMapping(value="/addElement")
    public ModelAndView addElement() {
    	ModelAndView modelAndView = new ModelAndView("addElement");
    	modelAndView.addObject("missing_dictionary", ValidateService.getMissingRuleData().getSm_dictionary());
        return modelAndView;
    }
    
    @RequestMapping(value="/sm_dictionary", method = RequestMethod.GET)
    public ModelAndView addSm_dictionary() {
    	ModelAndView modelAndView = new ModelAndView("sm_dictionary","command",new sm_dictionaryWrapper());
    	modelAndView.addObject("missing_dictionary", ValidateService.getMissingRuleData().getSm_dictionary());
    	modelAndView.addObject("sm_dictionary_id", ValidateService.getNextSm_dictionaryId());
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "/addSm_dictionary", method = RequestMethod.POST)
    public String addSm_dictionaryForm(@ModelAttribute("sm_dictionaryWrapper")sm_dictionaryWrapper dictionaryWrapper,
        ModelMap model) {
    	
    	model.addAttribute("name", dictionaryWrapper.getName());
    	model.addAttribute("description", dictionaryWrapper.getDescription());
    	model.addAttribute("key", dictionaryWrapper.getKey());
    	model.addAttribute("datatypes", dictionaryWrapper.getDatatypes());
    	model.addAttribute("required", dictionaryWrapper.getRequired());
    	
    	sm_dictionary dictionary = new sm_dictionary();
    	dictionary.setKey(dictionaryWrapper.getKey().split(";"));
    	dictionary.setDatatypes(dictionaryWrapper.getDatatypes().split(";"));
    	
    	ArrayList<String> temp1 = new ArrayList<String>(Arrays.asList(dictionaryWrapper.getRequired().split(";")));
    	ArrayList<Boolean> temp2 = new ArrayList<Boolean>();
    	for(String str : temp1){
    		temp2.add(Boolean.parseBoolean(str));
    	}
    	Boolean[] temp3 = new Boolean[temp2.size()];
    	temp3 = temp2.toArray(temp3);
    	
    	dictionary.setRequired(temp3);
    	dictionary.setName(dictionaryWrapper.getName());
    	dictionary.setDescription(dictionaryWrapper.getDescription());
    	dictionary.setId(ValidateService.getNextSm_dictionaryId());
    		
        List<sm_dictionary> temp5 = new ArrayList<sm_dictionary>();
        temp5.add(dictionary);
        ruleData ruleData = new ruleData();
        ruleData.setSm_dictionary(temp5);
        
        ValidateService.missingDataInserted(ruleData);
    	
    	return "result3";
    }
    
    @RequestMapping(value="/sm_nodes", method = RequestMethod.GET)
    public ModelAndView addSm_nodes() {
    	ModelAndView modelAndView = new ModelAndView("sm_nodes","command",new sm_nodesWrapper());
    	modelAndView.addObject("sm_nodes_id", ValidateService.getNextSm_nodesId());
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "/addSm_nodes", method = RequestMethod.POST)
    public String addSm_nodesForm(@ModelAttribute("sm_nodesWrapper")sm_nodesWrapper nodesWrapper,
        ModelMap model) {
    	
    	model.addAttribute("geom", nodesWrapper.getGeom());
    	model.addAttribute("osm_node", nodesWrapper.getOsm_node());
    	
    	ArrayList<String> geom = new ArrayList<String>(Arrays.asList(nodesWrapper.getGeom().split(";")));
    	ArrayList<Double> geomd = new ArrayList<Double>();
    	for(String str : geom){
    		geomd.add(Double.parseDouble(str));
    	}
    	
    	sm_nodes nodes = new sm_nodes();
    	Point point = new GeometryFactory().createPoint(new Coordinate(geomd.get(0),geomd.get(1)));
    	
    	nodes.setGeom(point);
    	nodes.setId(ValidateService.getNextSm_nodesId());
    	
        List<sm_nodes> temp = new ArrayList<sm_nodes>();
        temp.add(nodes);
        ruleData ruleData = new ruleData();
        ruleData.setSm_nodes(temp);
        
        ValidateService.missingDataInserted(ruleData);
    	
    	return "result3";
    }
    
    @RequestMapping(value="/sm_node_properties", method = RequestMethod.GET)
    public ModelAndView addSm_node_properties() {
    	ModelAndView modelAndView = new ModelAndView("sm_node_properties","command",new sm_node_propertiesWrapper());
    	modelAndView.addObject("sm_node_properties_id", ValidateService.getNextSm_node_propertiesId());
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "/addSm_node_properties", method = RequestMethod.POST)
    public String addSm_node_propertiesForm(@ModelAttribute("sm_node_propertiesWrapper")sm_node_propertiesWrapper propWrapper,
        ModelMap model) {
        
    	model.addAttribute("node_id", propWrapper.getNode_id());
    	model.addAttribute("tags", propWrapper.getTags());
    	model.addAttribute("description", propWrapper.getDescription());
    	model.addAttribute("dictionary_id", propWrapper.getDictionary_id());
    	
    	
    	sm_node_properties props = new sm_node_properties();
    	
    	props.setDescription(propWrapper.getDescription());
    	props.setId(ValidateService.getNextSm_node_propertiesId());
    	props.setTags(propWrapper.getTags().split(";"));
    	
    	sm_nodes temp1 = ValidateService.getSm_nodes(Long.parseLong(propWrapper.getNode_id()));
    	if(temp1 != null){
    		props.setNode_id(temp1);
    		
    		sm_dictionary temp2 = ValidateService.getSm_dictionary(Long.parseLong(propWrapper.getDictionary_id()));
    		if(temp2 != null){
    			props.setDictionary_id(temp2);
    			
    	        List<sm_node_properties> temp3 = new ArrayList<sm_node_properties>();
    	        temp3.add(props);
    	        ruleData ruleData = new ruleData();
    	        ruleData.setSm_node_properties(temp3);
    	        
    	        ValidateService.missingDataInserted(ruleData);
    		}
    	}
    	
    	return "result3";
    }
    
    @RequestMapping(value="/sm_segments", method = RequestMethod.GET)
    public ModelAndView addSm_segments() {
    	ModelAndView modelAndView = new ModelAndView("sm_segments","command",new sm_segmentsWrapper());
    	modelAndView.addObject("sm_segments_id", ValidateService.getNextSm_segmentsId());
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "/addSm_segments", method = RequestMethod.POST)
    public String addSm_segmentsForm(@ModelAttribute("sm_segmentsWrapper")sm_segmentsWrapper segmentsWrapper,
        ModelMap model) {
    	
    	model.addAttribute("start_node", segmentsWrapper.getStart_node());
    	model.addAttribute("end_node", segmentsWrapper.getEnd_node());
    	model.addAttribute("geom", segmentsWrapper.getGeom());
    	
    	ArrayList<String> geom = new ArrayList<String>(Arrays.asList(segmentsWrapper.getGeom().split(";")));
    	ArrayList<Double> geomd = new ArrayList<Double>();
    	for(String str : geom){
    		geomd.add(Double.parseDouble(str));
    	}
    	
        CoordinateSequence cs = new GeometryFactory().getCoordinateSequenceFactory().create(geomd.size()/2, 2);
        int j = 0;
        for (int i = 0; i < geomd.size()/2; ++i) {
            cs.setOrdinate(j, 0, geomd.get(i));
            cs.setOrdinate(j, 1, geomd.get(i+1));
            ++i;
            ++j;
        }
    	LineString lineString = new GeometryFactory().createLineString(cs);
    	
    	sm_segments segments = new sm_segments();
    	
    	segments.setGeom(lineString);
    	segments.setId(ValidateService.getNextSm_segmentsId());
    	segments.setStart_node(ValidateService.getSm_nodes(Long.parseLong(segmentsWrapper.getStart_node())));
    	segments.setEnd_node(ValidateService.getSm_nodes(Long.parseLong(segmentsWrapper.getEnd_node())));
    	segments.setLink_id(ValidateService.getSm_link());
    	
    	
        List<sm_segments> temp = new ArrayList<sm_segments>();
        temp.add(segments);
        ruleData ruleData = new ruleData();
        ruleData.setSm_segments(temp);
        
        ValidateService.missingDataInserted(ruleData);
    	
    	return "result3";
    }
    
    @RequestMapping(value="/sm_segment_properties", method = RequestMethod.GET)
    public ModelAndView addSm_segment_properties() {
    	ModelAndView modelAndView = new ModelAndView("sm_segment_properties","command",new sm_segment_propertiesWrapper());
    	modelAndView.addObject("sm_segment_properties_id", ValidateService.getNextSm_segment_propertiesId());
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "/addSm_segment_properties", method = RequestMethod.POST)
    public String addSm_segment_propertiesForm(@ModelAttribute("sm_segment_propertiesWrapper")sm_segment_propertiesWrapper propWrapper,
        ModelMap model) {
        
    	model.addAttribute("segment_id", propWrapper.getSegment_id());
    	model.addAttribute("tags", propWrapper.getTags());
    	model.addAttribute("description", propWrapper.getDescription());
    	model.addAttribute("dictionary_id", propWrapper.getDictionary_id());
    	
    	sm_segment_properties props = new sm_segment_properties();
    	
    	props.setDescription(propWrapper.getDescription());
    	props.setId(ValidateService.getNextSm_segment_propertiesId());
    	props.setTags(propWrapper.getTags().split(";"));
    	
    	sm_segments temp1 = ValidateService.getSm_segments(Long.parseLong(propWrapper.getSegment_id()));
    	if(temp1 != null){
    		props.setSegment_id(temp1);
    		
    		sm_dictionary temp2 = ValidateService.getSm_dictionary(Long.parseLong(propWrapper.getDictionary_id()));
    		if(temp2 != null){
    			props.setDictionary_id(temp2);
    			
    	        List<sm_segment_properties> temp3 = new ArrayList<sm_segment_properties>();
    	        temp3.add(props);
    	        ruleData ruleData = new ruleData();
    	        ruleData.setSm_segment_properties(temp3);
    	        
    	        ValidateService.missingDataInserted(ruleData);
    		}
    	}
    	
    	return "result3";
    }
    
    @RequestMapping(value="/sm_link_properties", method = RequestMethod.GET)
    public ModelAndView addSm_link_properties() {
    	ModelAndView modelAndView = new ModelAndView("sm_link_properties","command",new sm_link_propertiesWrapper());
    	modelAndView.addObject("sm_link_properties_id", ValidateService.getNextSm_link_propertiesId());
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "/addSm_link_properties", method = RequestMethod.POST)
    public String addSm_link_propertiesForm(@ModelAttribute("sm_link_propertiesWrapper")sm_link_propertiesWrapper propWrapper,
        ModelMap model) {
        
    	model.addAttribute("tags", propWrapper.getTags());
    	model.addAttribute("description", propWrapper.getDescription());
    	model.addAttribute("dictionary_id", propWrapper.getDictionary_id());
    	
    	sm_link_properties props = new sm_link_properties();
    	
    	props.setDescription(propWrapper.getDescription());
    	props.setId(ValidateService.getNextSm_link_propertiesId());
    	props.setTags(propWrapper.getTags().split(";"));
    	
    	sm_links temp1 = ValidateService.getSm_link();
    	if(temp1 != null){
    		props.setLink_id(temp1);
    		
    		sm_dictionary temp2 = ValidateService.getSm_dictionary(Long.parseLong(propWrapper.getDictionary_id()));
    		if(temp2 != null){
    			props.setDictionary_id(temp2);
    			
    	        List<sm_link_properties> temp3 = new ArrayList<sm_link_properties>();
    	        temp3.add(props);
    	        ruleData ruleData = new ruleData();
    	        ruleData.setSm_link_properties(temp3);
    	        
    	        ValidateService.missingDataInserted(ruleData);
    		}
    	}
    	
    	return "result3";
    }
}
