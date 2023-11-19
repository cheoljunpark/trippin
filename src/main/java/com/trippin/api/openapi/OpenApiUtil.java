package com.trippin.api.openapi;

import com.trippin.api.course.domain.AreaCode;
import com.trippin.api.course.domain.Course;
import com.trippin.api.course.domain.SigunguCode;
import com.trippin.api.course.domain.Spot;
import com.trippin.api.course.repository.AreaCodeRepository;
import com.trippin.api.course.repository.CourseRepository;
import com.trippin.api.course.repository.SigunguCodeRepository;
import com.trippin.api.course.repository.SpotRepository;
import com.trippin.api.openapi.area.AreaApi;
import com.trippin.api.openapi.course.CourseApi;
import com.trippin.api.openapi.sigungu.SigunguApi;
import com.trippin.api.openapi.spot.SpotApi;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Transactional
@Component
public class OpenApiUtil {

  private final AreaApi areaApi;
  private final CourseApi courseApi;
  private final SigunguApi sigunguApi;
  private final SpotApi spotApi;
  private final AreaCodeRepository areaCodeRepository;
  private final SigunguCodeRepository sigunguCodeRepository;
  private final CourseRepository courseRepository;
  private final SpotRepository spotRepository;

  private static JSONParser parser = new JSONParser();
  
  private JSONArray parse(JSONObject jsonObject) {
    JSONObject response = (JSONObject) jsonObject.get("response");
    JSONObject body = (JSONObject) response.get("body");
    JSONObject items = (JSONObject) body.get("items");
    JSONArray item = (JSONArray) items.get("item");
    return item;
  }

  @Bean
  private void dbAreaCode() throws IOException, ParseException {

    // JSONParser로 JSONObject로 변환
    JSONObject jsonObject = (JSONObject) parser.parse(
        OpenApiExplorer.getData(areaApi.getAreaCode()));

    // JSONObject에서 item 찾기
    JSONArray item = parse(jsonObject);

    // DB에 저장
    for (int i = 0; i < item.size(); i++) {
      String code = (String) ((JSONObject) item.get(i)).get("code");
      String name = (String) ((JSONObject) item.get(i)).get("name");
      areaCodeRepository.save(new AreaCode(code, name));
    }

  }

  @Bean
  private void dbSigunguCode() throws IOException, ParseException {

    // JSONParser로 JSONObject로 변환
    JSONObject jsonObject = (JSONObject) parser.parse(
        OpenApiExplorer.getData(areaApi.getAreaCode()));

    // JSONObject에서 item 찾기
    JSONArray item = parse(jsonObject);

    // 각 지역코드(areaCode)에 맞는 url 가져오기
    for (int i = 0; i < item.size(); i++) {
      String areaCode = (String) ((JSONObject) item.get(i)).get("code");
      JSONObject jsonObject2 = (JSONObject) parser.parse(
          OpenApiExplorer.getData(sigunguApi.getSigunguCode(areaCode)));
      JSONArray item2 = parse(jsonObject2);

      // 각 지역코드(areaCode)마다 시군구코드(sigunguCode) 가져오기
      for (int j = 0; j < item2.size(); j++) {
        String sigunguCode = (String) ((JSONObject) item2.get(j)).get("code");
        String sigunguName = (String) ((JSONObject) item2.get(j)).get("name");
        SigunguCode sc = new SigunguCode();
        sc.setCode(sigunguCode);
        sc.setAreaCode(areaCode);
        sc.setName(sigunguName);
        sigunguCodeRepository.save(sc); // DB에 저장
      }
    }
  }

