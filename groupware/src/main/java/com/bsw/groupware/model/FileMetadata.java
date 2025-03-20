package com.bsw.groupware.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

public class FileMetadata {

    private String fileId;
    private String originalFilename;
    private String storedFilename;
    private String filePath;
    private LocalDateTime uploadDate;
    private String submitterId;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getStoredFilename() {
		return storedFilename;
	}
	public void setStoredFilename(String storedFilename) {
		this.storedFilename = storedFilename;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public LocalDateTime getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}
	@Override
	public String toString() {
		return "FileMetadata [fileId=" + fileId + ", originalFilename=" + originalFilename + ", storedFilename="
				+ storedFilename + ", filePath=" + filePath + ", uploadDate=" + uploadDate + ", submitterId="
				+ submitterId + ", getFileId()=" + getFileId() + ", getOriginalFilename()=" + getOriginalFilename()
				+ ", getStoredFilename()=" + getStoredFilename() + ", getFilePath()=" + getFilePath()
				+ ", getUploadDate()=" + getUploadDate() + ", getSubmitterId()=" + getSubmitterId() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    
}