# 로그인 프로젝트

1. **마스터-슬레이브 데이터베이스** 아키텍처
2. **JWT 기반 인증** 및 **역할 기반 접근 제어(RBAC)** 를 활용 

---

## 개발 환경

- JDK 21
- MySQL 8.4.4 (LTS)
- Spring Boot 3.1.3

---

## 주요 기능

- **사용자 인증 및 권한 관리**
  - JWT 토큰 기반 로그인/로그아웃
  - 신규 회원은 기본적으로 `ROLE_USER` 권한 부여
  - 일반 사용자는 본인이 작성한 게시글만 수정/삭제 가능

- **게시판 기능**
  - 게시글 목록 조회 (페이지네이션 지원)
  - 게시글 상세 조회 (작성자 및 관리자만 접근 가능)
  - 게시글 작성, 수정, 삭제 기능
  - 첨부 파일 업로드 및 다운로드

- **데이터베이스 이중화**
  - **마스터-슬레이브** 구조로, 마스터는 쓰기 작업, 슬레이브는 읽기 작업을 처리하여 부하 분산 및 고가용성을 지원

---

## 시스템 아키텍처

### JWT 인증 및 권한 처리 흐름

![Image](https://github.com/user-attachments/assets/abeaa507-90c8-4a59-9db7-12ed78c8a4d4)

1. **회원가입 및 로그인**
   - 신규 회원은 기본적으로 `ROLE_USER` 권한을 부여
   - 로그인 시, `MemberApiController`에서 사용자를 인증하고 JWT 토큰(액세스 및 리프레시 토큰) 발급

2. **JWT 토큰 생성 및 검증**
   - `TokenProvider` 클래스는 JWT 토큰의 생성, 검증 및 정보 추출 담당
   - 클라이언트는 이후 모든 요청 시 HTTP 헤더의 `Authorization: Bearer <token>` 형식으로 토큰 전송

---

## 데이터베이스 구성 (Master-Slave)

  ![Image](https://github.com/user-attachments/assets/387eb6b0-8796-4473-af85-6a5433126d9c)
  ![Image](https://github.com/user-attachments/assets/bc7576bc-2f87-40db-b2fe-adb148bee049)
  
  -  **마스터 DB** : 모든 쓰기 작업(INSERT, UPDATE, DELETE)을 처리

  -  **슬레이브 DB** : 읽기 작업(SELECT)을 처리하여, 성능과 부하 분산

---

## 파일 업로드 및 관리

  -  파일 업로드 기능 : FileService에서 구현되어, 게시글 작성 시 첨부 파일을 서버의 지정된 폴더에 저장
  -  업로드된 파일은 게시글과 연동되어 다운로드 및 삭제 기능을 지원

  
