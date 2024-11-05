package com.bsw.groupware.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexContoller {
	
	@RequestMapping("/")
	public String index(){
		return "test";
	}

}
