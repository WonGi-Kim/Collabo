# 📚 Swagger 설정 완료

## ✅ 구현 완료 항목

### 1. 의존성 추가
- `springdoc-openapi-starter-webmvc-ui:2.3.0`

### 2. SwaggerConfig 생성
- JWT Bearer 토큰 인증 설정
- API 문서 정보 설정
- 개발 서버 URL 설정

### 3. SecurityConfig 수정
- Swagger UI 경로 허용 (`/swagger-ui/**`, `/v3/api-docs/**`)
- CSRF 설정 최신 방식으로 변경

### 4. application-dev.yml 설정
- Swagger UI 경로 및 표시 옵션 설정

## 🚀 사용 방법

### 1. Gradle 빌드 및 실행

```bash
# 의존성 다운로드 및 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 2. Swagger UI 접속

애플리케이션 실행 후 브라우저에서:

```
http://localhost:8081/swagger-ui.html
```

### 3. JWT 토큰 인증 설정

1. Swagger UI 우측 상단의 **Authorize** 버튼 클릭
2. JWT 토큰 입력 (Bearer 접두사 자동 추가됨)
3. **Authorize** 클릭
4. 이후 모든 API 요청에 자동으로 토큰 포함

## 📝 Controller 예시

Swagger 어노테이션을 사용하여 API 문서를 작성할 수 있습니다:

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 관리 API")
public class UserController {
    
    private final UserService userService;
    
    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserEntity>> getUser(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable Long id) {
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(CommonResponse.success(user));
    }
    
    @Operation(summary = "사용자 목록 조회", description = "모든 사용자를 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<List<UserEntity>>> getAllUsers() {
        List<UserEntity> users = userService.findAll();
        return ResponseEntity.ok(CommonResponse.success(users));
    }
}
```

## 🎨 주요 Swagger 어노테이션

### 클래스 레벨
- `@Tag(name, description)` - API 그룹 정의

### 메서드 레벨
- `@Operation(summary, description)` - API 설명
- `@ApiResponses` - 응답 코드별 설명

### 파라미터 레벨
- `@Parameter(description, required)` - 파라미터 설명
- `@RequestBody(description)` - 요청 바디 설명

## ⚙️ 설정 옵션

### application-dev.yml에서 커스터마이징 가능:

```yaml
springdoc:
  api-docs:
    path: /v3/api-docs              # API 문서 JSON 경로
  swagger-ui:
    path: /swagger-ui.html          # Swagger UI 경로
    tags-sorter: alpha              # 태그 정렬 방식
    operations-sorter: alpha        # 메서드 정렬 방식
    display-request-duration: true  # 요청 시간 표시
    doc-expansion: none             # 기본 확장 상태
```

## 📌 접근 가능한 URL

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:8081/v3/api-docs.yaml`

## 🔒 보안 설정

Swagger UI는 개발 환경에서만 활성화하는 것이 좋습니다.

### application-prod.yml (운영 환경)

```yaml
springdoc:
  swagger-ui:
    enabled: false  # 운영 환경에서는 비활성화
  api-docs:
    enabled: false
```

또는 SecurityConfig에서:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            // 개발 환경에서만 Swagger 허용
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                .access((authentication, context) -> 
                    new AuthorizationDecision(Arrays.asList(environment.getActiveProfiles()).contains("dev")))
            .anyRequest().authenticated()
        )
        // ...
}
```
---

**Swagger 설정 완료!** 🎉

이제 Controller를 구현하면 자동으로 API 문서가 생성됩니다.
`./gradlew build` 후 애플리케이션을 실행하고 `http://localhost:8081/swagger-ui.html`에 접속하세요.

