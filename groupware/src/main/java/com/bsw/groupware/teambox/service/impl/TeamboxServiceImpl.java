package com.bsw.groupware.teambox.service.impl;

import java.util.List;
import java.util.Map;

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
import com.bsw.groupware.model.ApprDocVO;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.FileMetadata;
import com.bsw.groupware.model.UserVO;
import com.bsw.groupware.teambox.service.TeamboxService;

@Service
public class TeamboxServiceImpl implements TeamboxService{
	
	@Autowired
	DocumentMapper documentMapper;

	@Override
	public List<ApprovalFormVO> getformList() {
		
		return documentMapper.getFormList();
	}


}
