package com.bsw.groupware.menu.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsw.groupware.menu.service.MenuService;
import com.bsw.groupware.model.MenuVO;

import ch.qos.logback.classic.Logger;

@RestController
public class MenuContoller {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/")
	public String index() throws Exception{
		MenuVO menu = menuService.menu();
		
		System.out.println(menu.getMenu_id());
		System.out.println(menu.getMenu_nm());
		System.out.println(menu.getMenu_seq());
		System.out.println(menu.getMenu_url());
		// ddd
		return "test";
	}

}
