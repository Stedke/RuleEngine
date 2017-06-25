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
import com.RuleEngine.service.ValidateService;
import com.RuleEngine.wrappers.sm_dictionaryWrapper;
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
    	
    	List<sm_dictionary> temp4 = new ArrayList<sm_dictionary>();
    	temp4.add(dictionary);
    	ruleData ruleData = new ruleData();
    	ruleData.setSm_dictionary(temp4);
    
    	ValidateService.missingDataInserted(ruleData);
    	
    	return "result3";
    }
}
