package com.lib.ext.widget.faceselector;

public class Face {

	private int faceRes = 0;
	private String faceName = "";
	private int position = 0;
	
	public int getFaceRes() {
		return faceRes;
	}
	public void setFaceRes(int faceRes) {
		this.faceRes = faceRes;
	}
	public String getFaceName() {
		return faceName;
	}
	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setPosition(int p){
		this.position = p;
	}
	
}
