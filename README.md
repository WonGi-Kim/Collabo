# 📘 1개월 성장 로드맵 | Spring Boot 백엔드 개발자용

> **목표**  
> 단순 구현에서 벗어나, 설계 · 운영 · 개선까지 고민하는 성장형 개발자로 도약하기

---

## 📅 Week 1 – 현재 구조에 문제의식 갖기

**✅ 목표**  
기존 구조를 시각화하고, 개선할 수 있는 지점을 찾기

**🔧 할 일**
- [ ] 현재 프로젝트 구조 간단히 다이어그램으로 정리
- [ ] 의존성 흐름 및 순환참조 여부 파악
- [ ] `@Transactional` 사용 위치 검토
- [ ] 클래스 크기 및 역할 분석 (비대한 service 등)

**📚 참고 리소스**
- 블로그: 서비스 책임 분리
- 인프런: 김영한 – 도메인 설계 기초

---

## 📅 Week 2 – 설계 정제 및 아키텍처 구조화

**✅ 목표**  
도메인 중심 설계 & 테스트 가능한 구조로 개선

**🔧 할 일**
- [ ] 패키지 구조를 도메인 중심으로 리팩토링
- [ ] 서비스 로직을 UseCase 단위로 분리
- [ ] DTO와 Entity 간 역할 명확화
- [ ] 클린 아키텍처 실험 (의존성 방향 전환)

**📚 참고 리소스**
- 『객체지향의 사실과 오해』
- YouTube: [오늘코딩 – 클린 아키텍처 개념 강의](https://www.youtube.com/watch?v=lTobdC1yU8c)

---

## 📅 Week 3 – 운영 환경 감각 키우기

**✅ 목표**  
내 서비스가 "돌아가는 중"이라는 관점 갖기

**🔧 할 일**
- [ ] Spring Boot Actuator 적용 (`/health`, `/metrics`)
- [ ] Logback + MDC로 trace id 추적 로깅 구현
- [ ] GitHub Actions CI 파이프라인 구성
- [ ] 서버 배포 자동화 스크립트 작성

**📚 참고 리소스**
- 블로그: Actuator 활용
- 인프런: GitHub Actions 기본

---

## 📅 Week 4 – 정리와 확장

**✅ 목표**  
구현한 내용을 문서화하고, 개선 실험 진행

**🔧 할 일**
- [ ] 프로젝트 구조 및 결정 이유 정리 (Notion 또는 GitHub Wiki)
- [ ] 기능 일부를 multi-module 구조로 분리 실험
- [ ] 서비스 단위 테스트 작성
- [ ] 성능 테스트 시나리오 작성 (Postman 또는 JMeter 활용)

**📚 참고 리소스**
- GitHub 예제: [멀티 모듈 실습](https://github.com/gurumee92/springboot-multimodule)
- 블로그: 김영한님의 JUnit 테스트 정리

---

## ✅ 최종 체크리스트

| 항목 | 체크 |
| --- | --- |
| 현재 구조를 시각화하고 설명할 수 있다 | ☐ |
| 도메인 중심 설계와 책임 분리를 실험했다 | ☐ |
| CI/CD 및 운영 환경을 다뤄봤다 | ☐ |
| 단위 테스트 및 성능 테스트를 진행했다 | ☐ |
| 아키텍처와 기술 선택의 이유를 문서화했다 | ☐ |

---

## 💡 실전 프로젝트 아이디어: **팀 업무 협업 툴 (Mini SaaS)**

간단한 기능 중심의 미니 SaaS 프로젝트로, 로드맵 학습 적용

### 🔨 주요 기능
- 팀 생성 / 팀원 초대
- 업무(Task) 등록 및 상태 관리 (To Do → In Progress → Done)
- 댓글 기능, 파일 첨부
- 작업 로그 기록

### 🚀 기술 포인트 적용

| 로드맵 내용 | 적용 예시 |
| --- | --- |
| 도메인 중심 구조 | `Team`, `Member`, `Task`, `Comment`, `Attachment` 등 |
| 아키텍처 개선 | `TaskUseCase`, `CommentService` 등으로 책임 분리 |
| 운영 감각 | Actuator, trace log, health check |
| 성능 테스트 | 댓글 1만 개 등록 시뮬레이션 |
| 테스트 작성 | 업무 완료 API 단위 테스트 |

> ⚠️ 프론트엔드는 Swagger UI로 대체 가능  
> ⚠️ 파일 업로드는 로컬 디렉토리 사용

---

## 📦 디렉터리 구조 예시

```plaintext
📁 team-collab-app
├── 📁 app-api             # API 진입점 (Spring Boot Starter)
│   ├── TeamCollabAppApplication.java
│   ├── 📁 config
│   ├── 📁 controller
│   ├── 📁 dto
│   ├── 📁 exception
│   └── 📁 security
├── 📁 domain-core         # 비즈니스 로직 계층
│   ├── 📁 task
│   │   ├── entity
│   │   ├── repository
│   │   └── service
│   ├── 📁 team
│   ├── 📁 member
│   └── 📁 comment
├── 📁 infra               # 외부 연동 (파일 저장, 로그 시스템 등)
│   ├── 📁 file
│   └── 📁 monitoring
├── 📁 common              # 공통 유틸, 예외, 로깅 설정 등
├── 📁 scripts             # 배포 스크립트, CI/CD 관련
└── build.gradle
