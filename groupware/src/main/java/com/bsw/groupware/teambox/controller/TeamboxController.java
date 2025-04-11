package com.bsw.groupware.teambox.controller;

import java.util.HashMap;
import java.util.List;
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
import com.bsw.groupware.model.TeamsVO;
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
public class TeamboxController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@Autowired
	private DocumentService documentService;
	
    @RequestMapping("/teambox.ex")
    public String showTeamList(HttpSession session, 
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        String user = (String) session.getAttribute("user");
        List<TeamsVO> teams = dashBoardService.getTeamsTitle(user);

        // 한 페이지에 5개의 아이템을 표시
        int pageSize = 10;
        int totalItems = teams.size();  // 전체 아이템 갯수
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);  // 전체 페이지 수

        // 현재 페이지에 해당하는 아이템 리스트 추출
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<TeamsVO> paginatedTeams = teams.subList(fromIndex, toIndex);

        model.addAttribute("teams", paginatedTeams);
        model.addAttribute("currentPage", page);  // 현재 페이지
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수

        return "/teambox/teamboxList";
    }
    
    @RequestMapping("/teambox/write.ex")
    public String writeTeambox(HttpSession session, Model model, @RequestParam(value = "action") String action) {
    	logger.debug("writeTeambox start");
        String user = (String) session.getAttribute("user");
        List<TeamsVO> teams = dashBoardService.getTeamsTitle(user);
        
        model.addAttribute("action", action);

        return "/teambox/write";
    }

}
