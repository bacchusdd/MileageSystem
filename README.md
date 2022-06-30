# triple-mileage
*Assignment for 2022 TRIPLE BE recruitment*

**트리플 여행자 클럽 마일리지 서비스**

> 트리플 사용자들이 장소에 리뷰를 작성할 때 포인트 부여,  
> 전체/개인에 대한 포인트 부여 히스토리와
> 개인별 누적 포인트 관리
  
  
## 1. 개발환경
* Java
* Springboot
* JPA
* MySQL
* Swagger-ui



## 2. 요구사항
* Review event 처리
  * 주어진 'action'에 따라 (ADD, MOD, DELETE) 처리
  * 처리에 맞게 포인트도 수정되어야함
    * 포인트 규칙
      * 내용 점수 : 1자 이상 작성 1점, 1장 이상 사진 1점
      * 보너스 점수 : 해당 장소의 첫 리뷰일 경우 1점
      * 포인트 수정될 때마다 이력이 남아야 함
      
  
      
* 포인트 조회
  * 개인 누적 포인트 조회
  * 개인 포인트 히스토리 조회
  * 전체 포인트 히스토리 조회
  
  

## 3. DB
* 테이블
* 관계성
* ERD



## 4. API 상세 설명
|DOMAIN|METHOD|URI|Description|
|---------|------|--------------------|--------------------------------------------|
| Reviews | POST | /events | action에 따라 리뷰, 포인트 처리 |
| Users | GET | /users/point/{userId} | 해당 user의 현재 총 point 조회 |
| PointHistories | GET | /points/userHistory/{userId} | 해당 user의 총 point history 조회 |
| PointHistories | GET | /points/allHistory | 모든 point history 조회, paging 방식 |


### "/events"
### "/users/point/{userId}"
### "/points/userHistory/{userId}"
### "/points/allHistory"


## 5. 작동 방법
* Springboot project run
* Test
  * http://localhost:8080/swagger-ui.html로 접속
  * 
