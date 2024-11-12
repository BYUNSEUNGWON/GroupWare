package com.bsw.groupware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bsw.groupware.filter.CustomAuthFilter;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GroupwareApplication extends SpringBootServletInitializer {
	

    public static void main(String[] args) {
        SpringApplication.run(GroupwareApplication.class, args);
    }
    
    @Bean
    public FilterRegistrationBean<CustomAuthFilter> customAuthFilter() {
        FilterRegistrationBean<CustomAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomAuthFilter());
        registrationBean.addUrlPatterns("/*");
        
        return registrationBean;
    }

}
