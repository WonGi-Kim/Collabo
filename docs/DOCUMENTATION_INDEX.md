# 📚 Documentation Index

Spring Boot Monolithic App Starter Kit의 모든 문서 목록입니다.

---

## 🚀 시작하기

### [Quick Start Guide](QUICKSTART.md)
**5분 안에 프로젝트 시작하기**
- 3단계 설정
- 빠른 체크리스트
- 기본 테스트 방법

### [🔄 새 프로젝트 설정](guides/NEW_PROJECT_SETUP.md)
**이 스타터 키트로 새 프로젝트 시작하기**
- 프로젝트 이름 변경
- 패키지명 변경
- 데이터베이스 설정
- JWT Secret Key 재생성
- 빠른 변경 스크립트

### [README](../README.md)
**전체 프로젝트 개요**
- 주요 기능 소개
- 기술 스택
- 프로젝트 구조
- 개발 가이드

---

## 🏗️ 아키텍처

### [Architecture & Tech Stack](ARCHITECTURE.md)
**시스템 아키텍처 및 기술 스택 상세**
- 시스템 아키텍처 다이어그램
- AOP 구조
- JWT 인증 흐름
- 예외 처리 흐름
- 요청 처리 라이프사이클

---

## 🔧 기능별 가이드

### [AOP 사용 가이드](../src/main/java/com/example/collabo/global/aop/README.md)
**AOP 로깅 및 모니터링**
- LoggingAspect
- ExceptionLoggingAspect
- PerformanceAspect
- 커스터마이징 방법

### [Swagger 설정](SWAGGER_SETUP.md)
**API 문서화**
- Swagger UI 사용법
- JWT 인증 설정
- API 테스트 방법
- 어노테이션 가이드

### [구현 완료 내역](../IMPLEMENTATION_COMPLETE.md)
**전체 구현 체크리스트**
- AOP 구조
- 예외 처리
- 로깅 설정
- 사용 예시

---

## 📖 주제별 가이드

### 인증 & 보안
- JWT 토큰 생성/검증 → [README - JWT 인증 시스템](../README.md#1️⃣-jwt-인증-시스템)
- Spring Security 설정 → [ARCHITECTURE - 보안 고려사항](ARCHITECTURE.md#-보안-고려사항)

### 로깅 & 모니터링
- AOP 자동 로깅 → [AOP README](../src/main/java/com/example/collabo/global/aop/README.md)
- 성능 모니터링 → [README - AOP 자동 로깅](../README.md#2️⃣-aop-자동-로깅)
- Logback 설정 → [README - Logback 로깅](../README.md#-logback-로깅)

### 예외 처리
- 커스텀 예외 → [README - 예외 처리](../README.md#3️⃣-예외-처리)
- ErrorCode 관리 → [개발 가이드](../README.md#새로운-errorcode-추가)
- GlobalExceptionHandler → [ARCHITECTURE - 예외 처리 흐름](ARCHITECTURE.md#-예외-처리-흐름)

### API 문서
- Swagger UI → [SWAGGER_SETUP](SWAGGER_SETUP.md)
- API 테스트 → [README - API 문서](../README.md#-api-문서)

### 데이터베이스
- JPA 설정 → [README - 설정 정보](../README.md#-설정-정보)
- Docker Compose → [QUICKSTART](QUICKSTART.md)

---

## 🛠️ 개발 참고

### 새로운 기능 추가
1. [도메인 추가 가이드](../README.md#새로운-도메인-추가)
2. [ErrorCode 추가](../README.md#새로운-errorcode-추가)
3. [커스텀 예외 사용](../README.md#커스텀-예외-사용)

### 설정 커스터마이징
1. [JWT 설정](../README.md#jwt-설정-application-devyml)
2. [데이터베이스 설정](../README.md#데이터베이스-설정)
3. [로깅 레벨 설정](../README.md#로깅-레벨-설정)
4. [Swagger 비활성화](../README.md#swagger-비활성화-운영)

---

## 🐛 문제 해결

### 일반적인 문제
- [포트 충돌](../README.md#포트-충돌)
- [MySQL 연결 실패](../README.md#mysql-연결-실패)
- [JWT 토큰 오류](../README.md#jwt-토큰-오류)
- [Gradle 빌드 실패](../README.md#gradle-빌드-실패)

### 빠른 해결
- [QUICKSTART - 문제 해결](QUICKSTART.md#-문제-해결)

---

## 📂 프로젝트 구조

```
Spring-Boot-Starter-Kit/
├── README.md                    # 📘 메인 문서
├── QUICKSTART.md               # ⚡ 빠른 시작 가이드
├── ARCHITECTURE.md             # 🏗️ 아키텍처 문서
├── SWAGGER_SETUP.md            # 📚 Swagger 설정
├── IMPLEMENTATION_COMPLETE.md  # ✅ 구현 완료 내역
├── DOCUMENTATION_INDEX.md      # 📑 이 문서
│
├── src/main/java/com/example/collabo/
│   ├── domain/                 # 도메인 레이어
│   └── global/                 # 글로벌 설정
│       └── aop/
│           └── README.md       # 🎯 AOP 가이드
│
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   └── logback-spring.xml
│
├── build.gradle
├── compose.yaml
└── logs/
```

---

## 🎓 학습 순서 추천

### 초보자
1. [QUICKSTART](QUICKSTART.md) - 일단 실행해보기
2. [README](../README.md) - 전체 기능 이해하기
3. [Swagger UI](http://localhost:8081/swagger-ui.html) - API 테스트해보기
4. [도메인 추가 가이드](../README.md#새로운-도메인-추가) - 직접 만들어보기

### 중급자
1. [ARCHITECTURE](ARCHITECTURE.md) - 아키텍처 이해하기
2. [AOP README](../src/main/java/com/example/collabo/global/aop/README.md) - AOP 동작 원리
3. [JWT 인증 흐름](ARCHITECTURE.md#-jwt-인증-흐름) - 보안 메커니즘
4. 커스터마이징 - 프로젝트에 맞게 수정하기

### 고급자
1. 전체 코드 리뷰
2. 성능 최적화 적용
3. 추가 기능 구현 (Redis, 파일 업로드 등)
4. CI/CD 파이프라인 구축

---

## 🔗 외부 링크

### 공식 문서
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [SpringDoc OpenAPI](https://springdoc.org/)

### 튜토리얼
- [JWT Introduction](https://jwt.io/introduction)
- [AOP Concepts](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)

---

## 🎯 빠른 참조

| 필요한 정보 | 문서 |
|------------|------|
| 프로젝트 시작 | [QUICKSTART](QUICKSTART.md) |
| 전체 기능 소개 | [README](../README.md) |
| 아키텍처 이해 | [ARCHITECTURE](ARCHITECTURE.md) |
| AOP 사용법 | [AOP README](../src/main/java/com/example/collabo/global/aop/README.md) |
| Swagger 설정 | [SWAGGER_SETUP](SWAGGER_SETUP.md) |
| 구현 체크리스트 | [IMPLEMENTATION_COMPLETE](../IMPLEMENTATION_COMPLETE.md) |
| 문제 해결 | [README - 문제 해결](../README.md#-문제-해결) |

---

<div align="center">

**📚 모든 문서를 읽고 나면 즉시 개발을 시작할 수 있습니다!**

[메인 README로 돌아가기](../README.md)

</div>

