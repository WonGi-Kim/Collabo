# 🎯 AOP & 예외 처리 구조 완성

## 📁 최종 구조

```
global/
├── aop/
│   ├── LoggingAspect.java              ✅ 컨트롤러/서비스 로깅
│   ├── ExceptionLoggingAspect.java     ✅ 예외 로깅
│   ├── PerformanceAspect.java          ✅ 성능 모니터링
│   └── README.md                       ✅ 사용 가이드
├── common/
│   ├── CommonResponse.java
│   └── CommonErrorResponse.java
├── config/
│   ├── JwtConfig.java
│   └── SecurityConfig.java
├── exception/
│   ├── CustomException.java            ✅ 기본 예외 클래스
│   ├── ErrorCode.java                  ✅ 에러 코드 정의
│   ├── GlobalExceptionHandler.java     ✅ 글로벌 예외 처리
│   └── specific/                       ✅ 구체적 예외 클래스
│       ├── UnauthorizedException.java
│       ├── ForbiddenException.java
│       ├── ResourceNotFoundException.java
│       └── InvalidInputException.java
├── filter/
│   └── JwtAuthFilter.java
├── jwt/
│   ├── JwtProvider.java
│   └── JwtSecretHashUtil.java
└── security/
    └── CustomUserDetails.java
```

## 🚀 구현 완료 항목

### 1. AOP 패키지 (3개 Aspect)

#### LoggingAspect
- ✅ Controller 메서드 로깅 (HTTP 메서드, URI, 실행 시간)
- ✅ Service 메서드 로깅 (메서드명, 실행 시간)
- ✅ 성공/실패 로깅

#### ExceptionLoggingAspect
- ✅ 도메인 전체 예외 로깅
- ✅ 예외 타입, 메시지, 파라미터 기록
- ✅ DEBUG 레벨에서 스택 트레이스 출력

#### PerformanceAspect
- ✅ Service 메서드 실행 시간 모니터링
- ✅ Repository 쿼리 성능 추적
- ✅ 느린 쿼리 감지 및 경고 (3초/1초 임계값)

### 2. 예외 처리 강화

#### GlobalExceptionHandler
- ✅ CustomException 처리
- ✅ MethodArgumentNotValidException (입력값 검증)
- ✅ HttpRequestMethodNotSupportedException
- ✅ NoResourceFoundException
- ✅ Exception (일반 예외)

#### 구체적 예외 클래스
- ✅ UnauthorizedException (인증 실패)
- ✅ ForbiddenException (권한 없음)
- ✅ ResourceNotFoundException (리소스 없음)
- ✅ InvalidInputException (잘못된 입력)

### 3. 설정 파일

#### application-dev.yml
- ✅ AOP 로깅 레벨 설정
- ✅ Spring AOP 디버그 활성화
- ✅ 패키지별 로깅 레벨 구분

## 📝 사용 예시

### Controller 예시
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserEntity>> getUser(@PathVariable Long id) {
        // LoggingAspect가 자동으로 로깅
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(CommonResponse.success(user));
    }
}
```

**로그 출력**:
```
===== [GET] /api/users/1 - UserController.getUser 시작 =====
[Service] UserService.findById 호출
[Service] UserService.findById 완료 (15ms)
===== [GET] /api/users/1 - 완료 (23ms) =====
```

### Service 예시
```java
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserEntity findById(Long id) {
        // PerformanceAspect가 성능 모니터링
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
            // ExceptionLoggingAspect가 예외 로깅
            // GlobalExceptionHandler가 응답 생성
    }
}
```

**예외 발생 시 로그**:
```
[예외 발생] UserService.findById - 타입: ResourceNotFoundException, 메시지: 유저를 찾을 수 없습니다.
[예외 파라미터] [1]
CustomException: 유저를 찾을 수 없습니다.
```

**느린 쿼리 감지 시**:
```
[느린 쿼리 감지] UserService.findAll - 실행시간: 3245ms (임계값: 3000ms)
```

## 🎨 커스터마이징 가이드

### 1. 성능 임계값 조정
`PerformanceAspect.java`:
```java
private static final long SLOW_THRESHOLD_MS = 3000; // 원하는 값으로 변경
```

### 2. 새로운 ErrorCode 추가
`ErrorCode.java`:
```java
// BOARD
BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
BOARD_ACCESS_DENIED(HttpStatus.FORBIDDEN, "게시글에 접근할 권한이 없습니다."),
```

### 3. 새로운 예외 클래스 추가
```java
public class BoardNotFoundException extends CustomException {
    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
```

### 4. 로그 레벨 조정

**개발 환경** (`application-dev.yml`):
```yaml
logging:
  level:
    com.example.collabo: DEBUG
    com.example.collabo.global.aop: DEBUG
```

**운영 환경** (`application-prod.yml`):
```yaml
logging:
  level:
    com.example.collabo: INFO
    com.example.collabo.global.aop: WARN
```

## ⚡ 실행 방법

### 1. Gradle 빌드
```bash
./gradlew clean build
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

또는 IntelliJ:
1. **Run** → **Edit Configurations**
2. **Active profiles**: `dev` 입력
3. **Run**

## 📊 예상 로그 흐름

```
# 1. 애플리케이션 시작
2025-11-18 16:30:20.369 [main] INFO  c.example.collabo.CollaboApplication - The following 1 profile is active: "dev"

# 2. HTTP 요청
===== [GET] /api/users/1 - UserController.getUser 시작 =====

# 3. Service 호출
[Service] UserService.findById 호출

# 4. 성능 측정
[Service] UserService.findById 완료 (15ms)

# 5. 응답 완료
===== [GET] /api/users/1 - 완료 (23ms) =====

# 6. 예외 발생 시
[예외 발생] UserService.findById - 타입: ResourceNotFoundException, 메시지: 유저를 찾을 수 없습니다.
CustomException: 유저를 찾을 수 없습니다.
```

## ✅ 체크리스트

- [x] AOP 의존성 추가 (build.gradle)
- [x] LoggingAspect 구현
- [x] ExceptionLoggingAspect 구현
- [x] PerformanceAspect 구현
- [x] 구체적 예외 클래스 4개 생성
- [x] GlobalExceptionHandler 강화
- [x] application-dev.yml 로깅 설정
- [x] README 문서 작성
- [ ] 실제 Controller/Service 구현 (다음 단계)
- [ ] 테스트 및 로그 확인

## 🎯 다음 단계

1. **도메인 구현**
   - User 도메인: UserController, UserService 구현
   - Board 도메인: BoardController, BoardService 구현

2. **테스트**
   - 각 Aspect가 올바르게 동작하는지 확인
   - 예외 처리가 제대로 되는지 확인

3. **로그 튜닝**
   - 불필요한 로그 제거
   - 성능 임계값 조정

---

**구현 완료!** 🎉

모든 AOP 및 예외 처리 구조가 준비되었습니다.
이제 실제 비즈니스 로직을 구현하면 자동으로 로깅과 예외 처리가 적용됩니다.

