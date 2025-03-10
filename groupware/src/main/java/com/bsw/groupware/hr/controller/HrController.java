package com.bsw.groupware.hr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsw.groupware.hr.service.HrService;
import com.bsw.groupware.model.HolidayUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
public class HrController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HrService hrService;
	
	@RequestMapping("/hrList.ex")
	public String HrList(HttpSession session, Model model) {
		logger.debug("HrList start");
	    return "/hr/hrRecord";
	}
	
    @GetMapping("/api/holidays")
    @ResponseBody
    public List<Map<String, Object>> getHolidays(HttpSession session, @RequestParam("year") int year) {
    	logger.debug("getHolidays Start");

        try {
            List<Map<String, Object>> holidays = HolidayUtil.getHolidays(year);
            String userId = (String) session.getAttribute("user");
            List<Map<String, Object>> workTimes = hrService.getUserWorkTimes(userId, year);

            for (Map<String, Object> workTime : workTimes) {
                String startDt = (String) workTime.get("startDt");
                String endDt = (String) workTime.get("endDt");

                logger.debug("Start Date: " + startDt);
                logger.debug("End Date: " + endDt);

                Map<String, Object> event = new HashMap<>();
                event.put("title", "Work Time");
                event.put("start", startDt);
                if (endDt != null) {
                    event.put("end", endDt);
                }
                event.put("allDay", false);
                event.put("description", "Working time details");

                holidays.add(event);
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writeValueAsString(holidays);
            logger.debug("Final Calendar Data: " + jsonResult);

            return holidays;
        } catch (IOException e) {
            logger.error("Failed to fetch holidays", e);
            return new ArrayList<>();
        }
    }
    
    @PostMapping("/api/updateWorkTimes.ex")
    public Map<String, Object> updateWorkTimes(@RequestBody Map<String, String> data) {
        String startTime = data.get("startTime");
        String endTime = data.get("endTime");

        // DB 업데이트 로직
        boolean updateSuccess = hrService.updateWorkTimes(startTime, endTime);

        Map<String, Object> response = new HashMap<>();
        response.put("success", updateSuccess);
        return response;
    }
	
}
