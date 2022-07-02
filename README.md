# triple-mileage
*Assignment for 2022 TRIPLE BE recruitment*

**트리플 여행자 클럽 마일리지 서비스**

> 트리플 사용자들이 장소에 리뷰를 작성할 때 포인트 부여,  
> 전체/개인에 대한 포인트 부여 히스토리와
> 개인별 누적 포인트 관리
  
실행방법 -> 6번!  
  
  
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
* 전제사항
  * user는 이미 존재한다고 가정. 회원가입 단계에서 db에 추가되었을 것.
  * place 경우에는 존재하지 않을 수도 있다고 가정. user가 리뷰 작성 시 새로운 place를 추가할 수 있을 것으로 예상하고 진행.



## 4. API 상세 설명
|DOMAIN|METHOD|URI|Description|
|---------|------|--------------------|--------------------------------------------|
| Reviews | POST | /events | action에 따라 리뷰, 포인트 처리 |
| Users | GET | /users/point/{userId} | 해당 user의 현재 총 point 조회 |
| PointHistories | GET | /points/userHistory/{userId} | 해당 user의 총 point history 조회 |
| PointHistories | GET | /points/allHistory | 모든 point history 조회, paging 방식 |


//[docs/APIdescription.md](docs/APIdescription.md) : Detailed descriptions for API


### "/events"
* 시나리오
  * **ADD**
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 이미 존재하는 리뷰ID를 받은 상황 | "이미 존재하는 리뷰 id예요!" 반환 |
    |-| 이미 해당 장소 리뷰를 적은 상황 | "이미 review를 작성한 place예요!" 반환 |
    |+| 내용 1자 이상 | type:content, action:increase1, "ADD" 성공" 반환 |
    |+| 사진 1장 이상 | type:photo, action:increase1, "ADD" 성공" 반환 |
    |+| 새로운 장소 리뷰 : 새로운 placeId인 경우 | type:bonus, action:increase1, "ADD" 성공" 반환 | 
    |+| 새로운 장소 리뷰 : 존재하는 placeId지만 해당 placeId 리뷰가 없는 경우 | type:bonus, action:increase1, "ADD" 성공" 반환 |
    
      
  * **MOD** : 모든 수정 사항 DB에 적용 (내용, 사진, 장소)
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 해당 reviewId가 존재하지 않는다면 | "존재하지 않는 review예요!" 반환 |
    |+| 기존에 사진을 첨부하지 않았는데 사진을 첨부한 경우 | type:photo, action:increase1, "MOD" 성공" 반환 |
    |+| 기존에 사진을 첨부했으나 현재는 사진이 없는 경우 | type:photo, action:decrease1, "MOD" 성공" 반환 |
    |+| 기존에 보너스 점수 받지 않음 & 바뀐 장소 새로운 placeId인 경우 | type:bonus, action:increase1, "MOD" 성공" 반환 |
    |+| 기존에 보너스 점수 받지 않음 & 바뀐 장소 존재 placeId지만 해당 placeId 리뷰가 없는 경우  | type:bonus, action:increase1, "MOD" 성공" 반환 |
    
    
  * **DELETE**
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 해당 reviewId가 존재하지 않는다면 | "존재하지 않는 review예요!" 반환 |
    |+| 모든 상황 | 해당 review 삭제, 해당 reviewId 갖는 photo들 삭제, type:delete, action:decrease'해당 리뷰로 얻은 포인트', "DELETE 성공"  |
    
      
### "/users/point/{userId}"
### "/points/userHistory/{userId}"
### "/points/allHistory"



## 5. 프로젝트 상세 설명
* 프로젝트 구조
* 코드 설명 및 공부 내용
  
  //[docs/Codedescription.md](docs/APIdescription.md) : Detailed descriptions for Code


## 6. 작동 방법
* Springboot project run
* Test
  
  * project 실행
    *
  
  
  * api test
    * http://localhost:8080/swagger-ui.html 로 접속
    * /events
    *
    
    
## 7. 회고
* 느낀점
* 궁금증 및 개선사항
  * /events API 요청에 type이나 action을 넣지 않고, 헤더 등으로 처리한다면 
