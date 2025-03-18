package com.bsw.groupware.login.service;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bsw.groupware.model.NaverVO;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class NaverService {

    @Value("${naver.client.id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client.secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${naver.redirect.url}")
    private String NAVER_REDIRECT_URL;

    private final static String NAVER_AUTH_URI = "https://nid.naver.com";
    private final static String NAVER_API_URI = "https://openapi.naver.com";

    // OkHttpClient 생성 메서드
    private OkHttpClient createUnsafeOkHttpClient() {
        try {
            // 신뢰를 모든 인증서에 대한 TrustManager를 설치
            final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                }
            };

            // 모든 호스트 이름을 무시하도록 허용
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getNaverLogin() {
        return NAVER_AUTH_URI + "/oauth2.0/authorize"
                + "?client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + NAVER_REDIRECT_URL
                + "&response_type=code";
    }

    public NaverVO getNaverInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed get authorization code");

        String accessToken = "";
        String refreshToken = "";

        OkHttpClient client = createUnsafeOkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", NAVER_CLIENT_ID)
                .add("client_secret", NAVER_CLIENT_SECRET)
                .add("code", code)
                .add("redirect_uri", NAVER_REDIRECT_URL)
                .build();

        Request request = new Request.Builder()
                .url(NAVER_AUTH_URI + "/oauth2.0/token")
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed with status code: " + response.code());
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.body().string());

            accessToken = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("API call failed: " + e.getMessage());
        }

        return getUserInfoWithToken(accessToken);
    }

    private NaverVO getUserInfoWithToken(String accessToken) throws Exception {
        OkHttpClient client = createUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(NAVER_API_URI + "/v1/nid/me")
                .post(RequestBody.create("", MediaType.parse("application/json; charset=utf-8")))
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed with status code: " + response.code());
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.body().string());
            JSONObject account = (JSONObject) jsonObj.get("response");

            String id = String.valueOf(account.get("id"));
            String email = String.valueOf(account.get("email"));
            String name = String.valueOf(account.get("name"));
            String birthday = String.valueOf(account.get("birthday"));
            String nickname = String.valueOf(account.get("nickname"));
            String mobile = String.valueOf(account.get("mobile"));

            mobile = mobile.replaceAll("-", "");

            NaverVO naver = new NaverVO();
            naver.setId(id);
            naver.setEmail(email);
            naver.setName(name);
            naver.setBirthday(birthday);
            naver.setMobile(mobile);
            naver.setNickname(nickname);

            return naver;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("API call failed: " + e.getMessage());
        }
    }
}