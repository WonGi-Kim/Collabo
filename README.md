# 🚀 Spring Boot Monolithic App Starter Kit

> **JWT 인증, Swagger API 문서화, AOP 로깅, 예외 처리가 미리 설정된 Spring Boot 모놀리식 애플리케이션 스타터 키트**

엔터프라이즈급 애플리케이션 개발을 위한 완성도 높은 보일러플레이트로, 즉시 비즈니스 로직 구현을 시작할 수 있습니다.

---

## 🎯 새 프로젝트 시작하기

이 스타터 키트를 복제하여 새 프로젝트를 시작하려면:
- **[🔄 새 프로젝트 설정 가이드](docs/guides/NEW_PROJECT_SETUP.md)** - 프로젝트 이름 변경 및 초기 설정

또는 빠른 스크립트 사용:
```bash
./rename-project.sh collabo yourproject
```

---

## 📚 문서

- **[⚡ Quick Start](docs/QUICKSTART.md)** - 5분 안에 시작하기
- **[🏗️ Architecture](docs/ARCHITECTURE.md)** - 시스템 아키텍처 및 기술 스택
- **[📑 Documentation Index](docs/DOCUMENTATION_INDEX.md)** - 전체 문서 목록

---

## 📑 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)
- [핵심 기능 상세](#-핵심-기능-상세)
- [API 문서](#-api-문서)
- [개발 가이드](#-개발-가이드)
- [설정 정보](#-설정-정보)

---

## ✨ 주요 기능

### 🔐 JWT 인증 시스템
- **Bearer Token 기반 인증** - Stateless 아키텍처
- **JwtAuthFilter** - 요청 전처리 및 토큰 검증
- **CustomUserDetails** - Spring Security 통합
- **자동 만료 처리** - Access/Refresh Token 지원

### 📚 Swagger API 문서화
- **자동 API 문서 생성** - SpringDoc OpenAPI 3.0
- **JWT 인증 통합** - UI에서 바로 토큰 테스트
- **인터랙티브 UI** - 실시간 API 테스트 가능
- **개발 환경 전용** - 운영 환경 비활성화 설정

### 📊 AOP 기반 로깅 시스템
- **자동 로깅** - Controller/Service 메서드 실행 추적
- **성능 모니터링** - 느린 쿼리 자동 감지 (3초 임계값)
- **예외 추적** - 모든 예외 자동 로깅
- **실행 시간 측정** - 메서드별 성능 분석

### 🎯 통합 예외 처리
- **GlobalExceptionHandler** - 일관된 에러 응답
- **커스텀 예외 체계** - 도메인별 예외 클래스
- **ErrorCode 관리** - HTTP 상태 코드와 메시지 통합
- **Validation 지원** - 입력값 검증 자동 처리

### 📝 Logback 로깅
- **환경별 설정** - dev/prod 프로파일별 로그 레벨
- **파일 롤링** - 날짜/크기 기반 로그 파일 관리
- **구조화된 로그** - 일관된 패턴과 포맷
- **성능 최적화** - 비동기 로깅 지원

### 🐳 Docker Compose 지원
- **MySQL 8.0** - 즉시 사용 가능한 DB 환경
- **볼륨 관리** - 데이터 영속성 보장
- **개발 환경 통합** - Spring Boot Docker Compose 자동 시작

---

## 🛠 기술 스택

### Core
- **Java 17** - LTS 버전
- **Spring Boot 3.5.3** - 최신 안정 버전
- **Gradle 8.x** - 빌드 도구

### Data & Persistence
- **Spring Data JPA** - ORM 및 Repository 추상화
- **MySQL 8.0** - 관계형 데이터베이스
- **HikariCP** - 고성능 커넥션 풀

### Security
- **Spring Security 6.5.1** - 인증/인가 프레임워크
- **JJWT 0.11.5** - JWT 토큰 생성/검증

### Documentation
- **SpringDoc OpenAPI 2.3.0** - API 문서 자동 생성
- **Swagger UI** - 인터랙티브 API 테스트

### AOP & Logging
- **Spring AOP** - 관점 지향 프로그래밍
- **Logback** - 로깅 프레임워크
- **SLF4J** - 로깅 파사드

### Tools
- **Lombok** - 보일러플레이트 코드 제거
- **Validation** - Bean Validation 2.0
- **Docker Compose** - 컨테이너 오케스트레이션

---

## 📁 프로젝트 구조

```
src/main/java/com/example/collabo/
├── CollaboApplication.java          # 메인 애플리케이션
├── domain/                          # 도메인 레이어
│   ├── user/                        # 사용자 도메인
│   │   ├── UserEntity.java          # 엔티티
│   │   └── UserRepository.java      # Repository
│   └── board/                       # 게시판 도메인 (확장 가능)
└── global/                          # 글로벌 설정 및 공통 기능
    ├── aop/                         # AOP 관점
    │   ├── LoggingAspect.java       # 로깅 AOP
    │   ├── ExceptionLoggingAspect.java  # 예외 로깅
    │   ├── PerformanceAspect.java   # 성능 모니터링
    │   └── README.md                # AOP 사용 가이드
    ├── common/                      # 공통 응답 모델
    │   ├── CommonResponse.java      # 성공 응답
    │   └── CommonErrorResponse.java # 에러 응답
    ├── config/                      # 설정 클래스
    │   ├── JwtConfig.java           # JWT 설정
    │   ├── SecurityConfig.java      # Security 설정
    │   └── SwaggerConfig.java       # Swagger 설정
    ├── exception/                   # 예외 처리
    │   ├── CustomException.java     # 커스텀 예외 기본 클래스
    │   ├── ErrorCode.java           # 에러 코드 정의
    │   ├── GlobalExceptionHandler.java  # 글로벌 예외 핸들러
    │   └── specific/                # 구체적 예외 클래스
    │       ├── UnauthorizedException.java
    │       ├── ForbiddenException.java
    │       ├── ResourceNotFoundException.java
    │       └── InvalidInputException.java
    ├── filter/                      # 서블릿 필터
    │   └── JwtAuthFilter.java       # JWT 인증 필터
    ├── jwt/                         # JWT 유틸리티
    │   ├── JwtProvider.java         # JWT 토큰 생성/검증
    │   └── JwtSecretHashUtil.java   # Secret Key 관리
    └── security/                    # Spring Security
        └── CustomUserDetails.java   # 사용자 인증 정보

src/main/resources/
├── application.yml                  # 기본 설정
├── application-dev.yml              # 개발 환경 설정
└── logback-spring.xml              # Logback 설정
```

---

## 🚀 시작하기

### 사전 요구사항

- **Java 17** 이상
- **Docker & Docker Compose**
- **Gradle** (래퍼 포함)

### 1. 프로젝트 클론

```bash
cd /path/to/your/workspace
```

### 2. 데이터베이스 시작

```bash
docker-compose up -d
```

MySQL이 `localhost:3306`에서 실행됩니다.

### 3. 애플리케이션 빌드

```bash
./gradlew clean build
```

### 4. 애플리케이션 실행

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

또는 IntelliJ IDEA:
1. `Run` → `Edit Configurations`
2. `Active profiles`에 `dev` 입력
3. `Run` 실행

### 5. 확인

애플리케이션이 정상적으로 시작되면:

- **애플리케이션**: `http://localhost:8081`
- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **API Docs JSON**: `http://localhost:8081/v3/api-docs`

---

## 🎯 핵심 기능 상세

### 1️⃣ JWT 인증 시스템

#### 토큰 생성
```java
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    
    public String login(LoginRequest request) {
        // 사용자 인증 로직
        return jwtProvider.generateToken(user.getEmail());
    }
}
```

#### 토큰 검증 (자동)
- `JwtAuthFilter`가 모든 요청을 전처리
- Authorization 헤더에서 토큰 추출
- 토큰 유효성 검증 및 SecurityContext 설정

#### 보호된 엔드포인트 호출
```bash
curl -H "Authorization: Bearer {your-token}" \
     http://localhost:8081/api/users/me
```

### 2️⃣ AOP 자동 로깅

#### Controller 로깅
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        // LoggingAspect가 자동으로 로깅
        return ResponseEntity.ok(userService.findById(id));
    }
}
```

**로그 출력 예시:**
```
===== [GET] /api/users/1 - UserController.getUser 시작 =====
[Service] UserService.findById 호출
[Service] UserService.findById 완료 (15ms)
===== [GET] /api/users/1 - 완료 (23ms) =====
```

#### 성능 모니터링
```
[느린 쿼리 감지] UserService.findAll - 실행시간: 3245ms (임계값: 3000ms)
[느린 DB 쿼리] UserRepository.findAll - 실행시간: 1523ms
```

### 3️⃣ 예외 처리

#### 커스텀 예외 사용
```java
@Service
public class UserService {
    
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
```

#### 자동 응답 생성
```json
{
  "statusCode": 404,
  "error": "Not Found",
  "message": "유저를 찾을 수 없습니다.",
  "timestamp": "2025-11-18T16:30:21.187"
}
```

### 4️⃣ Swagger API 문서

#### 어노테이션 사용
```java
@Tag(name = "User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
```

---

## 📚 API 문서

### Swagger UI 접속
```
http://localhost:8081/swagger-ui.html
```

### JWT 인증 설정
1. Swagger UI 우측 상단 **Authorize** 버튼 클릭
2. JWT 토큰 입력 (Bearer 접두사 자동 추가)
3. **Authorize** 클릭
4. 모든 API 요청에 자동으로 토큰 포함

### API 테스트
- 각 엔드포인트를 펼쳐서 파라미터 입력
- **Try it out** 버튼 클릭
- **Execute**로 실제 요청 전송
- 응답 확인

---

## 💻 개발 가이드

### 새로운 도메인 추가

#### 1. Entity 생성
```java
@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity author;
}
```

#### 2. Repository 생성
```java
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByAuthor(UserEntity author);
}
```

#### 3. Service 생성
```java
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    
    public BoardEntity create(BoardCreateRequest request) {
        // 비즈니스 로직
        // AOP가 자동으로 로깅 및 성능 모니터링
        return boardRepository.save(board);
    }
}
```

#### 4. Controller 생성
```java
@Tag(name = "Board", description = "게시판 API")
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    
    @Operation(summary = "게시글 생성")
    @PostMapping
    public ResponseEntity<CommonResponse<BoardEntity>> create(
            @RequestBody BoardCreateRequest request) {
        // AOP가 자동으로 로깅
        BoardEntity board = boardService.create(request);
        return ResponseEntity.ok(CommonResponse.success(board));
    }
}
```

### 새로운 ErrorCode 추가

```java
// ErrorCode.java에 추가
public enum ErrorCode {
    // ...existing codes...
    
