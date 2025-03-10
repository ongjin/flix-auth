# OpenJDK 17 기반 경량 이미지 사용
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# Maven 빌드 산출물(JAR 파일)을 컨테이너로 복사
COPY target/flix-auth-0.0.1-SNAPSHOT.jar app.jar

# 8050 포트 개방
EXPOSE 8050

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
