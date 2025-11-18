#!/bin/bash

# =================================================================
# 프로젝트 이름 자동 변경 스크립트
# 사용법: ./rename-project.sh collabo yourproject
# =================================================================

OLD_NAME=$1
NEW_NAME=$2

# 색상 정의
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 사용법 출력
if [ -z "$OLD_NAME" ] || [ -z "$NEW_NAME" ]; then
    echo -e "${RED}❌ 사용법:${NC} ./rename-project.sh <기존이름> <새이름>"
    echo ""
    echo "   예시: ./rename-project.sh collabo myproject"
    echo ""
    exit 1
fi

echo ""
echo -e "${BLUE}╔═══════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║        프로젝트 이름 자동 변경 스크립트              ║${NC}"
echo -e "${BLUE}╚═══════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${YELLOW}🔄 프로젝트 이름 변경: ${OLD_NAME} → ${NEW_NAME}${NC}"
echo ""

# 백업 디렉토리 생성
BACKUP_DIR="backup-$(date +%Y%m%d-%H%M%S)"
mkdir -p "$BACKUP_DIR"

# 1. settings.gradle
if [ -f "settings.gradle" ]; then
    echo -e "${GREEN}📝 settings.gradle 수정 중...${NC}"
    cp settings.gradle "$BACKUP_DIR/"
    sed -i.bak "s/rootProject.name = '$OLD_NAME'/rootProject.name = '$NEW_NAME'/g" settings.gradle
    rm settings.gradle.bak
    echo "   ✅ 완료"
else
    echo -e "${RED}   ⚠️  settings.gradle 파일을 찾을 수 없습니다${NC}"
fi

# 2. compose.yaml
if [ -f "compose.yaml" ]; then
    echo -e "${GREEN}📝 compose.yaml 수정 중...${NC}"
    cp compose.yaml "$BACKUP_DIR/"
    sed -i.bak "s/${OLD_NAME}/${NEW_NAME}/g" compose.yaml
    rm compose.yaml.bak
    echo "   ✅ 완료"
else
    echo -e "${YELLOW}   ⚠️  compose.yaml 파일을 찾을 수 없습니다${NC}"
fi

# 3. docker-compose.yml (대체 파일명)
if [ -f "docker-compose.yml" ]; then
    echo -e "${GREEN}📝 docker-compose.yml 수정 중...${NC}"
    cp docker-compose.yml "$BACKUP_DIR/"
    sed -i.bak "s/${OLD_NAME}/${NEW_NAME}/g" docker-compose.yml
    rm docker-compose.yml.bak
    echo "   ✅ 완료"
fi

# 4. application-dev.yml
if [ -f "src/main/resources/application-dev.yml" ]; then
    echo -e "${GREEN}📝 application-dev.yml 수정 중...${NC}"
    cp src/main/resources/application-dev.yml "$BACKUP_DIR/"
    sed -i.bak "s/${OLD_NAME}/${NEW_NAME}/g" src/main/resources/application-dev.yml
    rm src/main/resources/application-dev.yml.bak
    echo "   ✅ 완료"
else
    echo -e "${RED}   ⚠️  application-dev.yml 파일을 찾을 수 없습니다${NC}"
fi

# 5. application.yml (필요시)
if [ -f "src/main/resources/application.yml" ]; then
    if grep -q "$OLD_NAME" src/main/resources/application.yml; then
        echo -e "${GREEN}📝 application.yml 수정 중...${NC}"
        cp src/main/resources/application.yml "$BACKUP_DIR/"
        sed -i.bak "s/${OLD_NAME}/${NEW_NAME}/g" src/main/resources/application.yml
        rm src/main/resources/application.yml.bak
        echo "   ✅ 완료"
    fi
fi

echo ""
echo -e "${GREEN}✅ 자동 변경 완료!${NC}"
echo ""
echo -e "${YELLOW}📂 백업 위치: ./${BACKUP_DIR}${NC}"
echo ""
echo -e "${BLUE}╔═══════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║             다음 단계 (수동 작업 필요)                ║${NC}"
echo -e "${BLUE}╚═══════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${YELLOW}1. IntelliJ IDEA에서 패키지명 변경:${NC}"
echo "   - src/main/java/com/example/$OLD_NAME 우클릭"
echo "   - Refactor → Rename → '$NEW_NAME' 입력"
echo "   - Scope: 'Whole project' 선택"
echo ""
echo -e "${YELLOW}2. 메인 클래스명 변경:${NC}"
echo "   - ${OLD_NAME^}Application.java → ${NEW_NAME^}Application.java"
echo "   - 테스트 클래스도 함께 변경"
echo ""
echo -e "${YELLOW}3. JWT Secret Key 재생성 (필수!):${NC}"
echo "   $ openssl rand -base64 64"
echo "   - 생성된 키를 application-dev.yml의 jwt.accessSecret에 설정"
echo ""
echo -e "${YELLOW}4. SwaggerConfig.java 수정:${NC}"
echo "   - API 문서 제목 변경 (Collabo → ${NEW_NAME^})"
echo ""
echo -e "${YELLOW}5. README.md 업데이트:${NC}"
echo "   - 프로젝트 설명, 기능, 문서 링크 등 수정"
echo ""
echo -e "${YELLOW}6. 빌드 및 테스트:${NC}"
echo "   $ ./gradlew clean build"
echo "   $ docker-compose up -d"
echo "   $ ./gradlew bootRun --args='--spring.profiles.active=dev'"
echo ""
echo -e "${YELLOW}7. 확인:${NC}"
echo "   - http://localhost:8081/swagger-ui.html"
echo ""
echo -e "${BLUE}╔═══════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║   상세 가이드: docs/guides/NEW_PROJECT_SETUP.md     ║${NC}"
echo -e "${BLUE}╚═══════════════════════════════════════════════════════╝${NC}"
echo ""

