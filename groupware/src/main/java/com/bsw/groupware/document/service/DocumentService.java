package com.bsw.groupware.document.service;

import java.util.List;
import java.util.Map;

import com.bsw.groupware.model.ApprDocVO;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.FileMetadata;
import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.UserVO;

public interface DocumentService {
	
	List<ApprovalFormVO> getformList();

	String getContents(String formId);

	List<UserVO> getApprLine(String userId);

	void insertFile(FileMetadata fileMetadata);

	void submit(ApprDocVO apprDocVO);

	List<ApprDocVO> selectApprovalList(Map<String, Object> params);
	
}
