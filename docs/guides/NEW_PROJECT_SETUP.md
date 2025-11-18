# 🔄 새 프로젝트 시작하기

> 이 스타터 키트를 복제하여 새로운 프로젝트를 시작하는 방법

이 가이드는 `collabo` 프로젝트를 기반으로 새로운 프로젝트를 시작할 때 필요한 모든 단계를 설명합니다.

---

## 📋 전체 체크리스트

프로젝트 이름을 `collabo`에서 `yourproject`로 변경한다고 가정합니다.

- [ ] 리포지토리 복제 및 Git 설정
- [ ] 프로젝트 이름 변경 (`settings.gradle`)
- [ ] 패키지명 변경 (Refactor)
- [ ] 메인 클래스명 변경
- [ ] 데이터베이스 설정 변경
- [ ] JWT Secret Key 재생성
- [ ] Swagger 설정 업데이트
- [ ] README 업데이트
- [ ] 빌드 및 테스트

---

## 🚀 1단계: 리포지토리 복제

### Option A: GitHub Template 사용 (권장)

GitHub에서 스타터 키트를 **Template Repository**로 설정한 경우:

1. 스타터 키트 리포지토리로 이동
2. **"Use this template"** 버튼 클릭
3. 새 리포지토리 이름 입력 (예: `yourproject`)
4. **"Create repository from template"** 클릭

```bash
# 새로 생성된 리포지토리 클론
git clone https://github.com/your-username/yourproject.git
cd yourproject
```

### Option B: 수동 복제

```bash
# 스타터 키트 복제
git clone https://github.com/your-username/spring-boot-starter-kit.git yourproject
cd yourproject

# 기존 Git 히스토리 제거 (선택사항)
rm -rf .git
git init
git branch -M main

# 새 리모트 추가
git remote add origin https://github.com/your-username/yourproject.git
```

---

## 📝 2단계: 프로젝트 이름 변경

### 2.1. `settings.gradle` 수정

**위치**: 프로젝트 루트

**변경 전:**
```gradle
rootProject.name = 'collabo'
```

**변경 후:**
```gradle
rootProject.name = 'yourproject'
```

### 2.2. `build.gradle` 확인 (필요시)

**위치**: 프로젝트 루트

```gradle
group = 'com.example'  // 필요시 변경
version = '0.0.1-SNAPSHOT'
```

---

## 📦 3단계: 패키지명 및 클래스명 변경

### 3.1. 패키지명 변경 (IntelliJ IDEA)

**패키지 경로**: `src/main/java/com/example/collabo`

1. IntelliJ IDEA에서 프로젝트 열기
2. `src/main/java/com/example/collabo` 디렉토리 우클릭
3. **Refactor** → **Rename** 선택
4. `collabo` → `yourproject` 입력
5. **Scope**: "Whole project" 선택
6. **Refactor** 버튼 클릭

IntelliJ가 자동으로:
- 모든 패키지 import 문 업데이트
- 클래스 경로 업데이트
- 설정 파일 업데이트

### 3.2. 메인 클래스명 변경

**파일**: `src/main/java/com/example/yourproject/CollaboApplication.java`

1. 파일 우클릭 → **Refactor** → **Rename**
2. `CollaboApplication` → `YourprojectApplication`

**변경 전:**
```java
package com.example.collabo;

@SpringBootApplication
public class CollaboApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollaboApplication.class, args);
    }
}
```

**변경 후:**
```java
package com.example.yourproject;

@SpringBootApplication
public class YourprojectApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourprojectApplication.class, args);
    }
}
```

### 3.3. 테스트 클래스명 변경

**파일**: `src/test/java/com/example/collabo/CollaboApplicationTests.java`

1. 패키지명 변경: `com.example.collabo` → `com.example.yourproject`
2. 클래스명 변경: `CollaboApplicationTests` → `YourprojectApplicationTests`

**변경 후:**
```java
package com.example.yourproject;

@SpringBootTest
class YourprojectApplicationTests {
    @Test
    void contextLoads() {
    }
}
```

---

## 🗄️ 4단계: 데이터베이스 설정 변경

### 4.1. `compose.yaml` 수정

**위치**: 프로젝트 루트

**변경 전:**
```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: collaboapp-mysql
    environment:
      MYSQL_DATABASE: collabodb
      MYSQL_USER: collabo
      MYSQL_PASSWORD: collabo1234
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

**변경 후:**
```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: yourproject-mysql        # 변경
    environment:
      MYSQL_DATABASE: yourprojectdb          # 변경
      MYSQL_USER: yourproject                # 변경
      MYSQL_PASSWORD: yourproject1234        # 변경 권장
      MYSQL_ROOT_PASSWORD: root1234
    ports:
      - "3306:3306"
    volumes:
      - yourproject-data:/var/lib/mysql      # 변경

