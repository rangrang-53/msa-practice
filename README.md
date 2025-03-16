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
  
     ```java
     
      @PostMapping("/login")
        public SignInResponseDTO signIn (@RequestBody SignInRequestDTO signInRequestDTO,
                                         HttpServletResponse response) {
            System.out.println(signInRequestDTO);
    
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequestDTO.getUsername(),
                            signInRequestDTO.getPassword()
                    )
            );
    
            SecurityContextHolder.getContext().setAuthentication(authenticate);
    
            Member member = ((CustomUserDetails)authenticate.getPrincipal()).getMember();
    
            String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
            String refreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));
    
            log.info("Access Token: {}", accessToken);
            log.info("Refresh Token: {}", refreshToken);
    
    
            CookieUtil.addCookie(response, "refreshToken", refreshToken, 7*24*60*60);
    
            return SignInResponseDTO.builder()
                    .isLoggedIn(true)
                    .token(accessToken)
                    .userId(member.getUserId())
                    .userName(member.getUserName())
                    .build();
        }
     
     ```

2. **JWT 토큰 생성 및 검증**
   - `TokenProvider` 클래스는 JWT 토큰의 생성, 검증 및 정보 추출 담당
     
     ```java

      public int validToken(String token) {
            try {
                getClaims(token);
                return 1;
            } catch (ExpiredJwtException e){
                log.info("토큰 만료");
                return 2;
            } catch (Exception e) {
                System.out.println("Token 복호화 에러 : " + e.getMessage());
                return 3;
            }
    
        }
     
     ```
     
   - 클라이언트는 이후 모든 요청 시 HTTP 헤더의 `Authorization: Bearer <token>` 형식으로 토큰 전송
     
     ```java
     
       private final static String HEADER_AUTHORIZATION = "Authorization";
       private final static String TOKEN_PREFIX = "Bearer ";
          private String resolveToken(HttpServletRequest request) {
            String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
    
            if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
                return bearerToken.substring(TOKEN_PREFIX.length());
            }
    
            return null;
            }
     
     ```
  
     

---

## 데이터베이스 구성 (Master-Slave)

  ![Image](https://github.com/user-attachments/assets/387eb6b0-8796-4473-af85-6a5433126d9c)
  ![Image](https://github.com/user-attachments/assets/bc7576bc-2f87-40db-b2fe-adb148bee049)
  
  -  **마스터 DB** : 모든 쓰기 작업(INSERT, UPDATE, DELETE) 처리

  -  **슬레이브 DB** : 읽기 작업(SELECT)을 처리하여, 성능과 부하 분산
    

  -  application.yml
      ```java
      
      spring:
        datasource:
          master:
            driver-class-name: com.mysql.cj.jdbc.Driver
            jdbc-url: jdbc:mysql://localhost:3307/java_practice?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8
            
          slave:
            jdbc-url: jdbc:mysql://localhost:3308/java_practice?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8
            driver-class-name: com.mysql.cj.jdbc.Driver
      
     ```


  -  읽기와 쓰기/수정/분류에 따른 readOnly 표기
      
      ```java

       @Transactional(readOnly = true)
          public List<Article> getArticles(int page, int size) {
          int offset = (page - 1) * size;

          ...
        }

      ```

      ```java
    
        @Transactional
          public void saveArticle(String userId, String title, String content, MultipartFile file) {

          String path = null;
            ...
        }

      ```

---

## 파일 업로드 및 관리

  -  파일 업로드 기능 : FileService에서 구현되어, 게시글 작성 시 첨부 파일을 서버의 지정된 폴더에 저장
  -  업로드된 파일은 게시글과 연동되어 다운로드 및 삭제 기능 지원

  - 파일 업로드
    
  ```java

      @Service
        public class FileService {
        
            private final String UPLOADED_FOLDER = System.getProperty("user.home") + File.separator +
                    "Desktop" + File.separator + "msa-practice" + File.separator + "uploads" + File.separator;
        
            public String fileUpload(MultipartFile file) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        
                    Files.write(path, bytes);
                    return UPLOADED_FOLDER + file.getOriginalFilename();
        
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        
            }

  ```

  - 파일 다운로드


  ```java

          public Resource downloadFile(String filename) {
              try {
                  Path path = Paths.get(UPLOADED_FOLDER + filename).normalize();
                  UrlResource resource = new UrlResource(path.toUri());
      
                  if(!resource.exists() || !resource.isReadable()) {
                      throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
                  }
                  return resource;
              } catch (MalformedURLException e) {
                  throw new RuntimeException(e);
              }
          }

  ```

  - 파일 삭제

  ```java

          public void deleteFile(String filePath) {
              try {
                  if(!filePath.trim().isEmpty()) {
                      Path path = Paths.get(filePath);
                      Files.deleteIfExists(path);
                  }
              } catch (Exception e) {
                  e.printStackTrace();
                  }
              }
          }

```
