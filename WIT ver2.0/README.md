# ✈️ Way Into Travel (여행일정 구매/판매, 여행 후기 공유 사이트)

<img src="https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/wit.png" alt="WIT Banner" width="100%"/>

---

## 1️⃣ Project Overview (프로젝트 개요)

- **📝 프로젝트 이름**: **Way Into Travel (WIT)**  
- **📖 프로젝트 설명**:  
  여행일정을 구매 및 판매할 수 있는 플랫폼입니다.  
  - **여행 가이드가 되어 일정 판매**  
  - **다른 가이드 일정 구매**  
  - **여행 후기를 공유**할 수 있는 커뮤니티 기능 제공  

---

## 2️⃣ Team Members (팀원 및 팀 소개)

| 👩‍💻 이름   | 🎯 역할      | 🌍 GitHub 프로필                        |
| ---------- | --------- | -------------------------------------- |
| 박정은     | 기획 및 개발 |  [🔗 GitHub](https://github.com/shahmaran0207) |

---

## 3️⃣ Development Environment (개발 환경)

### 1. Front-End

| HTML | JavaScript | CSS |
|:----:|:----------:|:---:|
| <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original.svg" alt="HTML5" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="JavaScript" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original.svg" alt="CSS3" width="60"/> |

### 2. Back-End

| Spring Boot | Java | Firebase | Kakao Map API |
|:-----------:|:----:|:--------:|:-------------:|
| <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring Boot" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/firebase/firebase-plain.svg" alt="Firebase" width="60"/> | <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/google/google-original.svg" alt="Kakao Map API" width="60"/> |

### 3. Version Control

| GitHub |
|:------:|
| <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/github/github-original.svg" alt="GitHub" width="60"/> |

### 4. Deployment Environment

| RDS | EC2 | S3 |
|:---:|:---:|:--:|
| <img src="https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/rds.png" alt="RDS" width="60"/> | <img src="https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/ec2.png" alt="EC2" width="60"/>| <img src="https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/s3.png" alt="S3" width="60"/> |

<br>

## 4️⃣ Special Technology

### 1. Firebase
- 실제 사업을 컨셉으로 하였기에 보안을 위해 사용했습니다.
- Firebase의 Authentication를 통해 이메일 / 비밀번호로 회원가입 및 로그인을 하며 DB에 직접 비밀번호를 저장하지는 않습니다.
- 이메일 중복을 위해 가입한 이메일인지 체크하고 Firebase의 비밀번호 자릿수를 체크하는 로직을 회원가입 페이지에 추가하였습니다. 
<br>

### 2. S3
 - 사진 업로드 및 출력을 위해 사용하였습니다.
- 다운로드 시 업로드한 주소를 그래도 사용하니 접근이 되지 않아, 강제로 출력할 때 접근 주소를 변환하는 로직을 추가하였습니다. 

<br>

## 5️⃣Project Structure

```
📦project/
├── 📂 src/
│   └──  📂 main/
│       ├──  📂 java/
│       │   └──  📂 com/
│       │       └──  📂 JPA/
│       │           └── 📂 Member/
│       │               ├── MemberApplication.java                                           (프로젝트 메인 클래스, 실행 진입점)
│       │               ├──  📂 config/                                                      (설정 파일 모음)
│       │               │   └── 📜  FirebaseConfig.java                                      (FIrebase 초기화 파일)
│       │               │   └──  📜 QnA_WebConfig.java                                       (QnA 이미지 업로드 경로 설정 - 로컬 작업 시 사용)
│       │               │   └──  📜 S3Config.java                                            (AWS S3 관련 설정 - application.yml 에 있는 시크릿 키 밋 엑세스 키 적용)
│       │               │   └── 📜  WebConfig.java                                           (Board 게시판 사진 경로 설정 - 로컬 작업 시 사용, 로그인 인터셉터 적용 페이지 설정)
│       │               │
│       │               ├──  📂 Controller/                                                  ( 컨트롤러: 요청 처리 및 뷰 반환)
│       │               │   └──  📂 Board/   
│       │               │   │   └── 📜  BoardController.java                                 (게시판 컨트롤러 - CRUD 및 페이지 반환)
│       │               │   │   └──  📜 BoardHateController.java                             (게시판  싫어요 컨트롤러)
│       │               │   │   └──  📜 BoardLikeController.java                             (게시판  좋아요 컨트롤러)
│       │               │   │   └──  📜 CommentController.java                               (댓글 컨트롤러 = CRUD)
│       │               │   │
│       │               │   └──  📂 Guide/      
│       │               │   │   └──  📂 Guide/               
│       │               │   │   │   └──  📜 GuideController.java                             (가이드 컨트롤러 - 가이드 목록 및 상세정보 페이지 반환)
│       │               │   │   │   └── 📜  GuideHateController.java                         (가이드 싫어요 컨트롤러)
│       │               │   │   │   └── 📜  GuideLikeController.java                         (가이드 좋아요 컨트롤러)
│       │               │   │   │ 
│       │               │   │   └──  📂 TripList/             
│       │               │   │         └──  📜 SellController.java                            (판매 일정 컨트롤러 - CRUD)
│       │               │   │         └──  📜 TripListHateController.java                    (판매 일정 싫어요 컨트롤러)
│       │               │   │         └──  📜 TripListLikeController.java                    (판매 일정 좋아요컨트롤러)
│       │               │   │   
│       │               │   └──  📂  Member/         
│       │               │   │   └── 📜  MemberController.java                                (멤버 컨트롤러 - 가입 및 반환, 가이드 신청)
│       │               │   │
│       │               │   └──   📂 QnA/
│       │               │   │   └── 📂  Answer/       
│       │               │   │   │   └── 📜  AnswerController.java                            (답변 컨트롤러 - CRUD)
│       │               │   │   │        
│       │               │   │   └──  📂 Question/        
│       │               │   │         └── 📜 QuestionController.java                         (질문 컨트롤러 - CRUD) 
│       │               │   │
│       │               │   └──   📂 Travel_Review/            
│       │               │   │         └── 📜 Travel_Review_Controller.java                   (여행 후기 작성 컨트롤러 - CRUD) 
│       │               │   │         └── 📜 Travel_Review_HateController.java               (여행 후기 싫어요 컨트롤러) 
│       │               │   │         └── 📜 Travel_Review_LikeController.java               (여행 후기 좋아요 컨트롤러) 
│       │               │   │
│       │               │   └──  📜 HomeController.java                                      (메인 화면 구성을 위한 컨트롤러)
│       │               │
│       │               ├──  📂 DTO/                                                         (데이터 전송 객체)
│       │               │   └── 📂  Board/   
│       │               │   │   └──  📜 BoardDTO.java                                        (게시판 데이터 모델)
│       │               │   │   └──  📜 BoardHateDTO.java                                    (게시판 싫어요 데이터 모델)
│       │               │   │   └──  📜  BoardLikeDTO.java                                   (게시판 좋아요 데이터 모델)
│       │               │   │   └──  📜  CommentDTO.java                                     (댓글 데이터 모델)
│       │               │   │
│       │               │   └──  📂 Guide/
│       │               │   │   └── 📂  guide/
│       │               │   │   │    └──  📜 GuideDTO.java                                   (가이드 데이터 모델)
│       │               │   │   │    └──  📜 GuideHateDTO.java                               (가이드 싫어요 데이터 모델)
│       │               │   │   │    └──  📜 GuideLikeDTO.java                               (가이드 좋아요 데이터 모델)
│       │               │   │   │
│       │               │   │   └──  📂 TripList/              
│       │               │   │       └── 📜  TripListDTO.java                                 (판매 일정 데이터 모델)
│       │               │   │       └── 📜 TripListHateDTO.java                              (판매 일정 싫어요 데이터 모델)
│       │               │   │       └── 📜  TripListLikeDTO.java                             (판매 일정 좋아요 데이터 모델)
│       │               │   │
│       │               │   └──  📂 Member/   
│       │               │   │   └── 📜  MemberDTO.java                                       (멤버 데이터 모델)
│       │               │   │   └── 📜  MemberTripListDTOjava                                (구매한 일정 데이터 모델)
│       │               │   │
│       │               │   └──  📂 QnA/
│       │               │   │   └──  📂 Answer/       
│       │               │   │   │    └── 📜  AnswerDTO.java                                  (답변 데이터 모델)
│       │               │   │   │
│       │               │   │   └──  📂 Question/       
│       │               │   │         └── 📜  QuestionDTO.java                               (질문 데이터 모델)
│       │               │   │
│       │               │   └──  📂 Travel_Review/
│       │               │        └── 📜  ReviewDTO.java                                      (여행후기 데이터 모델)
│       │               │        └── 📜  TravelReviewHateDTO.java                            (여행후기 싫어요 데이터 모델)
│       │               │        └──  📜 TravelReviewLikeDTO.java                            (여행후기 좋아요 데이터 모델)
│       │               │
│       │               ├──  📂 Entity/                                                      (데이터베이스와 매핑되는 엔티티 클래스)
│       │               │   └──  📂 Board/   
│       │               │   │   └──  📜 BaseEntity.java                                      (게시판 기본 엔티티 - 생성, 수정 시간)
│       │               │   │   └──  📜 BoardEntity.java                                     (게시판 엔티티)
│       │               │   │   └──  📜  BaseFileEntity.java                                 (게시판 이미지 엔티티)
│       │               │   │   └──  📜  BoardHateEntity.java                                (게시판 싫어요 엔티티)
│       │               │   │   └──  📜 BoardLikeEntity.java                                 (게시판 좋아요 엔티티)
│       │               │   │   └──  📜 CommentEntity.java                                   (댓글 엔티티)
│       │               │   │
│       │               │   └──  📂 Guide/
│       │               │   │   └── 📂  Guide/
│       │               │   │   │    └──  📜 GuideEntity.java                                (가이드 엔티티)
│       │               │   │   │    └── 📜  GuideHateEntity.java                            (가이드 싫어요 엔티티)
│       │               │   │   │    └── 📜 GuideLikeEntity.java                             (가이드 좋아요 엔티티)
│       │               │   │   │
│       │               │   │   └── 📂  TripList/              
│       │               │   │       └──  📜 TripListEntity.java                              (판매일정 엔티티)
│       │               │   │       └──  📜 TripListHateEntity.java                          (판매일정 싫어요 엔티티)
│       │               │   │       └──  📜 TripListLikeEntity.java                          (판매일정 좋아요 엔티티)
│       │               │   │
│       │               │   └── 📂  Member/   
│       │               │   │   └── 📜  MemberEntity.java                                    (멤버 엔티티)
│       │               │   │   └── 📜  MemberProfileEntity.java                             (멤버 프로필 사진 엔티티)
│       │               │   │   └──  📜 MemberTripListEntity.java                            (구매일정 엔티티)
│       │               │   │
│       │               │   └── 📂  QnA/
│       │               │   │   └──  📂 Answer/       
│       │               │   │   │    └── 📜  AnswerEntity.java                               (답변 엔티티)
│       │               │   │   │
│       │               │   │   └── 📂  Question/       
│       │               │   │         └── 📜  QuestionEntity.java                           (질문 엔티티)
│       │               │   │         └──  📜 QuestionFileEntity.java                       (질문 이미지 엔티티)
│       │               │   │
│       │               │   └── 📂  Travel_Review/
│       │               │        └──  📜  ReviewBaseEntity.java                             (여행 후기 기본 엔티티 - 생성시간)
│       │               │        └──  📜  ReviewEntity.java                                 (여행후기 엔티티)
│       │               │        └──  📜  ReviewFileEntity.java                             (여행후기 이미지 파일 엔티티)
│       │               │        └──  📜 TravelReviewHateEntity.java                        (여행후기 싫어요 엔티티)
│       │               │        └──  📜 TravelReviewLikeEntity.java                        (여행후기 좋아요 엔티티)
│       │               │
│       │               ├── 📂  interceptor/
│       │               │   └──  📜 LoginInterceptor.java                                   (로그인 인터셉터)
│       │               │
│       │               ├── 📂  Repository /                                                (데이터베이스 접근 객체)
│       │               │   └── 📂  Board/   
│       │               │   │    └──  📜  BoardFileRepository.java                          (게시판 파일 레퍼지토리)
│       │               │   │    └──  📜  BoardHateRepository.java                          (게시판 싫어요 레퍼지토리)
│       │               │   │    └──  📜 BoardLikeRepository.java                           (게시판 좋아요 레퍼지토리)
│       │               │   │    └──  📜 BoardRepository.java                               (게시판  레퍼지토리)
│       │               │   │    └──  📜 CommentRepository.java                             (댓글 레퍼지토리)
│       │               │   │
│       │               │   └──  📂 Guide/
│       │               │   │   └── 📂  Guide/
│       │               │   │   │    └──  📜 GuideHateRepository.java                       (가이드 싫어요 레퍼지토리)
│       │               │   │   │    └──  📜 GuideLikeRepository.java                       (가이드 좋아요 레퍼지토리)
│       │               │   │   │    └──  📜 GuideRepository.java                           (가이드 레퍼지토리)
│       │               │   │   │
│       │               │   │   └── 📂  TripList/              
│       │               │   │         └──  📜 TripListHateRepository.java                   (여행일정 싫어요 레퍼지토리)
│       │               │   │         └──  📜 TripListLikeRepository.java                   (여행일정 좋아요 레퍼지토리)
│       │               │   │         └── 📜  TripListRepository.java                       (여행일정 레퍼지토리)
│       │               │   │
│       │               │   └── 📂  Member/   
│       │               │   │   └── 📜  MemberProfileRepository.java                        (멤버 프로필 이미지 레퍼지토리)
│       │               │   │   └── 📜  MemberRepository.java                               (멤버 레퍼지토리)
│       │               │   │   └── 📜  MemberTripListRepository.java                       (구매일정 레퍼지토리)
│       │               │   │
│       │               │   └──  📂 QnA/
│       │               │   │   └── 📂  Answer/       
│       │               │   │   │    └──  📜 AnswerRepository.java                          (답변 레퍼지토리)
│       │               │   │   │
│       │               │   │   └── 📂  Question/       
│       │               │   │         └──  📜 QuestionRepository.java                       (질문 레퍼지토리)
│       │               │   │
│       │               │   └── 📂  Travel_Review/
│       │               │              └──  📜 TravelReviewFileRepository.java              (여행후기 이미지 파일 레퍼지토리)
│       │               │              └──  📜 TravelReviewHateRepository.java              (여행후기 싫어요 레퍼지토리)
│       │               │              └──  📜 TravelReviewLikeRepository.java              (여행후기 좋아요 레퍼지토리)
│       │               │              └──  📜 TravelReviewRepository.java                  (여행후기 레퍼지토리)
│       │               │
│       │               └──  📂 Service/                                                    (비즈니스 로직)
│       │                   └── 📂  Board/   
│       │                   │     └── 📜  BoardHateService.java                             (게시판 싫어요 서비스)
│       │                   │     └──  📜 BoardLikeService.java                             (게시판 좋아요 서비스)
│       │                   │     └──  📜 BoardService.java                                 (게시판 서비스)
│       │                   │     └──  📜 CommentService.java                               (댓글 서비스)
│       │                   │
│       │                   └──  📂 Guide/
│       │                   │  └── 📂  Guide/
│       │                   │  │    └──  📜 GuideHateService.java                           (가이드 싫어요 서비스)   
│       │                   │  │    └── 📜  GuideLikeService.java                           (가이드 좋아요 서비스)   
│       │                   │  │    └── 📜  GuideService.java                               (가이드 서비스)   
│       │                   │  │
│       │                   │  └──  📂 TripList/              
│       │                   │        └──  📜 TripListHateService.java                       (판매일정 싫어요 서비스)   
│       │                   │        └──  📜 TripListLikeService.java                       (판매일정 좋아요 서비스)   
│       │                   │        └──  📜 TripListService.java                           (판매일정 서비스)   
│       │                   │    
│       │                   └──  📂 Member/   
│       │                   │  └── 📜  MemberService.java                                   (멤버 서비스)   
│       │                   │  └── 📜  MemberTripListService.java                           (구매일정 서비스)   
│       │                   │  └──  📜 ProfileService.java                                  (이미지 업로드 서비스)   
│       │                   │
│       │                   └── 📂  QnA/
│       │                   │ └── 📂  Answer/       
│       │                   │  │    └──  📜 AnswerService.java                              (답변서비스)   
│       │                   │  │ 
│       │                   │  └──  📂 Question/       
│       │                   │        └── 📜  QuestionService.java                           (질문서비스)   
│       │                   │
│       │                   └──  📂 Travel_Review/
│       │                   │        └──  📜 TravelReviewHateService.java                   (여행후기 싫어요 서비스)   
│       │                   │        └──  📜 TravelReviewLikeService.java                   (여행후기 좋아요서비스)   
│       │                   │        └── 📜  TravelReviewService.java                       (여행후기 서비스)   
│       │                   │
│       │                   └──  📜 S3Service.java                                          (S3 서비스)   
│       │               
│       │               
│       └──  📂 resources/
│           ├── 📂  static/
│           │   ├── 📂  assets/
│           │   │   ├──  📂 css/
│           │   │   └── 📂  js/     
│           │   └── 📂  images/           
│           │   
│           └──  📂 templates/
│           │   └──  📂 board/
│           │   │    └── 📜 detail.html                                                      (게시판 상세조회 페이지)   
│           │   │    └── 📜 paging.html                                                      (게시판 목록 페이지)   
│           │   │    └── 📜 save.html                                                        (게시판 작성 페이지)   
│           │   │    └── 📜 update.html                                                      (게시판 수정 페이지)   
│           │   │
│           │   └── 📂  guide/
│           │   │    └── 📜  detail.html                                                     (가이드 상세조회 페이지)   
│           │   │    └──  📜 list.html                                                       (가이드 목록 페이지)   
│           │   │
│           │   └── 📂  member/
│           │   │    └── 📜  buydetail.html                                                  (구매일정 상세조회 페이지)   
│           │   │    └──  📜 buylist.html                                                    (구매일정 목록 페이지)   
│           │   │    └── 📜  detail.html                                                     (멤버 상세조회 페이지)   
│           │   │    └──  📜 list.html                                                       (멤버 목록 페이지 - 배포에서는 사용 X)   
│           │   │    └── 📜  login.html                                                      (로그인 페이지)   
│           │   │    └── 📜  myPage.html                                                     (멤버 마이 페이지)   
│           │   │    └── 📜  save.html                                                       (회원가입 페이지)   
│           │   │    └──  📜 update.html                                                     (개인정보 수정 페이지)   
│           │   │
│           │   └──  📂 QnA/
│           │   │    └── 📂  Question/
│           │   │         └── 📜  detail.html                                                (문의사항 상세조회 페이지)  
│           │   │         └── 📜  list.html                                                  (문의사항 목록 페이지)  
│           │   │         └──  📜 write.html                                                 (문의사항 작성 페이지)  
│           │   │
│           │   └──  📂 Travel_Review/
│           │   │    └──  📜 detail.html                                                     (여행후기 상세조회 페이지)  
│           │   │    └── 📜  paging.html                                                     (여행후기 목록 페이지)  
│           │   │    └── 📜  save.html                                                       (여행후기 작성 페이지)  
│           │   │
│           │   └── 📂  TravelList/
│           │   │    └──  📜 detail.html                                                     (판매일정 상세조회 페이지)  
│           │   │    └──  📜 paging.html                                                     (판매일정 목록 페이지)  
│           │   │    └── 📜  save.html                                                       (판매일정 작성 페이지)  
│           │   │
│           │   └── 📜  home.html                                                            (메인 페이지) 
│           │   └── 📜 header.html                                                           (헤더 파일) 
│           │   
│           └── 🍃 application.yml                                                           
│           └── 🟣 service.json                                                              (Firebase SDK 파일 - 보안을 위해 업로드 하지 않음) 
│
├── 🐋 Dockerfile                                                                            (배포 위한 도커파일) 
```

<br>

## 6️⃣ 신경 쓴 부분
### 1. Firebase
- 보안을 위해 Firebase를 사용하였습니다.
- 비밀번호를 직접 DB에 저장하지 않고 로그인시 사용되는 비밀번호만을 저장하여, 로그인 성공 시 Firebase에서 토큰을 받아오면 로그인에 사용한 이메일을 통해 개인정보를 DB에서 불러옵니다.
<br>

### 2. S3
- 사진 업로드 및 출력을 위해 AWS의 S3를 사용하였습니다.
- 출력시 업로드한 경로 그대로 접근하니 접근제한이 되어, 강제로 주소를 변환하여 출력하였습니다.
<br>

### 3. Interceptor
- 초기구상 - 화이트리스트 방식: 모든 페이지를 막아두고 특정 페이지만 로그인 인터셉터에서 제외시켜려 하였으나, 배포 시 제외시키는 코드가 작동하지 않아 무한리다이렉션이 발생하였습니다.

- 배포 시 변경 - 블랙리스트 방식: 모든 페이지를 로그인 인터셉터에서 제외시키고 특정 페이지만 로그인 인터셉터를 적용시켰습니다. 

<br>

## 7️⃣ 페이지별 기능

### [초기화면]
- 헤더파일에 Guide, Community(자유게시판), 여행후기, 고객지원, 로그인, 회원가입을 선택할 수 있습니다.

| 초기화면 |
|----------|
|![first](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/wit.png)|

<br>

### [회원가입]
- 이메일 주소와 비밀번호를 입력하면 입력창에서 바로 유효성 검사가 진행됩니다
- 이메일을 다 입력하고 다른 곳을 클릭하면 즉시 DB에서 체크 후, 이미 가입한 이메일인지 확인합니다.
- 비밀번호의 경우 총 7자 이상이어야 가입이 가능하기에 글자수를 체크합니다.
- 이메일, 비밀번호의 유효성 검사 통과 및 이름까지 모두 입력해야 회원가입 버튼이 활성화됩니다.

| 회원가입 |
|----------|
|![save](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/membersave.gif)|

<br>

### [로그인]
- 이메일 주소와 비밀번호를 입력 후, 로그인 버튼을 누르면 Firebase를 통해 회원가입한 이메일과 비밀번호와 일치하는지 체크합니다.
- 회원가입한 이메일과 비밀번호와 일치한다면 특정 토큰값을 반환하고 이를 세션에 저장합니다.
- 비밀번호는 따로 DB에 저장하지 않기에 로그인 페이지 내에서 직접 유효성 검사는 불가합니다.
- 로그인 성공 시, 메인 페이지로 이동합니다.

<br>

| 로그인 |
|----------|
|![login](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/login.gif)|

<br>

### [로그아웃]
- 상단 의 헤더에 있는 로그아웃 버튼을 클릭하면 로그아웃이됩니다.
- 로그아웃 시, 세션에 저장한 멤버 아이디, 이메일, 파이어베이스의 토큰 값 등이 전부 삭제됩니다.

| 로그아웃 |
|----------|
|![logout](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/logout.gif)|

<br>


<br>

# 상단 배너
- 상단 배너의 WIT 버튼을 클릭하면 언제든 홈 화면으로 돌아옵니다.

- Guide를 누르면 Gudie 목록으로 이동하며 원하는 가이드를 조회할 수 있습니다.
  -Community를 누르면 자유게시판으로 이동합니다.
- 여행후기를 누르면 여행후기 게시판으로 이동합니다.
- 고객지원을 누르면 QnA 게시판으로 이동합니다.
- 로그인을 하지 않은 상태에서는 로그인, 회원가입 버튼이 보입니다.
- 로그인을 한 상태에서는 로그아웃과 마이페이지 버튼이 보입니다.
- Guide, Community, 여행후기, 고객지원은 로그인 하지 않은 상태에서 누르면 강제로 로그인 페이지로 이동합니다.

|상단 배너  |
|-------------------|
| ![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/banner_login.png)|
| ![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/banner_logout.png) |

<br>

### [마이페이지]
- 개인 아이디, 이메일, 이름, 사진이 확인 가능하며, 프로필 사진이 없다면 사진 칸 자체가 뜨지 않습니다.

- 정보수정, 회원탈퇴, 구매한 일정 조회가 가능합니다.
- 가이드 상태일 때는 판매중 일정 관리 버튼이, 가이드가 아닐때는 가이드 신청 버튼이 뜨며, 가이드 신청 버튼을 누르면 가이드로 정보가 바뀌게 됩니다.

| 가이드 상태일 때| 가이드가 아닐 때 |
|----------|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/guide-yes.png)|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/guide-no.png)|

