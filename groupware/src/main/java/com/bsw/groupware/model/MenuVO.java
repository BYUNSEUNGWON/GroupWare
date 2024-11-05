package com.bsw.groupware.model;

public class MenuVO {
	private String menu_id;
	private String menu_nm;
	private String menu_seq;
	private String menu_url;
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getMenu_seq() {
		return menu_seq;
	}
	public void setMenu_seq(String menu_seq) {
		this.menu_seq = menu_seq;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	@Override
	public String toString() {
		return "MenuVO [menu_id=" + menu_id + ", menu_nm=" + menu_nm + ", menu_seq=" + menu_seq + ", menu_url="
				+ menu_url + ", getMenu_id()=" + getMenu_id() + ", getMenu_nm()=" + getMenu_nm() + ", getMenu_seq()="
				+ getMenu_seq() + ", getMenu_url()=" + getMenu_url() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
