# ✅ 새 프로젝트 설정 완료

## 📁 생성된 파일

### 📚 문서
- ✅ `docs/guides/NEW_PROJECT_SETUP.md` - 새 프로젝트 설정 가이드 (전체 단계)

### 🔧 스크립트
- ✅ `rename-project.sh` - 프로젝트 이름 자동 변경 스크립트

### 📝 업데이트된 파일
- ✅ `README.md` - 새 프로젝트 시작 섹션 추가
- ✅ `docs/DOCUMENTATION_INDEX.md` - 설정 가이드 링크 추가
- ✅ `.gitignore` - 백업 디렉토리 제외 추가

---

## 🚀 사용 방법

### Option 1: 자동 스크립트 사용 (권장)

```bash
# 1. 실행 권한 부여
chmod +x rename-project.sh

# 2. 스크립트 실행
./rename-project.sh collabo yourproject

# 3. 안내에 따라 수동 작업 진행
#    - IntelliJ에서 패키지 Refactor
#    - JWT Secret Key 재생성
#    - SwaggerConfig 업데이트
#    - README 업데이트

# 4. 빌드 및 실행
./gradlew clean build
docker-compose up -d
./gradlew bootRun
```

### Option 2: 수동 설정

상세한 단계별 가이드를 따르세요:
📖 **[docs/guides/NEW_PROJECT_SETUP.md](docs/guides/NEW_PROJECT_SETUP.md)**

---

## 📋 스크립트가 자동으로 변경하는 항목

`rename-project.sh` 실행 시:

1. ✅ `settings.gradle` - rootProject.name
2. ✅ `compose.yaml` - 컨테이너명, DB명, 사용자명, 볼륨명
3. ✅ `application-dev.yml` - 데이터베이스 연결 정보
4. ✅ `application.yml` - 관련 설정 (있는 경우)
5. 📂 백업 생성 - `backup-YYYYMMDD-HHMMSS/` 디렉토리

### 수동으로 해야 하는 작업

스크립트 실행 후 반드시:

1. **패키지명 변경** (IntelliJ Refactor)
   - `com.example.collabo` → `com.example.yourproject`

2. **메인 클래스명 변경**
   - `CollaboApplication.java` → `YourprojectApplication.java`

3. **JWT Secret Key 재생성**
   ```bash
   openssl rand -base64 64
   ```
   생성된 키를 `application-dev.yml`에 설정

4. **SwaggerConfig 업데이트**
   - API 문서 제목 변경

5. **README.md 업데이트**
   - 프로젝트 설명, 기능 등

---

## 🎯 GitHub Template으로 만들기 (권장)

이 스타터 키트를 GitHub Template Repository로 설정하면 더 편리합니다:

### 1. Template Repository 설정

1. GitHub에서 리포지토리 페이지 이동
2. **Settings** 탭 클릭
3. 상단의 **Template repository** 체크박스 활성화
4. 저장

### 2. 새 프로젝트 생성

이제 이 리포지토리를 템플릿으로 사용:

1. 리포지토리 페이지에서 **"Use this template"** 버튼 클릭
2. 새 리포지토리 이름 입력 (예: `my-awesome-project`)
3. **"Create repository from template"** 클릭
4. 새 리포지토리가 생성됨 (Git 히스토리 없이 깨끗한 시작)

### 3. 프로젝트 설정

```bash
# 새 리포지토리 클론
git clone https://github.com/your-username/my-awesome-project.git
cd my-awesome-project

# 스크립트로 이름 변경
./rename-project.sh collabo myawesomeproject

# 수동 작업 완료 후 빌드
./gradlew clean build
```

---

## 📚 참고 문서

### 전체 가이드
- 📖 [NEW_PROJECT_SETUP.md](docs/guides/NEW_PROJECT_SETUP.md) - 상세 단계별 가이드

### 관련 문서
- ⚡ [QUICKSTART.md](docs/QUICKSTART.md) - 빠른 시작 가이드
- 🏗️ [ARCHITECTURE.md](docs/ARCHITECTURE.md) - 아키텍처 문서
- 📑 [DOCUMENTATION_INDEX.md](docs/DOCUMENTATION_INDEX.md) - 전체 문서 목록

---

## 🔍 체크리스트

새 프로젝트 설정 완료 확인:

### 자동 변경 항목
- [ ] `settings.gradle` 프로젝트명 변경 확인
- [ ] `compose.yaml` 모든 이름 변경 확인
- [ ] `application-dev.yml` DB 설정 변경 확인
- [ ] 백업 디렉토리 생성 확인

### 수동 작업 항목
- [ ] 패키지명 Refactor 완료
- [ ] 메인 클래스명 변경 완료
- [ ] 테스트 클래스명 변경 완료
- [ ] JWT Secret Key 재생성 및 설정
- [ ] SwaggerConfig API 제목 변경
- [ ] README.md 업데이트

### 테스트
- [ ] `./gradlew clean build` 성공
- [ ] `docker-compose up -d` 성공
- [ ] 애플리케이션 정상 실행
- [ ] Swagger UI 정상 접속 (`http://localhost:8081/swagger-ui.html`)
- [ ] API 문서 제목 확인

### Git
- [ ] Git 커밋
- [ ] GitHub 푸시
- [ ] 불필요한 백업 파일 정리

---

## ⚠️ 주의사항

### 보안
- ⚠️ **JWT Secret Key는 절대로 재사용하지 마세요**
- ⚠️ 데이터베이스 비밀번호도 변경하세요
- ⚠️ `application-dev.yml`을 Git에 커밋하지 않도록 주의

### Docker
- 🐳 기존 Docker 볼륨 완전히 제거 (`docker-compose down -v`)
- 🐳 새 컨테이너명이 충돌하지 않는지 확인

### Git
- 📝 백업 디렉토리는 `.gitignore`에 자동 추가됨
- 📝 스타터 키트의 Git 히스토리를 원하지 않으면 `.git` 삭제 후 재시작

---

## 🆘 문제 해결

### 스크립트 실행 권한 오류

```bash
# macOS/Linux
chmod +x rename-project.sh

# Windows (Git Bash)
git update-index --chmod=+x rename-project.sh
```

### sed 명령어 오류 (macOS)

macOS의 sed는 `-i` 옵션 사용이 다릅니다. 스크립트에 이미 반영되어 있습니다.

### IntelliJ Refactor 실패

1. **File** → **Invalidate Caches** 실행
2. IntelliJ 재시작
3. 다시 Refactor 시도

### Docker 컨테이너명 충돌

```bash
# 기존 컨테이너 확인
docker ps -a

# 충돌하는 컨테이너 제거
docker rm -f collaboapp-mysql

# 볼륨도 제거
docker volume prune
```

---

## 🎉 완료!

모든 설정이 완료되었다면:

1. ✅ 프로젝트 이름 변경 완료
2. ✅ 빌드 및 실행 테스트 완료
3. ✅ Git 커밋 및 푸시 완료

**이제 비즈니스 로직 개발을 시작하세요!**

---

<div align="center">

**🚀 새 프로젝트 시작을 축하합니다!**

[메인 문서로 돌아가기](../README.md)

</div>

