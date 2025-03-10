package com.bsw.groupware.dashboard.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsw.groupware.dashboard.service.DashBoardService;
import com.bsw.groupware.mapper.DashBoardMapper;

@Service
public class DashBoardServiceImpl implements DashBoardService{
	
	@Autowired
	DashBoardMapper dashBoardMapper;
	
	@Override
	public void startJob(String user) {
		dashBoardMapper.startJob(user);
	}

	@Override
	public Map getSelctJob(String user) {
		
		return dashBoardMapper.getSelctJob(user);
	}

	@Override
	public void endJob(String user) {
		dashBoardMapper.endJob(user);
	}


}
