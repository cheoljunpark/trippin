package com.trippin.api;

import static com.trippin.api.response.JSendResponseEntity.success;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}")
public class HealthCheckController {

  @GetMapping("/healthcheck")
  public ResponseEntity<?> ping() {
    JSONObject obj = new JSONObject();
    obj.put("health", "healthy");
    return ResponseEntity.ok(success(obj));
  }

}
