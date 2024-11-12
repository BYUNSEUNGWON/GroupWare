package com.bsw.groupware.model;

public class KakaoVO {

    private String id;
    private String nickname;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "KakaoVO [id=" + id + ", nickname=" + nickname + ", getId()=" + getId() + ", getNickname()="
				+ getNickname() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
    
    
}
