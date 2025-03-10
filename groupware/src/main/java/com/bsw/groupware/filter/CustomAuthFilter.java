package com.bsw.groupware.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        String loginURI = httpServletRequest.getContextPath() + "/login.ex";
        String signupURI = httpServletRequest.getContextPath() + "/signup.ex";
        String doLoginURI = httpServletRequest.getContextPath() + "/doLogin.ex";
        String naverLoginURI = httpServletRequest.getContextPath() + "/naverLoginCallback.ex";
        String kakaoLoginURI = httpServletRequest.getContextPath() + "/kakaoCallback.ex";
        String checkUserIdURI = httpServletRequest.getContextPath() + "/checkUserId.ex";

        boolean loggedIn = httpServletRequest.getSession().getAttribute("user") != null;
        boolean loginRequest = httpServletRequest.getRequestURI().equals(loginURI);
        boolean signupRequest = httpServletRequest.getRequestURI().equals(signupURI);
        boolean doLoginRequest = httpServletRequest.getRequestURI().equals(doLoginURI);
        boolean naverLoginRequest = httpServletRequest.getRequestURI().equals(naverLoginURI);
        boolean kakaoLoginRequest = httpServletRequest.getRequestURI().equals(kakaoLoginURI);
        boolean checkUserIdURIrRequest = httpServletRequest.getRequestURI().equals(checkUserIdURI);
/*	개발용으로 임시로 막아둠
        if (loggedIn || loginRequest || signupRequest || doLoginRequest || naverLoginRequest || kakaoLoginRequest || checkUserIdURIrRequest) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(loginURI);
        }
*/
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}