<br>


### [가이드 목록]
- 가입 인원 중 가이드로 신청한 목록을 볼 수 있습니다.
- 조회 버튼 클릭 시, 상세조회 페이지로 넘어갑니다.

| 가이드 목록 |
|-------------------|
| ![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/guide-list.png)|

<br>

### [가이드 상세조회]
- 가이드 이름, 이메일, 좋아요, 싫어요 개수를 확인 가능합니다.
- 좋아요, 싫어요는 개인당 1번 누를 수 있으며, 다시한번 누르면 취소됩니다.

|가이드 상세조회  |
|-------------------|
| ![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/guide-detail.png)|


<br>

### [판매중인 일정]
- 가이드 상세조회 페이지에서 판매중인 일정 관리 버튼을 누르면 그 가이드가 판매중인 여행일정 목록을 조회가능합니다.
- 그 중 원하는 것을 조회하면 아래 페이지가 나타납니다.
- 가이드가 설정한 일정, 계절, 가격, 조회수, 내용 등이 확인가능합니다.
- 타인이 작성한 일정 혹은 내가 구매하지 않은 일정일 경우 결제하기 버튼이 나타납니다.
- 구매한 일정의 경우 결제하기 버튼이 나타나지 않습니다.
- 본인이 작성한 일정의 경우 결제하기 버튼 대신 삭제 버튼이 나타납니다.
- 결제의 경우 KG 이니시스를 통한 카드결제로 이루어집니다.


