package com.bsw.groupware.login.service;

import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.UserVO;

public interface LoginService {
	
	int checkUserId(String userId) throws Exception;
	
	void saveUser(UserVO user) throws Exception;

	boolean authenticate(String username, String password);

	UserVO modify(String userId);
	
}
