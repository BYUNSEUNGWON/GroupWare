package com.bsw.groupware.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.mapper.LoginMapper;
import com.bsw.groupware.model.UserVO;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginMapper loginMapper;
	
	private NaverService naverService;
	
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	@Override
	public int checkUserId(String userId) throws Exception {
		int count = loginMapper.getCheckUserId(userId);
		return count;
	}
	
    @Transactional
	@Override
	public void saveUser(UserVO user) throws Exception {
    	if(user.getPassword() != null && !user.getPassword().isEmpty()) {
    		String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);	
    	}
		try {
            loginMapper.saveUser(user);	
		} catch (Exception e) {
			throw new Exception("User 저장 실패", e);
		}
	}

	@Override
	public boolean authenticate(String username, String password) {
        UserVO user = loginMapper.findByUsername(username);
        if(user != null) {
            if (user.isNaverUser()) {
            	System.out.println("naverUser True");
            	return true;
            } else if(user.isKakaoUser()) {
            	System.out.println("kakaoUser True");
            	return true;
            } else if(passwordEncoder.matches(password, user.getPassword())){
            	System.out.println("passwd encoding start");
            	return true;
            } else {
            	System.out.println("user null");
            	return false;
            }
        }else {
        	System.out.println("mapper is null");
        	return false;
        }
	}

}
