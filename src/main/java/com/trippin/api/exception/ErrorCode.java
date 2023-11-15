package com.trippin.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  // user error code
  MEMBER_NOT_FOUND(401, "유저정보를 찾을 수 없습니다."),
  DUPLICATED_EMAIL(409, "이미 가입되어있는 유저입니다."),
  PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
  LOGIN_FAIL(400, "아이디나 비밀번호를 확인하세요."),

  // === GLOBAL BASE ERROR CODE ===
  // 4xx: Client Errors
  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401, "Unauthorized"),
  FORBIDDEN(403, "Forbidden"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  CONFLICT(409, "Conflict"),

  // 5xx: Server Errors
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
  NOT_IMPLEMENTED(501, "Not Implemented"),
  BAD_GATEWAY(502, "Bad Gateway"),
  SERVICE_UNAVAILABLE(503, "Service Unavailable"),
  GATEWAY_TIMEOUT(504, "Gateway Timeout"),
  HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

  private final Integer errorCode;
  private final String errorMsg;
}