volumes:
  yourproject-data:                          # 변경
```

### 4.2. `application-dev.yml` 수정

**위치**: `src/main/resources/application-dev.yml`

**변경 전:**
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/collabodb?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: collabo
    password: collabo1234
```

**변경 후:**
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/yourprojectdb?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: yourproject
    password: yourproject1234
```

### 4.3. 기존 Docker 볼륨 정리

```bash
# 기존 컨테이너 및 볼륨 완전히 제거
docker-compose down -v

# 확인
docker volume ls | grep collabo
```

---

## 🔐 5단계: JWT Secret Key 재생성

**⚠️ 매우 중요: 보안을 위해 반드시 새로운 Secret Key를 생성하세요!**

### 5.1. Secret Key 생성

```bash
# macOS/Linux
openssl rand -base64 64

# 또는
python3 -c "import secrets; print(secrets.token_urlsafe(64))"
```

**출력 예시:**
```
A8b2C3d4E5f6G7h8I9j0K1l2M3n4O5p6Q7r8S9t0U1v2W3x4Y5z6A7b8C9d0E1f2G3h4I5j6K7l8M9n0O5p6Q7r8==
```

### 5.2. `application-dev.yml` 업데이트

**위치**: `src/main/resources/application-dev.yml`

```yaml
jwt:
  accessSecret: {여기에-새로-생성한-secret-key-붙여넣기}
  accessTokenValidity: 86400000      # 24시간 (필요시 변경)
  refreshTokenValidity: 604800000    # 7일 (필요시 변경)
```

### 5.3. 운영 환경 설정 (나중에)

**권장**: 운영 환경에서는 환경 변수 사용

```yaml
# application-prod.yml
jwt:
  accessSecret: ${JWT_SECRET}
  accessTokenValidity: ${JWT_ACCESS_VALIDITY:86400000}
  refreshTokenValidity: ${JWT_REFRESH_VALIDITY:604800000}
```

---

## 📚 6단계: Swagger 설정 업데이트

### 6.1. `SwaggerConfig.java` 수정

**위치**: `src/main/java/com/example/yourproject/global/config/SwaggerConfig.java`

**변경 전:**
```java
@Bean
public OpenAPI openAPI() {
    // ...existing code...
    return new OpenAPI()
            .info(new Info()
                    .title("Collabo API 문서")
                    .description("Collabo 프로젝트의 REST API 문서입니다.")
                    .version("v1.0.0"))
            // ...existing code...
}
```

**변경 후:**
```java
@Bean
public OpenAPI openAPI() {
    // ...existing code...
    return new OpenAPI()
            .info(new Info()
                    .title("YourProject API 문서")
                    .description("YourProject의 REST API 문서입니다.")
                    .version("v1.0.0"))
            // ...existing code...
}
```

---

## 📖 7단계: 문서 업데이트

### 7.1. 루트 `README.md` 업데이트

**위치**: 프로젝트 루트

```markdown
# 🚀 YourProject

> 프로젝트 설명을 여기에 작성하세요

## ✨ 주요 기능

- 기능 1 설명
- 기능 2 설명
- 기능 3 설명

## 🛠 기술 스택

- Java 17
- Spring Boot 3.5.3
- MySQL 8.0
- JWT 인증
- Swagger API 문서
- AOP 로깅

## 🚀 빠른 시작

```bash
# 데이터베이스 시작
docker-compose up -d

# 애플리케이션 실행
./gradlew bootRun --args='--spring.profiles.active=dev'

# Swagger UI
# http://localhost:8081/swagger-ui.html
```

## 📚 문서

- [Quick Start](docs/QUICKSTART.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Full Documentation](docs/README.md)

## 📝 라이선스

MIT License
```

### 7.2. `docs/README.md` 업데이트

프로젝트명과 관련된 모든 내용을 새 프로젝트에 맞게 수정하세요.

### 7.3. 불필요한 문서 제거 (선택사항)

스타터 키트 관련 문서를 제거하거나 보관:

```bash
# 선택 1: 스타터 키트 문서 보관
mkdir docs/starter-kit-docs
mv docs/IMPLEMENTATION_COMPLETE.md docs/starter-kit-docs/
mv docs/PROJECT_CHECKLIST.md docs/starter-kit-docs/