    // BOARD
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    BOARD_ACCESS_DENIED(HttpStatus.FORBIDDEN, "게시글에 접근할 권한이 없습니다."),
}
```

### 커스텀 예외 사용

```java
// 방법 1: CustomException 직접 사용
throw new CustomException(ErrorCode.BOARD_NOT_FOUND);

// 방법 2: 구체적인 예외 클래스 사용
throw new ResourceNotFoundException(ErrorCode.BOARD_NOT_FOUND);
```

---

## ⚙️ 설정 정보

### JWT 설정 (`application-dev.yml`)

```yaml
jwt:
  accessSecret: {your-base64-encoded-secret}
  accessTokenValidity: 86400000      # 24시간
  refreshTokenValidity: 604800000    # 7일
```

**안전한 Secret Key 생성:**
```bash
openssl rand -base64 64
```

### 데이터베이스 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/collabodb
    username: collabo
    password: collabo1234
  jpa:
    hibernate:
      ddl-auto: update    # 개발: update, 운영: validate
```

### 로깅 레벨 설정

**개발 환경 (application-dev.yml):**
```yaml
logging:
  level:
    com.example.collabo: DEBUG
    org.hibernate.SQL: DEBUG
```

**운영 환경 (application-prod.yml):**
```yaml
logging:
  level:
    com.example.collabo: INFO
    com.example.collabo.global.aop: WARN
```

