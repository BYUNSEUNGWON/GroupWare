package com.bsw.groupware.dashboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bsw.groupware.model.NaverNewsResponseVO;
import com.bsw.groupware.model.TeamsVO;

public interface DashBoardService {

	void startJob(String user);

	Map<String, Object> getSelctJob(String user);

	void endJob(String user);

	NaverNewsResponseVO getTopHeadlines();
	
	List<TeamsVO> getTeamsTitle(String user);
	
}
