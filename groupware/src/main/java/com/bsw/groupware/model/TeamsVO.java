package com.bsw.groupware.model;

import java.util.List;

public class TeamsVO {
	
	private String title;
    private String contents;
	private int count;
	private int seq;
    private String registUserId;
    private String registDt;
    private String link;
    private String fileId;

    private List<TeamsVO> teams;	
    
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getRegistUserId() {
		return registUserId;
	}
	public void setRegistUserId(String registUserId) {
		this.registUserId = registUserId;
	}
	public String getRegistDt() {
		return registDt;
	}
	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}
    
	public List<TeamsVO> getTeams() {
		return teams;
	}
	
	public void setItems(List<TeamsVO> teams) {
		this.teams = teams;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public void setTeams(List<TeamsVO> teams) {
		this.teams = teams;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	@Override
	public String toString() {
		return "TeamsVO [title=" + title + ", contents=" + contents + ", count=" + count + ", seq=" + seq
				+ ", registUserId=" + registUserId + ", registDt=" + registDt + ", link=" + link + ", fileId=" + fileId
				+ ", teams=" + teams + ", getTitle()=" + getTitle() + ", getContents()=" + getContents()
				+ ", getCount()=" + getCount() + ", getRegistUserId()=" + getRegistUserId() + ", getRegistDt()="
				+ getRegistDt() + ", getTeams()=" + getTeams() + ", getSeq()=" + getSeq() + ", getLink()=" + getLink()
				+ ", getFileId()=" + getFileId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
	

}