### Swagger 비활성화 (운영)

```yaml
springdoc:
  swagger-ui:
    enabled: false
  api-docs:
    enabled: false
```

---

## 🔧 커스터마이징

### AOP 성능 임계값 조정

`PerformanceAspect.java`:
```java
private static final long SLOW_THRESHOLD_MS = 3000; // 3초 → 원하는 값
```

### SecurityConfig 커스터마이징

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/public/**").permitAll()  // 추가 public 경로
            .anyRequest().authenticated()
        )
        // ...
}
```

### 로그 파일 위치 변경

`logback-spring.xml`:
```xml
<file>logs/application.log</file>  <!-- 경로 변경 -->
```

---

## 🧪 테스트

### 단위 테스트
```bash
./gradlew test
```

### 통합 테스트
```bash
./gradlew integrationTest
```

### API 테스트
Swagger UI 또는 Postman 사용

---

## 📝 로그 확인

### 콘솔 로그
애플리케이션 실행 시 실시간 로그 출력

### 파일 로그
```bash
tail -f logs/application.log
```

### 로그 레벨별 확인
```bash
# ERROR 레벨만
grep "ERROR" logs/application.log

# 특정 클래스 로그
grep "UserService" logs/application.log
```

---

## 🚨 문제 해결

### 포트 충돌
```bash
# 8081 포트 사용 중인 프로세스 확인
lsof -i :8081

# 프로세스 종료
kill -9 {PID}
```

### MySQL 연결 실패
```bash
# Docker 컨테이너 상태 확인
docker-compose ps

# 컨테이너 재시작
docker-compose restart mysql
```

### JWT 토큰 오류
- `application-dev.yml`의 `jwt.accessSecret` 확인
- Base64로 인코딩된 256비트 이상 키 사용
- 토큰 만료 시간 확인

### Gradle 빌드 실패
```bash
# Gradle 캐시 정리
./gradlew clean

# 의존성 재다운로드
./gradlew build --refresh-dependencies
```

---

## 📦 배포

### JAR 빌드
```bash
./gradlew bootJar
```

생성 위치: `build/libs/collabo-0.0.1-SNAPSHOT.jar`

### 실행
```bash
java -jar build/libs/collabo-0.0.1-SNAPSHOT.jar \
     --spring.profiles.active=prod
```

### Docker 이미지 빌드 (옵션)
```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

## 📖 추가 문서

- [AOP 사용 가이드](src/main/java/com/example/collabo/global/aop/README.md)
- [Swagger 설정](docs/SWAGGER_SETUP.md)
- [구현 완료 내역](IMPLEMENTATION_COMPLETE.md)

---

## 🎯 로드맵

### 현재 구현 완료
- ✅ JWT 인증 시스템
- ✅ Swagger API 문서화
- ✅ AOP 로깅 시스템
- ✅ 통합 예외 처리
- ✅ Logback 로깅
- ✅ Docker Compose 지원

### 향후 계획
- ⏭️ Redis 캐싱
- ⏭️ 이메일 인증
- ⏭️ 파일 업로드
- ⏭️ 페이징/정렬
- ⏭️ QueryDSL 통합
- ⏭️ CI/CD 파이프라인

---

<div align="center">

**🚀 즉시 사용 가능한 Spring Boot 스타터 키트로 빠르게 개발을 시작하세요!**

Made with ❤️ by 

</div>

