package com.bsw.groupware.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.UserVO;

@Mapper
public interface HrMapper {
	
	
	List<Map<String, Object>> getUserWorkTimes(Map<String, Object> params);

	int updateWorkTimes(String startTime, String endTime);
}