| 판매 일정 상세 조회  | 결제창  |
|-------------------|-------------------|
| ![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/sell.png)|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/pay.png)|


<br>

### [Community - 목록]
- 상단 배너에서 community를 누르면 자유게시판의 글 목록으로 이동합니다.

- 각 게시글의 아이디, 제목, 작성자, 작성시간, 조회수 등을 확인가능합니다.

- 한 페이지에 최대 10개의 게시글 까지 보이며 10개를 초과할 시 다음페이지로 넘어갑니다.


| 자유게시판 글 목록 |
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/community-list.png)|

<br>

### [Community - 작성]
- community 목록 페이지에서 글 쓰기 버튼을 누를 시 글 작성 페이지로 이동합니다.

- 게시글의 비밀번호, 제목, 내용, 파일 첨부 등이 가능하며, 글 작성 버튼을 누르면 글이 작성됩니다.


| 자유게시판 - 글 작성 |
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/community-write.png)|

<br>

### [Community - 상세조회]
- 게시글의 아이디, 제목, 작성자, 작성시간, 조회수, 내용 등이 확인 가능합니다.

- 댓글 또한 남길 수 있으며, 댓글 작성 시 작성자는 자동으로 고정됩니다.

- 자신이 작성한 게시그의 경우 수정, 삭제 버튼이 보이지만, 타인이 작성한 게시글의 경우 보이지 않습니다.

