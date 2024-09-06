package com.trippin.api.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class OpenApiExplorer {

  public static String getData(URL url) throws IOException {
    // 1. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    // 2. 통신을 위한 메소드 SET.
    conn.setRequestMethod("GET");

    // 3. 통신을 위한 Content-type SET.
    conn.setRequestProperty("Content-type", "application/json");

    // 4. 통신 응답 코드 확인.
    int responseCode = conn.getResponseCode();

    // 5. 전달 받은 데이터를 BufferedReader 객체로 저장.
    BufferedReader br;
    if (responseCode >= 200 && responseCode <= 300) {
      br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    } else {
      br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
    }

    // 6. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }

    // 7. 객체 해제.
    br.close();
    conn.disconnect();

    // 8. 전달 받은 데이터 리턴.
    return sb.toString();

  }

  public static String fetch(URL url) throws UnsupportedEncodingException, URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters()
        .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<?> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(url.toURI(), HttpMethod.GET, entity,
          String.class);
      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      System.out.println("Exception occurred! Status code: " + e.getStatusCode());
      System.out.println("Response body: " + e.getResponseBodyAsString());
      return null; // 혹은 적절한 에러 처리
    }
  }
}
