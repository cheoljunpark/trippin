# 관통 프로젝트 - Trippin Web

![Trippin Logo](../assets/img/readme/trippin_logo.png)

---

## 요구사항

-   Java 1.8
-   SpringBoot 2.7.17
-   Gradle 8.4
-   MySQL 8.0.21

### application-api 파일 설정

```
# OPEN API 키
api:
  version: # version here
  open-api:
    service-key: # service key here
```

### application-db 파일 설정

```
# DB
spring:
  datasource:
    driver-class-name: # db driver here
    url: # db url here
    username: # db username here
    password: # db password here
```

### application-jwt 파일 설정

```
# JWT
jwt:
  access-token:
    expiretime: # jwt access token expire time here
  refresh-token:
    expiretime: # jwt refresh token expire time here
  secret: # jwt secret here
```

## 사용기술

-   [<img src="https://spring.io/img/projects/spring-framework.svg?v=2" width="50" />](https://spring.io/projects/spring-framework)
-   [<img src="https://spring.io/img/projects/spring-boot.svg" width="50" />](https://spring.io/projects/spring-boot)
-   [<img src="https://spring.io/img/projects/spring-security.svg" width="50" />](https://spring.io/projects/spring-security)
