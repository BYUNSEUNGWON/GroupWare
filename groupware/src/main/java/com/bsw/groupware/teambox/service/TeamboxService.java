package com.bsw.groupware.teambox.service;

import java.util.List;
import java.util.Map;

import com.bsw.groupware.model.ApprDocVO;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.FileMetadata;
import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.UserVO;

public interface TeamboxService {
	
	List<ApprovalFormVO> getformList();

}
