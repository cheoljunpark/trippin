package com.trippin.api.openapi.course;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CourseApi {

  @Value("${open-api.service-key}")
  private String serviceKey;

  public URL getCourseContentId() throws IOException {

    StringBuilder urlBuilder = new StringBuilder(
        "http://apis.data.go.kr/B551011/KorService1/areaBasedList1");
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
        "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("2000",
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("25",
            "UTF-8"));

    return new URL(urlBuilder.toString());
  }

  public URL getCourseInfo(String contentId) throws IOException {

    StringBuilder urlBuilder = new StringBuilder(
        "http://apis.data.go.kr/B551011/KorService1/detailCommon1");
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
        "&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentId,
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("25",
            "UTF-8"));

    return new URL(urlBuilder.toString());
  }

  public URL getCourseDistance(String contentId) throws IOException {

    StringBuilder urlBuilder = new StringBuilder(
        "http://apis.data.go.kr/B551011/KorService1/detailIntro1");
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
        "&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentId,
            "UTF-8"));
    urlBuilder.append(
        "&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("25",
            "UTF-8"));

    return new URL(urlBuilder.toString());
  }

}