- 좋아요, 싫어요를 누를 수 있으며, 개수 또한 확인 가능합니다.

| 자유게시판 상세조회 |
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/community-detail.png)|

<br>

### [여행후기 목록]
- 상단 배너에서 여행후기를 누르면 자유게시판의 글 목록으로 이동합니다.

- 각 게시글의 아이디, 제목, 작성자, 작성시간, 조회수 등을 확인가능합니다.

- 한 페이지에 최대 10개의 게시글 까지 보이며 10개를 초과할 시 다음페이지로 넘어갑니다.

| 여행후기 목록 |
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/after-list.png)|

<br>

### [여행후기 상세조회]
- 여행후기 목록에서 제목을 클릭하면 상세조회 페이지로 이동합니다.
- 여행후기의 아이디, 제목, 작성자, 여행 시작일, 여행 종료일, 조회수, 내용, 여행 일자별 여행지, 좋아요, 싫어요가 확인 가능합니다.
- 여행지가 2개가 넘어가게 되면 여행지 순서별로 마커가 표시되며, 각 여행지가 직선으로 이어집니다.

<br>

| 여행후기 상세조회 |
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/after-detail.png)|

<br>

### [여행후기 작성]
- 여행 시작일, 종료일, 제목, 비밀번호까지 선택 후 날짜 설정 버튼을 누르면 날짜를 계산해서 일수만큼 주소를 검색할 수 있습니다.

  - 여행 시작일: 오늘날짜 이전부터 오늘 날짜까지 선택이 가능합니다.
  - 여행 종료일: 여행 시작일 부터 오늘 날짜까지 선택이 가능합니다.

