package com.bsw.groupware.document.service.impl;

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

	@Override
	public void insertFile(FileMetadata fileMetadata) {
		documentMapper.insertFile(fileMetadata);
	}

	@Override
	public void submit(ApprDocVO apprDocVO) {
		documentMapper.submit(apprDocVO);
	}

	@Override
	public List<ApprDocVO> selectApprovalList(Map<String, Object> params) {
	    return documentMapper.selectApprovalList(params);
	}

	@Override
	public ApprDocVO getDetailContents(String documentNo) {
		return documentMapper.getDetailContents(documentNo);
	}

	@Override
	public List<FileMetadata> getFileData(String fileId) {
		return documentMapper.getFileData(fileId);
	}

	@Override
	public FileMetadata getFileDataById(String fileId, String fileNm) {
		return documentMapper.getFileDataById(fileId, fileNm);
	}

	@Override
	public void updtDocument(String documentNo, String comment, String resultStatus) {
		documentMapper.updtDocument(documentNo, comment, resultStatus);
	}

	@Override
	public int countApprovalList(Map<String, Object> params) {
		return documentMapper.countApprovalList(params);
	}

	@Override
	public int countByStatus(Map<String, Object> params, String status) {
		params.put("action", status);
	    return documentMapper.countByStatus(params);
	}


}
