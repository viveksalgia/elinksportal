package com.enterprise.links;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.enterprise.links.beans.ChildPortalBean;
import com.enterprise.links.beans.ConfigBean;
import com.enterprise.links.beans.ParentPortalBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadConfigs {
	
	// Method to read configurations for getting parent and child portals
	public String GetConfigs(){
		
		//Converting the Object to JSONString
		String jsonString = null;
		String configFile = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "elinks" + File.separator + "configs.csv";
		
		try {
			Scanner sc = new Scanner(new File(configFile));
			sc.useDelimiter(","); // Setting comma as delimiter pattern
			
			List<ConfigBean> cblist = new ArrayList<ConfigBean>();
			
			//Assign the configuration values to class object
			while(sc.hasNext()) {
				
				ConfigBean cb = new ConfigBean();
				cb.setName(sc.next().replace("\"", ""));
				cb.setValue(sc.next().replace("\"", ""));
				sc.nextLine();
						
				cblist.add(cb);
				
			}
			sc.close();
			
			// Print the values
			//for (int i = 1; i < cblist.size(); i++)
			//{
			//	System.out.println(cblist.get(i).getName() + "," + cblist.get(i).getValue());
			//}
			
			//Creating the ObjectMapper object
			ObjectMapper mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(cblist);
		} 
		catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return jsonString;
	}
	
	
	//method to get the portal lists
	public String GetPortals(String PortalType, String parentId) {
		
		String PortalJson = null;
		String PortalFile = null;
		
		try {
			ReadConfigs rc = new ReadConfigs();
			List<ConfigBean> cblist = new ArrayList<ConfigBean>();
			List<ParentPortalBean> ppblist = new ArrayList<ParentPortalBean>();
			List<ChildPortalBean> cpblist = new ArrayList<ChildPortalBean>();
			
			ObjectMapper mapper = new ObjectMapper();
			
			String retJsonString = rc.GetConfigs();
			
			cblist = mapper.readValue(retJsonString, new TypeReference<List<ConfigBean>>(){});
			
			for (int i = 1; i < cblist.size(); i++) {
				
				//System.out.println(cblist.get(i).getName());
				
				if (cblist.get(i).getName().equals("DataDirectory")) {
					PortalFile = cblist.get(i).getValue();
				}
				if (cblist.get(i).getName().equals(PortalType)){
						PortalFile = PortalFile + "/" + cblist.get(i).getValue();
				}
			}
			
			//System.out.println(ParentPortalFile);
			
			Scanner sc = new Scanner(new File(PortalFile));
			sc.useDelimiter(",");
			String HeaderLineFlag = "Y";
			
			if (PortalType.equals("ParentPortalFile")) {
				//Assign the configuration values to class object
				while(sc.hasNext()) {
					
					ParentPortalBean ppb = new ParentPortalBean();
					ppb.setId(sc.next().replace("\"", ""));
					ppb.setPortal(sc.next().replace("\"", ""));
					ppb.setLink(sc.next().replace("\"", ""));
					ppb.setImage(sc.next().replace("\"", ""));
					ppb.setComments(sc.next().replace("\"", ""));
					sc.nextLine();
					
					/*
					System.out.println("Id = " + ppb.getId());
					System.out.println("Portal = " + ppb.getPortal());
					System.out.println("Link = " + ppb.getLink());
					System.out.println("Image = " + ppb.getImage());
					System.out.println("Comments = " + ppb.getComments());
					*/
					
					//Below if condition is to ignore the first line read as it is suppose to be header
					if (HeaderLineFlag.equals("Y")){
						HeaderLineFlag = "N";
					}
					else {
						ppblist.add(ppb);
					}
					
				}
			}
			
			if (PortalType.equals("ChildPortalFile")) {
				
				while(sc.hasNext()) {
					
					ChildPortalBean cpb = new ChildPortalBean();
					cpb.setId(sc.next().replace("\"", ""));
					cpb.setParentId(sc.next().replace("\"", ""));
					cpb.setPortal(sc.next().replace("\"", ""));
					cpb.setLink(sc.next().replace("\"", ""));
					cpb.setImage(sc.next().replace("\"", ""));
					cpb.setComments(sc.next().replace("\"", ""));
					sc.nextLine();
					
					/*
					System.out.println("Id = " + ppb.getId());
					System.out.println("Portal = " + ppb.getPortal());
					System.out.println("Link = " + ppb.getLink());
					System.out.println("Image = " + ppb.getImage());
					System.out.println("Comments = " + ppb.getComments());
					*/
					
					//Below if condition is to ignore the first line read as it is suppose to be header
					if (HeaderLineFlag.equals("Y")){
						HeaderLineFlag = "N";
					}
					else if (cpb.getParentId().equals(parentId)){
						cpblist.add(cpb);
					}
					else if (parentId == "" || parentId == null){
						cpblist.add(cpb);
					}
					
				}
			}
			
			sc.close();
			
			// Print the values
			//for (int i = 0; i < ppblist.size(); i++)
			//{
			//	System.out.println(ppblist.get(i).getId() + "," + ppblist.get(i).getPortal() + "," + ppblist.get(i).getLink() + "," + ppblist.get(i).getImage() + "," + ppblist.get(i).getComments());
			//}
			
			//Creating the ObjectMapper object
			ObjectMapper finalMapper = new ObjectMapper();
			
			if (PortalType.equals("ParentPortalFile")) {
				PortalJson = finalMapper.writeValueAsString(ppblist);
			}
			
			if (PortalType.equals("ChildPortalFile")) {
				PortalJson = finalMapper.writeValueAsString(cpblist);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return PortalJson;
	}
	

}
