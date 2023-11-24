package com.trippin.api.openapi.api;

import com.trippin.api.util.YamlLoadFactory;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application-api.yml"}, factory = YamlLoadFactory.class)
public class SigunguApi {

  @Value("${api.open-api.service-key}")
  private String serviceKey;

  public URL getSigunguCode(String areaCode) throws IOException {

    StringBuilder urlBuilder = new StringBuilder(
        "http://apis.data.go.kr/B551011/KorService1/areaCode1");
    urlBuilder.append(
        "?" + URLEncoder.encode("serviceKey", "UTF-8")
            + "=" + serviceKey);
    urlBuilder.append(
        "&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("trippin",
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json",
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100",
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode,
            "UTF-8"));

    return new URL(urlBuilder.toString());
  }
}
