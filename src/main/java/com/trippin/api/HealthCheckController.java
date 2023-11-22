package com.trippin.api;

import static com.trippin.api.response.JSendResponseEntity.success;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "헬스체크")
@RestController
@RequestMapping("${api.version}")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HealthCheckController {

  @ApiOperation(
      value = "헬스 체크 api",
      notes = "헬스 체크하는 API")
  @GetMapping("/healthcheck")
  public ResponseEntity<?> ping() {
    JSONObject obj = new JSONObject();
    obj.put("health", "healthy");
    return ResponseEntity.ok(success(obj));
  }

}
