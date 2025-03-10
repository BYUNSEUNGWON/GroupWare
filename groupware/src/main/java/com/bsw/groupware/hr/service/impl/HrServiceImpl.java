package com.bsw.groupware.hr.service.impl;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsw.groupware.hr.service.HrService;
import com.bsw.groupware.mapper.HrMapper;

@Service
public class HrServiceImpl implements HrService{
	
	@Autowired
	HrMapper hrMapper;

	@Override
	public List<Map<String, Object>> getUserWorkTimes(String userId, int year) {
		Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("year", year);

        return hrMapper.getUserWorkTimes(params);
	}

	@Override
	public boolean updateWorkTimes(String startTime, String endTime) {
        return hrMapper.updateWorkTimes(startTime, endTime) > 0;
	}
	

}