  @Bean
  private void dbCourseInfo() throws IOException, ParseException {
    // "지역기반 관광정보조회"에서 여행코스(25)의 "contentid"를 가져온다
    JSONObject jsonObject = (JSONObject) parser.parse(
        OpenApiExplorer.getData(courseApi.getCourseContentId()));
    JSONArray item = parse(jsonObject);

    // "공통정보조회"에서 "contentid"를 기반으로 데이터를 가져온다
    // 각 "contentid"별로 공통정보조회 조회
    for (int i = 0; i < item.size(); i++) {
      String contentId = (String) ((JSONObject) item.get(i)).get("contentid");
      JSONObject jsonObject2 = (JSONObject) parser.parse(
          OpenApiExplorer.getData(courseApi.getCourseInfo(contentId)));
      JSONArray item2 = parse(jsonObject2);

      // "distance"는 "소개정보조회에서" 추출해야함
      JSONObject jsonObject3 = (JSONObject) parser.parse(
          OpenApiExplorer.getData(courseApi.getCourseDistance(contentId)));
      JSONArray item3 = parse(jsonObject3);
      String distance = (String) ((JSONObject) item3.get(0)).get("distance");

      // 조회한 공통정보에서 데이터 추출
      String title = (String) ((JSONObject) item2.get(0)).get("title");
      String addr1 = (String) ((JSONObject) item2.get(0)).get("addr1");
      String mapx = (String) ((JSONObject) item2.get(0)).get("mapx");
      String mapy = (String) ((JSONObject) item2.get(0)).get("mapy");
      String firstimage = (String) ((JSONObject) item2.get(0)).get("firstimage");
      String cat1 = (String) ((JSONObject) item2.get(0)).get("cat1");
      String overview = (String) ((JSONObject) item2.get(0)).get("overview");
      String areacode = (String) ((JSONObject) item2.get(0)).get("areacode");
      String sigungucode = (String) ((JSONObject) item2.get(0)).get("sigungucode");

      // DB에 저장
      Course course = new Course();
      course.setTitle(title);
      course.setDistance(distance);
      course.setAddress(addr1);
      course.setMapx(mapx);
      course.setMapy(mapy);
      course.setImage(firstimage);
      course.setCategory(cat1);
      course.setOverview(overview);
      course.setAreaCode(areacode);
      course.setSigunguCode(sigungucode);
      courseRepository.save(course);
    }

  }

  @Bean
  private void dbSpotInfo() throws IOException, ParseException, URISyntaxException {

    // "지역기반 관광정보조회"에서 관광지(12)의 "contentid"를 가져온다
    String rawData = OpenApiExplorer.fetch(spotApi.getSpotContentId());
    String cleanedData = rawData.replaceAll("\\<.*?\\>", "");
    JSONObject jsonObject = (JSONObject) parser.parse(cleanedData);
    JSONArray item = parse(jsonObject);

    // "공통정보조회"에서 "contentid"를 기반으로 데이터를 가져온다
    // 각 "contentid"별로 공통정보조회 조회
    for (int i = 0; i < item.size(); i++) {
      String contentId = (String) ((JSONObject) item.get(i)).get("contentid");
      String rawData2 = OpenApiExplorer.fetch(spotApi.getSpotInfo(contentId));
      String cleanedData2 = rawData2.replaceAll("\\<.*?\\>", "");
      JSONObject jsonObject2 = (JSONObject) parser.parse(cleanedData2);
      JSONArray item2 = parse(jsonObject2);

      // 조회한 공통정보에서 데이터 추출
      String title = (String) ((JSONObject) item2.get(0)).get("title");
      String addr1 = (String) ((JSONObject) item2.get(0)).get("addr1");
      String mapx = (String) ((JSONObject) item2.get(0)).get("mapx");
      String mapy = (String) ((JSONObject) item2.get(0)).get("mapy");
      String firstimage = (String) ((JSONObject) item2.get(0)).get("firstimage");
      String cat1 = (String) ((JSONObject) item2.get(0)).get("cat1");
      String overview = (String) ((JSONObject) item2.get(0)).get("overview");
      String areacode = (String) ((JSONObject) item2.get(0)).get("areacode");
      String sigungucode = (String) ((JSONObject) item2.get(0)).get("sigungucode");

      // DB에 저장
      Spot spot = new Spot();
      spot.setTitle(title);
      spot.setAddress(addr1);
      spot.setMapx(mapx);
      spot.setMapy(mapy);
      spot.setImage(firstimage);
      spot.setCategory(cat1);
      spot.setOverview(overview);
      spot.setAreaCode(areacode);
      spot.setSigunguCode(sigungucode);
      spotRepository.save(spot);
    }

  }

}
