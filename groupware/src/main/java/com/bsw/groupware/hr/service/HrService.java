package com.bsw.groupware.hr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

public interface HrService {

	List<Map<String, Object>> getUserWorkTimes(String user_id, int year);

	boolean updateWorkTimes(String startTime, String endTime);

	
	
}
