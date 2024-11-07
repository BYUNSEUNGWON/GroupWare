package com.bsw.groupware.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsw.groupware.common.MsgEntity;
import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.model.NaverVO;
import com.bsw.groupware.model.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private NaverService naverService;
	
	@RequestMapping("/login.ex")
	public String login(Model model) {
		String naverUrl = naverService.getNaverLogin();
        model.addAttribute("naverUrl", naverUrl);
		return "/login/login";
	}
	
	@GetMapping("/signup.ex")
    public String showSignupForm(Model model) {
        model.addAttribute("userForm", new UserVO());
        return "/login/signup";
    }

    @PostMapping("/signup.ex")
    public String signup(@ModelAttribute UserVO user, HttpServletRequest request, Model model) {
    	try {
    		user.setUser_ip(getClientIp(request));
			loginService.saveUser(user);
			model.addAttribute("message", "회원가입에 성공하였습니다.");
	        return "/login/login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "회원가입 실패");
	        return "/login/signup";
		}
    }
    
    private String getClientIp(HttpServletRequest request) {
    	
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
	}

	@GetMapping("/checkUserId.ex")
    public ResponseEntity<String> checkUserId(@RequestParam("userId") String userId) throws Exception {
    	
    	int result = loginService.checkUserId(userId);
        
    	if(result == 0) {
			return ResponseEntity.ok().body("success");
    	} else {
			return ResponseEntity.ok().body("duplicate");
    	}
    }

}
