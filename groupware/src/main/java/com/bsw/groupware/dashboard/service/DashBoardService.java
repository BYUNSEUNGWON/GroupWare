package com.bsw.groupware.dashboard.service;

import java.util.Map;

import org.springframework.stereotype.Service;

public interface DashBoardService {

	void startJob(String user);

	Map<String, Object> getSelctJob(String user);

	void endJob(String user);

	
	
}
