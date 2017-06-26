package com.RuleEngine.dao;

import java.util.ArrayList;
import java.util.List;
import com.vividsolutions.jts.geom.Polygon;
import com.RuleEngine.model.sm_links;

public interface sm_linksDAO {
	public List<sm_links> getSm_links(ArrayList<String> linksArea);
	public List<sm_links> getChosenSm_link(Integer linkId);
}
