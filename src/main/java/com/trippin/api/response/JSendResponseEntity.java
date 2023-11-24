package com.trippin.api.response;

import static com.trippin.api.response.ResponseStatus.ERROR;
import static com.trippin.api.response.ResponseStatus.FAIL;
import static com.trippin.api.response.ResponseStatus.SUCCESS;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class JSendResponseEntity<T> {

  String status;
  T data;
  String message;

  @Builder
  public JSendResponseEntity(ResponseStatus status, String message) {
    this.status = status.getStatus();
    this.message = message;
  }

  public JSendResponseEntity(ResponseStatus status, T data) {
    this.status = status.getStatus();
    this.data = data;
  }

  public static <T> JSendResponseEntity<T> success(@Nullable T data) {
    return new JSendResponseEntity<>(SUCCESS, data);
  }

  public static <T> JSendResponseEntity<T> failure(T data) {
    return new JSendResponseEntity<>(FAIL, data);
  }

  public static JSendResponseEntity error(String message) {
    return JSendResponseEntity.builder()
        .status(ERROR)
        .message(message)
        .build();
  }

}
