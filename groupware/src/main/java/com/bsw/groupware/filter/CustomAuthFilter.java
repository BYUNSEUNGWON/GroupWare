package com.bsw.groupware.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthFilter implements Filter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	logger.debug("init Start");
    	logger.debug("init End");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	logger.debug("doFilter Start");
		/*
		 * HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		 * HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		 * 
		 * String loginURI = httpServletRequest.getContextPath() + "/login.ex"; String
		 * signupURI = httpServletRequest.getContextPath() + "/signup.ex"; String
		 * doLoginURI = httpServletRequest.getContextPath() + "/doLogin.ex"; String
		 * naverLoginURI = httpServletRequest.getContextPath() +
		 * "/naverLoginCallback.ex"; String kakaoLoginURI =
		 * httpServletRequest.getContextPath() + "/kakaoCallback.ex"; String
		 * checkUserIdURI = httpServletRequest.getContextPath() + "/checkUserId.ex";
		 * 
		 * boolean loggedIn = httpServletRequest.getSession().getAttribute("user") !=
		 * null; boolean loginRequest =
		 * httpServletRequest.getRequestURI().equals(loginURI); boolean signupRequest =
		 * httpServletRequest.getRequestURI().equals(signupURI); boolean doLoginRequest
		 * = httpServletRequest.getRequestURI().equals(doLoginURI); boolean
		 * naverLoginRequest = httpServletRequest.getRequestURI().equals(naverLoginURI);
		 * boolean kakaoLoginRequest =
		 * httpServletRequest.getRequestURI().equals(kakaoLoginURI); boolean
		 * checkUserIdURIrRequest =
		 * httpServletRequest.getRequestURI().equals(checkUserIdURI);
		 * 
		 * if (loggedIn || loginRequest || signupRequest || doLoginRequest ||
		 * naverLoginRequest || kakaoLoginRequest || checkUserIdURIrRequest) {
		 * chain.doFilter(request, response); } else {
		 * httpServletResponse.sendRedirect(loginURI); }
		 */

    	logger.debug("doFilter end");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    	logger.debug("destroy Start");
    	logger.debug("destroy end");
    }
}