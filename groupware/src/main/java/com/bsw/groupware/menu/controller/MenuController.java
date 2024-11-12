package com.bsw.groupware.menu.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class MenuController {
	
	
	@RequestMapping("/")
	public void index(HttpServletResponse response) throws Exception{
		String login = "login.ex";
		response.sendRedirect(login);
	}

}
