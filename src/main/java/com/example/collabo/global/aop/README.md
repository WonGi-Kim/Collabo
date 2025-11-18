# AOP 구현 가이드

## 📁 구조

```
global/
└── aop/
    ├── LoggingAspect.java           # 컨트롤러/서비스 로깅
    ├── ExceptionLoggingAspect.java  # 예외 로깅
    └── PerformanceAspect.java       # 성능 모니터링
```

## 🎯 각 Aspect 설명

### 1. LoggingAspect
- **대상**: Controller, Service 클래스
- **기능**: 
  - HTTP 요청 정보 로깅 (메서드, URI)
  - 실행 시간 측정
  - 성공/실패 로깅
  
**로그 예시**:
```
===== [GET] /api/users - UserController.getUser 시작 =====
[Service] UserService.findById 호출
[Service] UserService.findById 완료 (15ms)
===== [GET] /api/users - 완료 (23ms) =====
```

### 2. ExceptionLoggingAspect
- **대상**: domain 패키지 전체
- **기능**:
  - 예외 발생 시 상세 로깅
  - 예외 타입, 메시지, 파라미터 기록
  - DEBUG 레벨에서 스택 트레이스 출력

**로그 예시**:
```
[예외 발생] UserService.findById - 타입: ResourceNotFoundException, 메시지: 사용자를 찾을 수 없습니다
[예외 파라미터] [1]
```

### 3. PerformanceAspect
- **대상**: Service, Repository 클래스
- **기능**:
  - 실행 시간 모니터링
  - 느린 쿼리 감지 (3초 이상)
  - DB 쿼리 성능 추적 (1초 이상)

**로그 예시**:
```
[느린 쿼리 감지] UserService.findAll - 실행시간: 3245ms (임계값: 3000ms)
[느린 DB 쿼리] UserRepository.findAll - 실행시간: 1523ms
```

## ⚙️ 설정

### build.gradle
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-aop'
}
```

### application-dev.yml
```yaml
logging:
  level:
    com.example.collabo: DEBUG
    com.example.collabo.global.aop: DEBUG
    org.springframework.aop: DEBUG
```

## 🚀 사용 방법

### 1. Controller 생성 (자동 AOP 적용)
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        // LoggingAspect가 자동으로 로깅
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(new UserResponse(user));
    }
}
```

### 2. Service 생성 (자동 AOP 적용)
```java
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserEntity findById(Long id) {
        // LoggingAspect + PerformanceAspect가 자동으로 적용
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        // ExceptionLoggingAspect가 예외 로깅
    }
}
```

## 📊 성능 임계값 조정

`PerformanceAspect.java`에서 임계값 수정 가능:

```java
private static final long SLOW_THRESHOLD_MS = 3000; // 3초 → 원하는 값으로 변경
```

## 🔍 로그 레벨 조정

### 프로덕션 환경 (application-prod.yml)
```yaml
logging:
  level:
    com.example.collabo: INFO              # 일반 로그
    com.example.collabo.global.aop: WARN   # 경고 이상만
```

### 개발 환경 (application-dev.yml)
```yaml
logging:
  level:
    com.example.collabo: DEBUG             # 상세 로그
    com.example.collabo.global.aop: DEBUG  # AOP 상세 로그
```

## ✅ 빌드 및 실행

```bash
# 빌드
./gradlew clean build

# 실행
./gradlew bootRun --args='--spring.profiles.active=dev'

# 또는 IntelliJ에서
# Run Configuration > Active profiles: dev
```

## 📝 주의사항

1. **@Component, @Service, @Repository 어노테이션 필수**: AOP는 Spring Bean에만 적용됩니다
2. **Pointcut 패턴 확인**: 도메인 패키지 구조에 맞게 작성되었는지 확인
3. **성능 영향 최소화**: 로그 레벨을 적절히 조정하세요
4. **예외 처리**: ExceptionLoggingAspect는 로깅만 수행하며, GlobalExceptionHandler가 실제 응답 처리

## 🎨 커스터마이징

### 새로운 Aspect 추가 예시

```java
@Slf4j
@Aspect
@Component
public class SecurityAspect {
    
    @Before("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void logSecurityAccess(JoinPoint joinPoint) {
        log.info("[보안] 권한 체크: {}", joinPoint.getSignature().toShortString());
    }
}
```

---

## 📌 다음 단계

1. ✅ AOP 구조 구현 완료
2. ⏭️ 도메인별 예외 클래스 추가 (exception/specific/)
3. ⏭️ Controller, Service 구현
4. ⏭️ 실제 로그 확인 및 튜닝

