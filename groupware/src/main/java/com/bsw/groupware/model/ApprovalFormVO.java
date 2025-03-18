package com.bsw.groupware.model;

public class ApprovalFormVO {

    private String formId;
    private String formNm;
    private String content;
    
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFormNm() {
		return formNm;
	}
	public void setFormNm(String formNm) {
		this.formNm = formNm;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	@Override
	public String toString() {
		return "ApprovalFormVO [formId=" + formId + ", formNm=" + formNm + ", content=" + content + ", getFormId()="
				+ getFormId() + ", getFormNm()=" + getFormNm() + ", getContent()=" + getContent() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    
    
    
    
    
}
