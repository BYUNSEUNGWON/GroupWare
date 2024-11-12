package com.bsw.groupware.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bsw.groupware.model.MenuVO;
import com.bsw.groupware.model.UserVO;

@Mapper
public interface LoginMapper {
	
	int getCheckUserId(String userId);

	void saveUser(UserVO user);

	UserVO findByUsername(String username);
}
