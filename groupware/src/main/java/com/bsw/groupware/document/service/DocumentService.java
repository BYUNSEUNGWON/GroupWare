package com.bsw.groupware.document.service;

import java.util.List;

import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.UserVO;

public interface DocumentService {
	
	List<ApprovalFormVO> getformList();

	String getContents(String formId);

	List<UserVO> getApprLine(String userId);
	
}
