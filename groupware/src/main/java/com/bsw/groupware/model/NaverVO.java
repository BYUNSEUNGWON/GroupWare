package com.bsw.groupware.model;

public class NaverVO {

    private String id;
    private String email;
    private String name;
    private String nickname;
    private String mobile;
    private String birthday;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "NaverVO [id=" + id + ", email=" + email + ", name=" + name + ", nickname=" + nickname + ", mobile="
				+ mobile + ", birthday=" + birthday + ", getId()=" + getId() + ", getEmail()=" + getEmail()
				+ ", getName()=" + getName() + ", getNickname()=" + getNickname() + ", getMobile()=" + getMobile()
				+ ", getBirthday()=" + getBirthday() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
    
    
}
