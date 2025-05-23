package com.bsw.groupware.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bsw.groupware.model.ApprDocVO;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.FileMetadata;
import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.TeamsVO;
import com.bsw.groupware.model.UserVO;

@Mapper
public interface DocumentMapper {
	
	List getFormList();

	String getContents(String formId);

	List<UserVO> getApprLine(String userId);

	void insertFile(FileMetadata fileMetadata);

	void submit(ApprDocVO apprDocVO);

	List<ApprDocVO> selectApprovalList(Map<String, Object> params);

	ApprDocVO getDetailContents(String documentNo);

	List<FileMetadata> getFileData(String fileId);

	FileMetadata getFileDataById(String fileId, String fileNm);

	void updtDocument(String documentNo, String comment, String resultStatus);

	int countApprovalList(Map<String, Object> params);

	int countByStatus(Map<String, Object> params);

	
	
}
