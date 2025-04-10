package com.bsw.groupware.dashboard.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bsw.groupware.dashboard.service.DashBoardService;
import com.bsw.groupware.mapper.DashBoardMapper;
import com.bsw.groupware.model.NaverNewsResponseVO;
import com.bsw.groupware.model.TeamsVO;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class DashBoardServiceImpl implements DashBoardService{

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	DashBoardMapper dashBoardMapper;
	
	@Autowired
    private RestTemplate restTemplate;
	
    @Value("${naver.news.id}")
    private String clientId;

    @Value("${naver.news.secretl}")
    private String clientSecret;

    @Value("${naver.api.url}")
    private String apiUrl;

	@Override
	public void startJob(String user) {
		dashBoardMapper.startJob(user);
	}

	@Override
	public Map getSelctJob(String user) {
		return dashBoardMapper.getSelctJob(user);
	}

	@Override
	public void endJob(String user) {
		dashBoardMapper.endJob(user);
	}

	@Override
	public NaverNewsResponseVO getTopHeadlines() {

        String text = null;
        try {
            text = URLEncoder.encode("IT", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = apiUrl + text;


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NaverNewsResponseVO> response = restTemplate.exchange(apiURL, HttpMethod.GET, entity, NaverNewsResponseVO.class);
            NaverNewsResponseVO responseBody = response.getBody();
            logger.info("Response Body: {}", responseBody);
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 요청과 응답 실패", e);
        }
    }

	@Override
	public List<TeamsVO> getTeamsTitle(String user) {
        List<TeamsVO> teamsList = dashBoardMapper.getTeamsTitle(user);

        for (TeamsVO team : teamsList) {
            String link = "/teams/detail?title=" + team.getTitle() + "&seq=" + team.getSeq();
            team.setLink(link);
        }
        
        return teamsList;
	}
        

    
}
