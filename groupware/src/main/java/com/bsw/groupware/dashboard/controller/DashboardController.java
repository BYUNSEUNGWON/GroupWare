package com.bsw.groupware.dashboard.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsw.groupware.common.MsgEntity;
import com.bsw.groupware.dashboard.service.DashBoardService;
import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.model.NaverVO;
import com.bsw.groupware.model.UserVO;
import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@Slf4j
public class DashboardController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@RequestMapping("/dashboard.ex")
	public String showDashboard(HttpSession session, Model model) {
		
	    String user = (String) session.getAttribute("user");
	    String[] jobTime = dashBoardService.getSelctJob(user);
	    String startDt = (jobTime.length > 0 ? jobTime[0] : null);
	    String endDt = (jobTime.length > 1 ? jobTime[1] : null);
 
	    model.addAttribute("startDt", startDt);
	    model.addAttribute("endDt", endDt);

	    return "/dashboard/commonDashboard";
	}
	
	@RequestMapping("/startJob.ex")
	public String startJob(HttpSession session) {
		
		String user = (String) session.getAttribute("user");
		dashBoardService.startJob(user);
		
		return "/dashboard/commonDashboard";
	}

}
