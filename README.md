# mileage system

**마일리지 서비스**

> 사용자들이 장소에 리뷰를 작성할 때 포인트 부여,  
> 전체/개인에 대한 포인트 부여 히스토리와
> 개인별 누적 포인트 관리
<br/> 
실행 방법 : 5
<br/>
<br/>

## 1. 개발환경
* Java 17
* Springboot 2.6.4
* Maven 3.6.3
* Spring Data JPA 
* MySQL 8.0.28
* Swagger-ui 
<br/>


## 2. 요구사항
* Review event 처리
  * 주어진 'action'에 따라 (ADD, MOD, DELETE) 처리
  * 처리에 맞게 포인트도 수정되어야함
    * 포인트 규칙
      * 내용 점수 : 1자 이상 작성 1점, 1장 이상 사진 1점
      * 보너스 점수 : 해당 장소의 첫 리뷰일 경우 1점
      * 포인트 수정될 때마다 이력이 남아야 함
<br/>      
  
      
* 포인트 조회
  * 개인 누적 포인트 조회
  * 개인 포인트 히스토리 조회
  * 전체 포인트 히스토리 조회
<br/>  
  

## 3. DB
* ERD
 ![erd](https://user-images.githubusercontent.com/55009516/177242624-beb60e6f-930f-4903-9da2-37b780899fdb.PNG)
 * place에는 주소가 있다고 가정하고 address column 추가.
 * pointhistory에 관련된 reviewId는, review가 삭제되면 관계성에 따라 문제가 생길 것으로 판단, review table과 관계를 갖지 않고 같은 값의 독립된 column 생성.
* 전제사항
  * user는 이미 존재한다고 가정. 회원가입 단계에서 db에 추가되었을 것.
  * place 경우에는 존재하지 않을 수도 있다고 가정. user가 리뷰 작성 시 새로운 place를 추가할 수 있을 것으로 예상하고 진행.
<br/>  
  

## 4. API 상세 설명
|DOMAIN|METHOD|URI|Description|
|---------|------|--------------------|--------------------------------------------|
| Reviews | POST | /events | action에 따라 리뷰, 포인트 처리 |
| Users | GET | /users/point?userId={userId} | 해당 user의 현재 총 point 조회 |
| PointHistories | GET | /pointhistories/user?userId={userId} | 해당 user의 총 point history 조회 |
| PointHistories | GET | /pointhistories/all?page={int}&size={int} | 모든 point history  조회, paging 방식 |
<br/>

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
    <br/>
      
  * **MOD** : 모든 수정 사항 DB에 적용 (내용, 사진, 장소)
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 해당 reviewId가 존재하지 않는다면 | "존재하지 않는 review예요!" 반환 |
    |+| 기존에 사진을 첨부하지 않았는데 사진을 첨부한 경우 | type:photo, action:increase1, "MOD" 성공" 반환 |
    |+| 기존에 사진을 첨부했으나 현재는 사진이 없는 경우 | type:photo, action:decrease1, "MOD" 성공" 반환 |
    |+| 기존에 보너스 점수 받지 않음 & 바뀐 장소 새로운 placeId인 경우 | type:bonus, action:increase1, "MOD" 성공" 반환 |
    |+| 기존에 보너스 점수 받지 않음 & 바뀐 장소 존재 placeId지만 해당 placeId 리뷰가 없는 경우  | type:bonus, action:increase1, "MOD" 성공" 반환 |
    <br/>
    
  * **DELETE**
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 해당 reviewId가 존재하지 않는다면 | "존재하지 않는 review예요!" 반환 |
    |+| 모든 상황 | 해당 review 삭제, 해당 reviewId 갖는 photo들 삭제, type:delete, action:decrease'해당 리뷰로 얻은 포인트', "DELETE 성공"  |
    <br/>
    
### "/users/point?userId={userId}"
* 시나리오
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 존재하지 않는 userId인 경우 | "없는 회원입니다. 포인트 조회 실패!" 반환 |
    |+| 모든 상황 | 해당 회원 point 반환 |
    <br/>
  
### "/pointhistories/user?userId={userId}"
* 시나리오
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |-| 존재하지 않는 userId인 경우 | "없는 회원입니다. 포인트 조회 실패!" 반환 |
    |+| 아직 포인트 이력이 없는 경우 | "아직 point history가 없어요!" 반환 |
    |+| 그 외 상황 | point history list 반환 |
    <br/>
    
### "/pointhistories/all?page={int}&size={int}"
* 시나리오
    |-|상황|처리|
    |---|--------------------------------------|--------------------------------------------|
    |+| 모든 상황 | 모든 회원의 모든 history page, size에 따라 최신순으로 반환 |
    <br/>


## 5. 실행 방법
* 참고 사항
  * ddl file은 resoureces 폴더에 **schema.sql** 로 존재
  <br/>
* Springboot project 실행
  * C:'file directory path'\target> java -jar triple-0.0.1-SNAPSHOT.jar
  <br/>
* Test  
  * 전제 사항
    * 저장된 user 목록 : (예시)'3ede0ef2-92b7-4817-a5f3-0c575361f745', '05f5af06-fafa-11ec-a809-3c7c3fc20bf9', '46b805d8-fafa-11ec-a809-3c7c3fc20bf9'
    * 저장된 place 목록 : (예시)'2e4baf1c-5acb-4efb-a1af-eddada31b00f', '261a0a74-fafa-11ec-a809-3c7c3fc20bf9'
    * 먼저 해당 user 중 하나의 userId로 review ADD 진행
    <br/>
  * api test
    * http://localhost:8080/swagger-ui.html 로 접속
    * test 하고자 하는 controller -> api 선택
    * "Try it out!" 버튼 클릭 후, Edit Value에 test 값 추가!
    * 페이지에서 입력 형식 등 상세 설명 확인 가능
    
    * **리뷰이벤트 처리**
      * review-api-controller -> "/events" 선택
      * Edit Value에 하단의 형식으로 test 값 추가
```json
{
    "type": "REVIEW",
    "action": "ADD", /* "MOD", "DELETE" */
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "content": "좋아요!",
    "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
    "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```

* * * * 결과 예시

```json
{
  "type": "REVIEW",
  "action": "MOD",
  "result": "MOD 성공"
}
```    
      
* * * **회원 누적 포인트 조회**
      * user-api-controller -> "/uesers/points" 선택
      * Edit Value에 userId 추가
      * 결과 예시   
      
```json
{
  "type": "POINT",
  "action": "GET",
  "result": {
    "points": 6
  }
}
```      

      
* * * **회원 포인트 기록 조회**
      * history-api-controller -> "/pointhistories/user" 선택
      * Edit Value에 userId 추가
      * 결과 예시
      

```json
{
  "type": "USER HISTORY",
  "action": "GET",
  "result": [
    {
      "pointId": 7,
      "type": "photo", 
      "action": "decrease1",
      "reviewId": "440a0658-dc5f-4878-9381-ebb7b2667712",
      "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
      "time": "2022-07-01 23:37:40"
    }
    ...
  ]
}
```   


* * * **모든 회원 포인트 기록 조회**
      * history-api-controller -> "/pointhistories/all" 선택
      * page, size에 숫자 입력
      * 결과 예시
```json
{
  "type": "ALL HISTORY",
  "action": "GET",
  "result": [
    {
      "pointId": 7,
      "type": "photo", 
      "action": "decrease1",
      "reviewId": "440a0658-dc5f-4878-9381-ebb7b2667712",
      "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
      "time": "2022-07-01 23:37:40"
    }
    ...
  ]
}
```
<br/>


    
## 6. 회고
* 느낀점
  * 모든 action마다 API를 따로 만들곤 했었는데, /events 처럼 여러 action을 한 API로 요청하는 방법이 있다는 것을 알게되었다.
  * data.sql 적용을 실패하여 모든 sql 코드를 schema.sql에 작성했다. Springboot 버전에 따라 다르다고 하나 정확하게 왜 안 되는지 알아내지 못했다.
  * test code 에 대한 이해와 연습이 필요한 것 같다.
  * 관계성(foreign key)를 이용하는 부분에서 코드 작성 시 오류가 조금 났었다. 영속성과 이를 이용하는 것에 대한 공부를 조금 더 해보는 게 좋을 것 같다는 생각이 들었다.
<br/>
