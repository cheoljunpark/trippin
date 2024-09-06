package com.trippin.api.response;

public enum ResponseStatus {
  SUCCESS("success"),
  FAIL("fail"),
  ERROR("error");

  private final String status;

  ResponseStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
