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
import com.bsw.groupware.login.service.KakaoService;
import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.model.NaverVO;
import com.bsw.groupware.model.UserVO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private KakaoService kakaoService;
	
	@RequestMapping("/login.ex")
	public String login(Model model) {
		String naverUrl = naverService.getNaverLogin();
		String kakaoUrl = kakaoService.getKakaoLogin();
        model.addAttribute("naverUrl", naverUrl);
        model.addAttribute("kakaoUrl", kakaoUrl);
		return "login/login";
	}
	
	@RequestMapping("/doLogin.ex")
    public String doLogin(@RequestParam String username,@RequestParam(required = false) String password, HttpSession session, Model model) {
        boolean isAuthenticated = loginService.authenticate(username, password);
        if (isAuthenticated) {
            session.setAttribute("user", username);
            return "redirect:/dashboard.ex";
        } else {
            model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/login";
        }
    }
    
    @GetMapping("/logout.ex")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.ex";
    }
	
	@GetMapping("/signup.ex")
    public String showSignupForm(Model model) {
        model.addAttribute("userForm", new UserVO());
        return "login/signup";
    }

    @PostMapping("/signup.ex")
    public String signup(UserVO user, HttpServletRequest request, Model model) {
    	try {
    		user.setUser_ip(getClientIp(request));
			loginService.saveUser(user);
			model.addAttribute("message", "회원가입에 성공하였습니다.");
	        return "login/login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "회원가입 실패");
	        return "login/signup";
		}
    }
    
    @GetMapping("/checkUserId.ex")
    public ResponseEntity<String> checkUserId(@RequestParam String userId) throws Exception {
    	int count = loginService.checkUserId(userId);
    	if(count == 0) {
    		return ResponseEntity.ok().body("success");
    	}else {
    		return ResponseEntity.ok().body("duplicate");
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
}