# 선택 2: 완전히 제거
rm docs/IMPLEMENTATION_COMPLETE.md
rm docs/PROJECT_CHECKLIST.md
```

---

## 🧪 8단계: 빌드 및 테스트

### 8.1. Gradle 빌드

```bash
# 의존성 다운로드 및 빌드
./gradlew clean build

# 테스트 제외하고 빌드
./gradlew clean build -x test
```

**예상 출력:**
```
BUILD SUCCESSFUL in 30s
```

### 8.2. 데이터베이스 시작

```bash
# Docker Compose로 MySQL 시작
docker-compose up -d

# 컨테이너 상태 확인
docker-compose ps

# 로그 확인
docker-compose logs mysql
```

### 8.3. 애플리케이션 실행

```bash
# dev 프로파일로 실행
./gradlew bootRun --args='--spring.profiles.active=dev'
```

**확인할 로그:**
```
Started YourprojectApplication in 3.456 seconds
The following 1 profile is active: "dev"
```

### 8.4. Swagger UI 확인

브라우저에서 접속:
```
http://localhost:8081/swagger-ui.html
```

**확인 사항:**
- API 문서 제목이 "YourProject API 문서"로 표시
- Authorize 버튼이 정상 작동
- 모든 엔드포인트가 정상 표시

### 8.5. 로그 확인

애플리케이션 로그에서 확인:
```
===== [GET] /api/users - UserController.getUsers 시작 =====
[Service] UserService.findAll 호출
```

---

## 🔄 9단계: Git 커밋

### 9.1. 변경사항 확인

```bash
git status
git diff
```

### 9.2. 커밋 및 푸시

```bash
# 모든 변경사항 추가
git add .

# 커밋
git commit -m "feat: Initialize project from starter kit

- Rename project from 'collabo' to 'yourproject'
- Update package names
- Configure database settings
- Generate new JWT secret key
- Update Swagger documentation
- Update README"

# 첫 푸시
git push -u origin main
```

---

## 🎨 10단계: 커스터마이징 (선택사항)

### 10.1. 포트 변경

`application-dev.yml`:
```yaml
server:
  port: 8080  # 8081 → 8080으로 변경
```

### 10.2. 로그 레벨 조정

`application-dev.yml`:
```yaml
logging:
  level:
    com.example.yourproject: INFO  # DEBUG → INFO
```

### 10.3. 성능 임계값 조정

`PerformanceAspect.java`:
```java
private static final long SLOW_THRESHOLD_MS = 5000; // 3000 → 5000
```

### 10.4. CORS 설정 (필요시)

`SecurityConfig.java`에 추가:
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## 🚀 빠른 변경 스크립트 (고급)

프로젝트 루트에 `rename-project.sh` 생성:

```bash
#!/bin/bash

# 사용법: ./rename-project.sh collabo yourproject

OLD_NAME=$1
NEW_NAME=$2

if [ -z "$OLD_NAME" ] || [ -z "$NEW_NAME" ]; then
    echo "❌ 사용법: ./rename-project.sh <기존이름> <새이름>"
    echo "   예시: ./rename-project.sh collabo myproject"
    exit 1
fi

echo "🔄 프로젝트 이름 변경: $OLD_NAME → $NEW_NAME"
echo ""

# 1. settings.gradle
echo "📝 settings.gradle 수정..."
sed -i.bak "s/rootProject.name = '$OLD_NAME'/rootProject.name = '$NEW_NAME'/g" settings.gradle
rm settings.gradle.bak

# 2. compose.yaml
echo "📝 compose.yaml 수정..."
sed -i.bak "s/${OLD_NAME}/${NEW_NAME}/g" compose.yaml
rm compose.yaml.bak

# 3. application-dev.yml
echo "📝 application-dev.yml 수정..."
sed -i.bak "s/${OLD_NAME}/${NEW_NAME}/g" src/main/resources/application-dev.yml
rm src/main/resources/application-dev.yml.bak

