package com.enterprise.links.beans;

public class ChildPortalBean {
	
	private String Id;
	private String ParentId;
	private String Portal;
	private String Link;
	private String Image;
	private String Comments;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getParentId() {
		return ParentId;
	}
	public void setParentId(String parentId) {
		ParentId = parentId;
	}
	public String getPortal() {
		return Portal;
	}
	public void setPortal(String portal) {
		Portal = portal;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	
}
