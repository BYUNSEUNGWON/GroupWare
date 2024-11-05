package com.bsw.groupware.menu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsw.groupware.mapper.MenuMapper;
import com.bsw.groupware.menu.service.MenuService;
import com.bsw.groupware.model.MenuVO;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	MenuMapper menuMapper;

	@Override
	public MenuVO menu() throws Exception {
		MenuVO menu = new MenuVO();
		
		menu = menuMapper.getList();
				
		return menu;
	}

}
