package com.bsw.groupware.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DashBoardMapper {

	void startJob(String user);

	void selectJob(String user);

	Map getSelctJob(String user);

	void endJob(String user);
	
	
}
