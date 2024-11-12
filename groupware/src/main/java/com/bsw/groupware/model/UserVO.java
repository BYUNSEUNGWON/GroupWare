package com.bsw.groupware.model;

public class UserVO {
	private String user_id;
	private String user_ip;
	private String password;
	private String name;
	private String nickname;
	private String dept;
	private String position;
	private String email;
	private String phone;
	private boolean naverUser;
	private boolean kakaoUser;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean isNaverUser() {
		return naverUser;
	}
	public void setNaverUser(boolean naverUser) {
		this.naverUser = naverUser;
	}
	
	public boolean isKakaoUser() {
		return kakaoUser;
	}
	public void setKakaoUser(boolean kakaoUser) {
		this.kakaoUser = kakaoUser;
	}
	@Override
	public String toString() {
		return "UserVO [user_id=" + user_id + ", user_ip=" + user_ip + ", password=" + password + ", name=" + name
				+ ", nickname=" + nickname + ", dept=" + dept + ", position=" + position + ", email=" + email
				+ ", phone=" + phone + ", naverUser=" + naverUser + ", kakaoUser=" + kakaoUser + ", getUser_id()="
				+ getUser_id() + ", getUser_ip()=" + getUser_ip() + ", getPassword()=" + getPassword() + ", getName()="
				+ getName() + ", getNickname()=" + getNickname() + ", getDept()=" + getDept() + ", getPosition()="
				+ getPosition() + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone() + ", isNaverUser()="
				+ isNaverUser() + ", isKakaoUser()=" + isKakaoUser() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
