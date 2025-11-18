# ⚡ Quick Start Guide

Spring Boot Monolithic App Starter Kit를 5분 안에 시작하세요!

## 🎯 3단계로 시작하기

### 1️⃣ 데이터베이스 시작
```bash
docker-compose up -d
```

### 2️⃣ 애플리케이션 실행
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 3️⃣ Swagger UI 접속
```
http://localhost:8081/swagger-ui.html
```

---

## 📋 체크리스트

시작하기 전에 다음 항목을 확인하세요:

- [ ] Java 17 설치 확인: `java -version`
- [ ] Docker 실행 확인: `docker --version`
- [ ] MySQL 컨테이너 실행: `docker-compose ps`
- [ ] 애플리케이션 빌드: `./gradlew clean build`

---

## 🚀 핵심 기능 테스트

### 1. API 문서 확인
- Swagger UI: http://localhost:8081/swagger-ui.html
- API Docs JSON: http://localhost:8081/v3/api-docs

### 2. 로그 확인
애플리케이션 실행 시 자동으로 출력되는 로그:
```
===== [GET] /api/users - UserController.getUsers 시작 =====
[Service] UserService.findAll 호출
[Service] UserService.findAll 완료 (25ms)
===== [GET] /api/users - 완료 (35ms) =====
```

### 3. 예외 처리 테스트
존재하지 않는 리소스 요청 시:
```json
{
  "statusCode": 404,
  "error": "Not Found",
  "message": "리소스를 찾을 수 없습니다.",
  "timestamp": "2025-11-18T16:30:21.187"
}
```

---

## 🔑 주요 설정

### JWT Secret Key
`src/main/resources/application-dev.yml`:
```yaml
jwt:
  accessSecret: {your-secret-key}  # 필수: 안전한 키로 변경
```

**안전한 키 생성:**
```bash
openssl rand -base64 64
```

### 데이터베이스 연결
기본 설정 (Docker Compose):
- **Host**: localhost:3306
- **Database**: collabodb
- **User**: collabo
- **Password**: collabo1234

---

## 🎨 다음 단계

1. **도메인 추가**: `domain/` 패키지에 새로운 엔티티/서비스 생성
2. **API 구현**: Controller에 비즈니스 로직 추가
3. **테스트**: Swagger UI에서 API 테스트
4. **커스터마이징**: 설정 파일 수정

---

## 📚 상세 문서

자세한 내용은 [README.md](../README.md)를 참조하세요.

---

## 🆘 문제 해결

### 포트 충돌 (8081)
```bash
# 사용 중인 프로세스 확인
lsof -i :8081

# 또는 포트 변경
# application-dev.yml에서 server.port 수정
```

### MySQL 연결 실패
```bash
# 컨테이너 상태 확인
docker-compose ps

# 재시작
docker-compose restart mysql

# 로그 확인
docker-compose logs mysql
```

### 빌드 오류
```bash
# Gradle 캐시 정리 후 재빌드
./gradlew clean build --refresh-dependencies
```

---

## ✅ 확인 사항

모든 것이 정상적으로 작동하는지 확인:

1. ✅ 애플리케이션 시작: `Started CollaboApplication` 로그 확인
2. ✅ Swagger 접속: http://localhost:8081/swagger-ui.html
3. ✅ MySQL 연결: 콘솔에 에러 없음
4. ✅ AOP 로깅: API 호출 시 로그 출력

---

**🎉 축하합니다! 이제 개발을 시작할 준비가 되었습니다.**

더 자세한 가이드는 [README.md](../README.md)를 확인하세요.

