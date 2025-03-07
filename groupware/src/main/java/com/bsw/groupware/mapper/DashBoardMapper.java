package com.bsw.groupware.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DashBoardMapper {

	void startJob(String user);

	void selectJob(String user);

	String[] getSelctJob(String user);
	
	
}
