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
public interface TeamMapper {

	void saveTeambox(TeamsVO teamVO);

	TeamsVO getDetail(String title, String seq);
	

	
	
}
