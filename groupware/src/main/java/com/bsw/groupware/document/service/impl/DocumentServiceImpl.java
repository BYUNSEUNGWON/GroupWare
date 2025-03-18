package com.bsw.groupware.document.service.impl;

import java.util.List;

import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsw.groupware.document.service.DocumentService;
import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.mapper.DocumentMapper;
import com.bsw.groupware.mapper.LoginMapper;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.UserVO;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	DocumentMapper documentMapper;

	@Override
	public List<ApprovalFormVO> getformList() {
		
		return documentMapper.getFormList();
	}

	@Override
	public String getContents(String formId) {
		return documentMapper.getContents(formId);
	}

	@Override
	public List<UserVO> getApprLine(String userId) {
		return documentMapper.getApprLine(userId);
	}
	

}
