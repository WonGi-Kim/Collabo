# 🏗️ Architecture & Tech Stack

## 📐 시스템 아키텍처

```
┌─────────────────────────────────────────────────────────────┐
│                         Client                               │
│                    (Browser/Mobile)                          │
└─────────────────────┬───────────────────────────────────────┘
                      │ HTTP/HTTPS
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              RestController                           │  │
│  │  - JWT Auth Filter (인증)                            │  │
│  │  - Swagger UI (API 문서)                             │  │
│  │  - ExceptionHandler (예외 처리)                      │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                   Business Layer                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │                  Service                              │  │
│  │  - Business Logic                                     │  │
│  │  - Transaction Management                             │  │
│  │  - AOP Logging (자동 로깅)                           │  │
│  │  - Performance Monitoring (성능 측정)                │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                  Persistence Layer                           │
│  ┌──────────────────────────────────────────────────────┐  │
│  │            Repository (JPA)                           │  │
│  │  - CRUD Operations                                    │  │
│  │  - Custom Queries                                     │  │
│  │  - Entity Management                                  │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                     Data Layer                               │
│                  MySQL Database                              │
│              (Docker Container)                              │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 AOP 구조

```
┌─────────────────────────────────────────────────────────────┐
│                    Cross-cutting Concerns                    │
│                         (AOP Layer)                          │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌───────────────┐  ┌──────────────────┐  ┌─────────────┐ │
│  │   Logging     │  │   Performance    │  │  Exception  │ │
│  │    Aspect     │  │    Monitoring    │  │   Logging   │ │
│  └───────┬───────┘  └────────┬─────────┘  └──────┬──────┘ │
│          │                    │                    │         │
│          └────────────────────┼────────────────────┘         │
│                               ▼                               │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              Pointcut (Join Point)                    │  │
│  │  - Controller Methods: @RestController               │  │
│  │  - Service Methods: @Service                         │  │
│  │  - Repository Methods: @Repository                   │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                      Target Object                           │
│                 (Business Logic Layer)                       │
│   Controller → Service → Repository → Database              │
└─────────────────────────────────────────────────────────────┘
```

### AOP 실행 흐름

1. **요청 진입** → `LoggingAspect` (메서드 시작 로깅)
2. **비즈니스 로직 실행** → `PerformanceAspect` (시간 측정 시작)
3. **예외 발생 시** → `ExceptionLoggingAspect` (예외 로깅)
4. **응답 반환** → `LoggingAspect` (메서드 종료 로깅 + 실행 시간)

---

## 🔐 JWT 인증 흐름

```
┌──────────┐                                    ┌──────────┐
│  Client  │                                    │  Server  │
└─────┬────┘                                    └────┬─────┘
      │                                              │
      │  1. POST /api/auth/login                    │
      │     (email, password)                        │
      ├─────────────────────────────────────────────>│
      │                                              │
      │                                              │ 2. 사용자 인증
      │                                              │    (DB 조회)
      │                                              │
      │  3. JWT Token                                │
      │<─────────────────────────────────────────────┤
      │                                              │
      │                                              │
      │  4. GET /api/users/me                        │
      │     Authorization: Bearer {token}            │
      ├─────────────────────────────────────────────>│
      │                                              │
      │                                              │ 5. JwtAuthFilter
      │                                              │    - 토큰 추출
      │                                              │    - 토큰 검증
      │                                              │    - SecurityContext 설정
      │                                              │
      │                                              │ 6. Controller 실행
      │                                              │
      │  7. User Data                                │
      │<─────────────────────────────────────────────┤
      │                                              │
```

### JWT 구조

```
Header.Payload.Signature

Header:
{
  "alg": "HS256",
  "typ": "JWT"
}

Payload:
{
  "sub": "user@example.com",
  "iat": 1700000000,
  "exp": 1700086400
}

Signature:
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret
)
```

---

## 📊 예외 처리 흐름

```
┌────────────┐
│ Controller │
└─────┬──────┘
      │
      │ throws CustomException
      ▼
┌─────────────────────┐
│ Service/Repository  │
└──────┬──────────────┘
       │
       │ Exception bubbles up
       ▼
┌──────────────────────────────┐
│   ExceptionLoggingAspect     │
│   - 예외 로깅                 │
│   - 파라미터 기록             │
└──────┬───────────────────────┘
       │
       │ Exception continues
       ▼
┌──────────────────────────────┐
│  GlobalExceptionHandler      │
│  (@RestControllerAdvice)     │
├──────────────────────────────┤
│  @ExceptionHandler           │
│  - CustomException           │
│  - ValidationException       │
│  - Exception (fallback)      │
└──────┬───────────────────────┘
       │
       │ Error Response
       ▼
┌──────────────────────────────┐
│   CommonErrorResponse        │
│   {                          │
│     "statusCode": 404,       │
│     "error": "Not Found",    │
│     "message": "...",        │
│     "timestamp": "..."       │
│   }                          │
└──────────────────────────────┘
       │
       ▼
