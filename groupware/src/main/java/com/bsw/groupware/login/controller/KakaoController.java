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
import com.bsw.groupware.model.KakaoVO;
import com.bsw.groupware.model.NaverVO;
import com.bsw.groupware.model.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class KakaoController {
	
	@Autowired
	private KakaoService kakaoService;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/kakaoCallback.ex")
    public String callback(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        KakaoVO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
        int kakaoInfoCount = loginService.checkUserId(kakaoInfo.getId());

        if(kakaoInfoCount == 0) {
        	UserVO user = new UserVO();
        	user.setUser_id(kakaoInfo.getId());
        	user.setName(kakaoInfo.getNickname());
        	user.setKakaoUser(true);
        	loginService.saveUser(user);
        }

        redirectAttributes.addAttribute("username", kakaoInfo.getId());
        redirectAttributes.addAttribute("password", "");
		

        return "redirect:/doLogin.ex";
	}

}
