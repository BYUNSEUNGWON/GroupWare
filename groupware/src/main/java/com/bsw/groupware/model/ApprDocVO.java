package com.bsw.groupware.model;

public class ApprDocVO {
	
	private String submitter;
    private String submitterId;
	private String approver;
    private String approverId;
	private String subject;
    private String resultStatus;
    private String documentNo;
    private String fileId;
	private String contents;
	private String comment;
    private String approverComment;
    private String registUserId;
    private String registDt;
	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	public String getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
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
	@Override
	public String toString() {
		return "ApprDocVO [submitter=" + submitter + ", submitterId=" + submitterId + ", approver=" + approver
				+ ", approverId=" + approverId + ", subject=" + subject + ", resultStatus=" + resultStatus
				+ ", documentNo=" + documentNo + ", fileId=" + fileId + ", contents=" + contents + ", comment="
				+ comment + ", approverComment=" + approverComment + ", registUserId=" + registUserId + ", registDt="
				+ registDt + ", getSubmitter()=" + getSubmitter() + ", getSubmitterId()=" + getSubmitterId()
				+ ", getApprover()=" + getApprover() + ", getApproverId()=" + getApproverId() + ", getSubject()="
				+ getSubject() + ", getResultStatus()=" + getResultStatus() + ", getDocumentNo()=" + getDocumentNo()
				+ ", getFileId()=" + getFileId() + ", getContents()=" + getContents() + ", getComment()=" + getComment()
				+ ", getApproverComment()=" + getApproverComment() + ", getRegistUserId()=" + getRegistUserId()
				+ ", getRegistDt()=" + getRegistDt() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
	

}