echo ""
echo "✅ 자동 변경 완료!"
echo ""
echo "⚠️  다음 단계를 수동으로 진행하세요:"
echo ""
echo "1. IntelliJ IDEA 열기"
echo "2. src/main/java/com/example/$OLD_NAME 우클릭"
echo "3. Refactor → Rename → '$NEW_NAME' 입력"
echo "4. ${OLD_NAME^}Application.java → ${NEW_NAME^}Application.java 이름 변경"
echo "5. JWT Secret Key 재생성:"
echo "   openssl rand -base64 64"
echo "6. application-dev.yml에 새 Secret Key 설정"
echo "7. SwaggerConfig.java에서 API 문서 제목 변경"
echo "8. README.md 업데이트"
echo ""
echo "완료 후:"
echo "   ./gradlew clean build"
echo "   docker-compose up -d"
echo "   ./gradlew bootRun"
echo ""
```

**사용 방법:**
```bash
chmod +x rename-project.sh
./rename-project.sh collabo yourproject
```

---

## ⚠️ 주의사항

### 🔐 보안
- **JWT Secret Key는 절대로 재사용 금지**
- 데이터베이스 비밀번호 변경 권장
- `.gitignore`에 민감한 정보 추가
- 운영 환경에서는 환경 변수 사용

### 🗄️ 데이터베이스
- 기존 Docker 볼륨 완전히 제거 (`docker-compose down -v`)
- 데이터베이스명, 사용자명 일치 확인

### 📦 패키지명
- IntelliJ의 Refactor 기능 사용 권장
- 수동 변경 시 모든 import 문 확인
- 테스트 코드도 함께 변경

### 📚 문서
- README 업데이트 필수
- 스타터 키트 관련 내용 제거
- 프로젝트 특화 내용 추가

---

## 🆘 문제 해결

### 빌드 실패

```bash
# Gradle 캐시 정리
./gradlew clean --refresh-dependencies

# IntelliJ 캐시 정리
# File → Invalidate Caches → Invalidate and Restart
```

### 패키지명 변경 실패

1. IntelliJ에서 Project Structure 확인 (⌘+;)
2. Modules → Sources 탭에서 소스 루트 확인
3. 수동으로 디렉토리 이름 변경 후 Reimport

### Docker 충돌

```bash
# 모든 컨테이너 정지
docker stop $(docker ps -aq)

# 볼륨 정리
docker volume prune

# 재시작
docker-compose up -d
```

### JWT 토큰 오류

`application-dev.yml`에서:
- `accessSecret` 값이 Base64 인코딩되었는지 확인
- 최소 256비트 이상인지 확인
- 공백이나 특수문자 확인

---

## 📋 최종 체크리스트

변경이 모두 완료되었는지 확인:

### 파일 및 설정
- [ ] `settings.gradle` - rootProject.name 변경
- [ ] 패키지명 변경 완료 (com.example.yourproject)
- [ ] 메인 클래스명 변경 (YourprojectApplication)
- [ ] 테스트 클래스명 변경 (YourprojectApplicationTests)
- [ ] `compose.yaml` - 모든 collabo → yourproject 변경
- [ ] `application-dev.yml` - 데이터베이스 설정 변경
- [ ] JWT Secret Key 새로 생성 및 설정
- [ ] `SwaggerConfig.java` - API 문서 제목 변경

### 문서
- [ ] 루트 `README.md` 업데이트
- [ ] `docs/README.md` 프로젝트명 변경
- [ ] 불필요한 스타터 키트 문서 정리

### 테스트
- [ ] `./gradlew clean build` 성공
- [ ] `docker-compose up -d` 성공
- [ ] 애플리케이션 정상 실행
- [ ] Swagger UI 정상 접속
- [ ] 로그 정상 출력

### Git
- [ ] Git 커밋 완료
- [ ] GitHub 푸시 완료
- [ ] `.gitignore` 확인

---

## 🎯 다음 단계

프로젝트 설정이 완료되었다면:

1. **비즈니스 로직 구현**
   - 도메인 모델 설계
   - Controller, Service, Repository 구현
   - [개발 가이드](../README.md#-개발-가이드) 참고

2. **API 문서화**
   - Swagger 어노테이션 추가
   - API 테스트 작성

3. **테스트 코드 작성**
   - 단위 테스트
   - 통합 테스트

4. **운영 환경 설정**
   - `application-prod.yml` 생성
   - 환경 변수 설정
   - 배포 스크립트 작성

---

## 📚 추가 리소스

- [개발 가이드](../README.md#-개발-가이드)
- [아키텍처 문서](../ARCHITECTURE.md)
- [AOP 가이드](../guides/aop-guide.md)
- [문제 해결](../README.md#-문제-해결)

---

<div align="center">

**🎉 새 프로젝트 설정 완료를 축하합니다!**

이제 비즈니스 로직 구현을 시작하세요!

문제가 발생하면 [Issues](https://github.com/your-username/spring-boot-starter-kit/issues)에 문의하세요.

</div>

