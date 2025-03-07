package com.bsw.groupware.dashboard.service;

import org.springframework.stereotype.Service;

public interface DashBoardService {

	void startJob(String user);

	String[] getSelctJob(String user);

	
	
}
