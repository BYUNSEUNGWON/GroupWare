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
import com.bsw.groupware.document.service.DocumentService;
import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.model.NaverNewsResponseVO;
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
	
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping("/dashboard.ex")
	public String showDashboard(HttpSession session, Model model) {
		
	    String user = (String) session.getAttribute("user");
	    Map<String, Object> jobTimeMap = dashBoardService.getSelctJob(user);
	    NaverNewsResponseVO  newsData = dashBoardService.getTopHeadlines();

	    String startDt = (jobTimeMap != null && jobTimeMap.get("START_DT") != null) ? jobTimeMap.get("START_DT").toString() : null;
	    String endDt = (jobTimeMap != null && jobTimeMap.get("END_DT") != null) ? jobTimeMap.get("END_DT").toString() : null;

	    model.addAttribute("startDt", startDt);
	    model.addAttribute("endDt", endDt);
        model.addAttribute("articles", newsData.getItems());

        logger.debug("articles value :: {} ", newsData.getItems());
        
        logger.debug("articles valie :: {} ", newsData.getItems().get(0).getTitle());
        logger.debug("articles valie :: {} ", newsData.getItems().get(0).getLink());

	    return "/dashboard/commonDashboard";
	}
	
	@RequestMapping("/startJob.ex")
	public String startJob(HttpSession session) {
		
		String user = (String) session.getAttribute("user");
		dashBoardService.startJob(user);
		
		return "/dashboard/commonDashboard";
	}
	
	@RequestMapping("/endJob.ex")
	public String endJob(HttpSession session) {
		
		String user = (String) session.getAttribute("user");
		dashBoardService.endJob(user);
		
		return "/dashboard/commonDashboard";
	}
	
	@RequestMapping("/dashboard/counts.ex")
    public ResponseEntity<Map<String, Integer>> getDocumentCounts(HttpSession session) {
        String user = (String) session.getAttribute("user");

        Map<String, Object> params = new HashMap<>();
        params.put("user", user);

        int approvalCount = documentService.countByStatus(params, "approval");
        int approvedCount = documentService.countByStatus(params, "approved");
        int proceedCount = documentService.countByStatus(params, "proceed");
        int returnedCount = documentService.countByStatus(params, "returend");
        int cancelCount = documentService.countByStatus(params, "cancel");
        
        logger.debug("approvalCount :: {} ", approvalCount);
        logger.debug("approvedCount :: {} ", approvedCount);
        logger.debug("proceedCount :: {} ", proceedCount);
        logger.debug("returnedCount :: {} ", returnedCount);
        logger.debug("cancelCount :: {} ", cancelCount);

        Map<String, Integer> result = new HashMap<>();
        result.put("approval", approvalCount);
        result.put("approved", approvedCount);
        result.put("proceed", proceedCount);
        result.put("returned", returnedCount);
        result.put("cancel", cancelCount);
        
        return ResponseEntity.ok(result);
    }

}
