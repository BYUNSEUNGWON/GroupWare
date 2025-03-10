package com.bsw.groupware.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HolidayUtil {
    private static final String API_KEY = "IgDn21D6Yq53fxy9vJVV%2BJ3f8LqHNFMP4X%2F9PiX5SCdKYeDsVZqXUiWpkSyR23cylShHzCwvweuC0fbDYRtOgA%3D%3D"; 
    private static final String ENDPOINT = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";

    public static List<Map<String, Object>> getHolidays(int year) throws IOException {
        String urlStr = String.format("%s?ServiceKey=%s&solYear=%d&_type=json", ENDPOINT, API_KEY, year);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = conn.getInputStream();
            String responseBody = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode items = rootNode.path("response").path("body").path("items").path("item");

            List<Map<String, Object>> holidays = new ArrayList<>();
            for (JsonNode item : items) {
                String dateName = item.path("dateName").asText();
                String locdate = item.path("locdate").asText();
                String yearStr = locdate.substring(0, 4);
                String monthStr = locdate.substring(4, 6);
                String dayStr = locdate.substring(6, 8);
                String formattedDate = String.format("%s-%s-%s", yearStr, monthStr, dayStr);

                holidays.add(Map.of("title", dateName, "start", formattedDate));
            }

            return holidays;
        } else {
            throw new IOException("HTTP request failed with response code " + responseCode);
        }
    }
}
