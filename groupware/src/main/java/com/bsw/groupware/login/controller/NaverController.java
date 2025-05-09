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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class NaverController {
	
	@Autowired
	private NaverService naverService;
	
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/naverLoginCallback.ex")
    public String callback(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        NaverVO naverInfo = naverService.getNaverInfo(request.getParameter("code"));
        int naverInfoCount = loginService.checkUserId(naverInfo.getId());
        
        if(naverInfoCount == 0) {
        	UserVO user = new UserVO();
        	user.setUser_id(naverInfo.getId());
        	user.setEmail(naverInfo.getEmail());
        	user.setName(naverInfo.getName());
        	user.setPhone(naverInfo.getMobile());
        	user.setNickname(naverInfo.getNickname());
        	user.setNaverUser(true);
        	loginService.saveUser(user);
        }
        
        redirectAttributes.addAttribute("username", naverInfo.getId());
        redirectAttributes.addAttribute("password", "");

        return "redirect:/doLogin.ex";
	}

}
