package com.enterprise.links;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElinksController {

	@RequestMapping("/getParentPortals")
	public String getParentPortals() {
		
		ReadConfigs rc = new ReadConfigs();
		String retJsonString = null;
		
		try {
			retJsonString = rc.GetPortals("ParentPortalFile", null);
			
			//System.out.println(retJsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retJsonString;
	}
	
	@RequestMapping("/getChildPortals")
	public String getChildPortals(@RequestParam(value = "parentId", required = false) String parentId) {
		
		ReadConfigs rc = new ReadConfigs();
		String retJsonString = null;
		
		try {
			retJsonString = rc.GetPortals("ChildPortalFile", parentId);
			
			//System.out.println(retJsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retJsonString;
	}
}