┌────────────┐
│   Client   │
└────────────┘
```

---

## 🛠️ 기술 스택 상세

### Backend Framework
```
Spring Boot 3.5.3
├── Spring Web (MVC)
├── Spring Security 6.5.1
├── Spring Data JPA
├── Spring AOP
└── Spring Validation
```

### Security & Authentication
```
JWT (JJWT 0.11.5)
├── Token Generation
├── Token Validation
├── Claims Management
└── Signature Verification
```

### Database
```
MySQL 8.0
├── InnoDB Engine
├── UTF-8 Character Set
├── Timezone: Asia/Seoul
└── HikariCP Connection Pool
```

### API Documentation
```
SpringDoc OpenAPI 2.3.0
├── Swagger UI
├── OpenAPI 3.0 Spec
├── JWT Auth Integration
└── Interactive Testing
```

### Logging
```
Logback + SLF4J
├── Console Appender
├── Rolling File Appender
├── Pattern Layout
└── Async Logging
```

### Build & Dependency
```
Gradle 8.x
├── Dependency Management
├── Multi-module Support
├── Test Automation
└── Build Optimization
```

### DevOps
```
Docker Compose
├── MySQL Container
├── Volume Management
├── Network Configuration
└── Auto-start Support
```

---

## 📦 패키지 구조 설계 원칙

### Domain-Driven Design (DDD)
```
domain/
├── user/           # User Bounded Context
│   ├── UserEntity.java
│   ├── UserRepository.java
│   ├── UserService.java
│   └── UserController.java
└── board/          # Board Bounded Context
    ├── BoardEntity.java
    ├── BoardRepository.java
    ├── BoardService.java
    └── BoardController.java
```

### Cross-cutting Concerns (Global)
```
global/
├── aop/            # Aspect-Oriented Programming
├── common/         # Common Response Models
├── config/         # Configuration Classes
├── exception/      # Exception Handling
├── filter/         # Servlet Filters
├── jwt/            # JWT Utilities
└── security/       # Security Components
```

---

## 🔄 요청 처리 라이프사이클

```
1. HTTP Request
   ↓
2. JwtAuthFilter (토큰 검증)
   ↓
3. Spring Security (인가 확인)
   ↓
4. DispatcherServlet
   ↓
5. @RestController
   ├─ LoggingAspect (Before)
   ├─ Validation
   └─ Method Execution
   ↓
6. @Service
   ├─ PerformanceAspect (Around)
   ├─ @Transactional
   └─ Business Logic
   ↓
7. @Repository (JPA)
   ├─ Query Execution
   └─ Entity Management
   ↓
8. Database (MySQL)
   ↓
9. Response
   ├─ LoggingAspect (After)
   ├─ CommonResponse Wrapping
   └─ JSON Serialization
   ↓
10. HTTP Response
```

---

## 🎨 설정 파일 계층

```
application.yml (기본)
├── spring.profiles.active: dev
└── 공통 설정

application-dev.yml (개발)
├── server.port: 8081
├── logging.level: DEBUG
├── spring.jpa.show-sql: true
└── swagger: enabled

application-prod.yml (운영)
├── server.port: 8080
├── logging.level: INFO
├── spring.jpa.show-sql: false
└── swagger: disabled
```

---

## 📈 성능 최적화

### 1. Connection Pool (HikariCP)
```yaml
hikari:
  maximum-pool-size: 10
  minimum-idle: 5
  connection-timeout: 30000
```

### 2. JPA 설정
```yaml
jpa:
  open-in-view: false        # OSIV 비활성화
  properties:
    hibernate:
      batch_size: 100         # Batch Insert
```

### 3. AOP 성능
- **Pointcut 최적화**: 필요한 패키지만 타겟팅
- **로깅 레벨**: 운영 환경에서는 INFO 이상만

### 4. 캐싱 전략 (향후 추가)
- Redis 통합 예정
- Spring Cache Abstraction

---

## 🔒 보안 고려사항

### 1. JWT 보안
- ✅ HMAC SHA-256 서명
- ✅ 토큰 만료 시간 설정
- ✅ Refresh Token 지원
- ⏭️ Token Blacklist (Redis)

### 2. Password 보안
- ✅ BCrypt 해싱
- ✅ Salt 자동 생성
- ⏭️ 비밀번호 정책 강화

### 3. API 보안
- ✅ CSRF 비활성화 (JWT 사용)
- ✅ CORS 설정
- ⏭️ Rate Limiting
- ⏭️ API Key 관리

### 4. Database 보안
- ✅ 환경 변수 사용 권장
- ✅ SSL/TLS 연결 (운영)
- ⏭️ Read-only Replica

---

## 📚 참고 자료

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT.io](https://jwt.io/)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [Logback Manual](https://logback.qos.ch/manual/)

---

<div align="center">

**🏗️ 확장 가능하고 안전한 아키텍처로 설계된 스타터 키트**

[README로 돌아가기](../README.md)

</div>