<br>


- 여행지를 순서대로 검색 후 마커표시를 하면 지도에 순서대로 마커가 표시됩니다.

- 마커를 전부 표시 후, 내용을 작성 후, 제출 버튼을 누르면 여행후기가 작성됩니다.

<br>

| 여행후기 작성(날짜 선택 전)|여행후기 작성(날짜 선택 후)|
|----------|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/after-write.png)|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/after-write2.png)|

<br>

### [Q&A 목록]
- 사용자들이 작성한 문의사항 목록 페이지 입니다.

- 상단 배너에서 고객문의를 누르면 문의사항 글 목록으로 이동합니다.

- 각 게시글의 아이디, 제목, 작성자, 작성시간, 답변여부 등을 확인가능합니다.

- 한 페이지에 최대 10개의 게시글 까지 보이며 10개를 초과할 시 다음페이지로 넘어갑니다.

- 답변을 한 문의사항은 초록색 글씨로 '답변 완료'라는 글씨가 답변을 하지 않은 문의사항은 붉은 글씨로 '미답변'이라는 글씨가 답변상태에 표시됩니다.

<br>

| Q&A 목록|
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/Q&A-list.png)|

<br>

### [Q&A 작성]
- 사용자들이 사이트를 사용하며 생기는 문의사항을 작성하는 페이지 입니다.
- 비밀번호, 제목, 내용을 작성하고 문의사항의 사진으로 있으면 사진또한 첨부 가능합니다.
- 내용을 다 작성하고 글 작성을 누르면 문의사항이 작성됩니다.

