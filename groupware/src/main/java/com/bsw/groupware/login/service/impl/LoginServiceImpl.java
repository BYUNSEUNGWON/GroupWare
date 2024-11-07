package com.bsw.groupware.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.mapper.LoginMapper;
import com.bsw.groupware.model.UserVO;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginMapper loginMapper;
	
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	@Override
	public int checkUserId(String userId) throws Exception {
		int count = loginMapper.getCheckUserId(userId);
		return count;
	}
	
    @Transactional
	@Override
	public void saveUser(UserVO user) throws Exception {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            loginMapper.saveUser(user);	
		} catch (Exception e) {
			throw new Exception("User 저장 실패", e);
		}
	}
	

}
