package com.bsw.groupware.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bsw.groupware.model.TeamsVO;

@Mapper
public interface DashBoardMapper {

	void startJob(String user);

	void selectJob(String user);

	Map getSelctJob(String user);

	void endJob(String user);

	List<TeamsVO> getTeamsTitle(String user);

	List<TeamsVO> getTeamsTitleLimit(String user);
	
	
}