<br>

| Q&A 작성|
|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/Q&A-write.png)|

<br>

### [Q&A 상세정보]
- 사용자가 작성한 문의사항에 대한 상세조회 페이지입니다.
- 제목, 아이디, 작성자, 작성시간, 조회수 내용이 확인 가능합니다.
- 답변을 한 내용은 답변이 보입니다.
- 관리자가 답변을 하지 않은 내용은 답변이 보이지 않습니다.
- 답변 작성 칸은 관리자로 로그인 했을때만 보이며, 일반 작성자의 경우 답변 작성 칸이 보이지 않습니다.

  - 관리자 ID: `test@naver.com`
  - 관리자 PW: `testtest`

<br>

| Q&A 상세정보 - 답변한 문의사항|Q&A 상세정보 - 답변하지 않은 문의사항|
|----------|----------|
|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/Q&A-yes.png)|![tab](https://github.com/shahmaran0207/Member_project/blob/main/Readme_images/Q&A-not.png)|

<br>

## 9. 개선 목표
### 1. 웹 소켓을 통한 채팅
- 처음 계획했던 가이드와 사용자 간의 매칭 기능을 위해 채팅 기능을 추가할 예정입니다.

<br>

### 2. GPS를 통한 친구 찾기 기능
- 갑작스럽게 여행을 떠났을 때, 현지에서 여행 친구를 찾기 위해 GPS 기능을 추가할 예정입니다.

<br>

### 3. 인터셉터 개선
- 현재 로그인 인터셉터는 블랙  리스트 방식입니다.
- 현재는 배포시, 로그인 인터셉터에서 제외시키는 코드가 작동하지 않아 블랙리스트 방식을 사용하였습니다.
- 이를, 보안성 개선을 위해 화이트리스트 방식으로 변경할 예정입니다.

<br>

### 4. JWT를 통한 보안강화
- 현재 보안을 위해 Firebase를 사용하고 DB에 패스워드를 저장하지 않은 상태입니다.
- 보안성을 강화하기 위해 여기에 JWT를 통해 보안성을 추가로 강화할 예정입니다.
