# Flix Auth Service

인증 및 권한 관리를 위한 마이크로서비스입니다. JWT를 사용하여 사용자 인증을 처리하고, PostgreSQL을 사용하여 사용자 데이터를 관리합니다.

## 주요 기능

-   사용자 인증 및 권한 관리
-   JWT 토큰 발급 및 검증
-   사용자 등록 및 관리
-   비밀번호 암호화
-   세션 관리 연동

## 기술 스택

-   Java 17
-   Spring Boot 3.4.3
-   Spring Security
-   Spring Data JPA
-   PostgreSQL
-   JWT (JSON Web Token)
-   Spring Cloud Config
-   Maven

## API 엔드포인트

### 인증 관리

-   `POST /api/v1/auth/signup`

    -   새로운 사용자 등록
    -   Request Body:
        ```json
        {
            "email": "string",
            "username": "string",
            "password": "string"
        }
        ```
    -   Validation:
        -   email: 필수, 이메일 형식
        -   username: 필수, 3-20자
        -   password: 필수, 최소 6자
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "User registered successfully",
            "data": null
        }
        ```

-   `POST /api/v1/auth/login`

    -   사용자 로그인
    -   Request Body:
        ```json
        {
            "email": "string",
            "password": "string"
        }
        ```
    -   Validation:
        -   email: 필수, 이메일 형식
        -   password: 필수
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "Login successful",
            "data": {
                "accessToken": "string",
                "refreshToken": "string",
                "userId": "number",
                "username": "string"
            }
        }
        ```

-   `POST /api/v1/auth/refresh`

    -   JWT 토큰 갱신
    -   Request Body:
        ```json
        {
            "refreshToken": "string"
        }
        ```
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "Token refreshed successfully",
            "data": {
                "accessToken": "string",
                "refreshToken": "string"
            }
        }
        ```

-   `POST /api/v1/auth/logout`
    -   로그아웃
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "Logout successful",
            "data": null
        }
        ```

### 사용자 관리 (관리자 전용)

-   `GET /api/v1/auth/users`

    -   모든 사용자 목록 조회
    -   권한: ROLE_ADMIN
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "Users retrieved successfully",
            "data": [
                {
                    "id": "number",
                    "email": "string",
                    "username": "string",
                    "role": "ROLE_USER|ROLE_ADMIN|ROLE_BUYER",
                    "enabled": "boolean",
                    "lastLoginAt": "datetime",
                    "createdAt": "datetime",
                    "updatedAt": "datetime"
                }
            ]
        }
        ```

-   `GET /api/v1/auth/users/{id}`

    -   특정 사용자 정보 조회
    -   권한: ROLE_ADMIN
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "User retrieved successfully",
            "data": {
                "id": "number",
                "email": "string",
                "username": "string",
                "role": "ROLE_USER|ROLE_ADMIN|ROLE_BUYER",
                "enabled": "boolean",
                "lastLoginAt": "datetime",
                "createdAt": "datetime",
                "updatedAt": "datetime"
            }
        }
        ```

-   `DELETE /api/v1/auth/users/{id}`

    -   사용자 삭제
    -   권한: ROLE_ADMIN
    -   Response:
        ```json
        {
            "status": "SUCCESS",
            "message": "User deleted successfully",
            "data": null
        }
        ```

## 사용자 권한

-   `ROLE_USER`: 일반 사용자
-   `ROLE_ADMIN`: 관리자
-   `ROLE_BUYER`: 구매자

## 로컬 개발 환경 설정

1. PostgreSQL 데이터베이스 실행

    ```bash
    # Docker 사용
    docker run --name postgres -e POSTGRES_PASSWORD=your_password -p 5432:5432 -d postgres

    # 또는 로컬 PostgreSQL 사용
    sudo service postgresql start
    ```

2. 환경 변수 설정

    ```bash
    # .env 파일 생성
    DB_HOST=localhost
    DB_PORT=5432
    DB_NAME=flix_auth
    DB_USER=postgres
    DB_PASSWORD=[your_password]
    JWT_SECRET=[your_jwt_secret]
    JWT_EXPIRATION=86400000
    ```

3. 애플리케이션 실행
    ```bash
    mvn spring-boot:run
    ```

## 사용자 데이터 구조

```json
{
    "id": "number",
    "email": "string",
    "username": "string",
    "password": "string (encrypted)",
    "role": "ROLE_USER|ROLE_ADMIN|ROLE_BUYER",
    "enabled": "boolean",
    "lastLoginAt": "datetime",
    "createdAt": "datetime",
    "updatedAt": "datetime"
}
```

## 테스트

```bash
./mvnw test
```

## 프로젝트 구조

```
flix-auth/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/zerry/auth/
│   │   │       ├── controller/
│   │   │       │   └── AuthController.java
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   └── AuthServiceImpl.java
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java
│   │   │       ├── dto/
│   │   │       │   ├── UserDto.java
│   │   │       │   ├── LoginRequestDto.java
│   │   │       │   └── SignupRequestDto.java
│   │   │       ├── entity/
│   │   │       │   └── User.java
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── JwtConfig.java
│   │   │       ├── security/
│   │   │       │   ├── JwtTokenProvider.java
│   │   │       │   └── UserDetailsServiceImpl.java
│   │   │       └── global/
│   │   │           ├── exception/
│   │   │           │   └── GlobalExceptionHandler.java
│   │   │           └── dto/
│   │   │               └── ApiResponse.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── Dockerfile
└── pom.xml
```
