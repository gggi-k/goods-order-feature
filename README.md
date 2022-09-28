
# goods-order-feature


## 링크

### Local
http://localhost:8080

### h2-console
http://localhost:8080/h2-console

### Heroku (원격)
https://goods-order-feature.herokuapp.com/


## 개발언어

* Java11
* Spring Boot - 경량 프레임워크
* Spring Security - 인가/ 인증 설정으로 인해
* Jwt - 무상태성 토큰
* H2 - 인메모리 DB 사용
* JPA - ORM
* Hibernate - ORM 프레임워크
* HATEOAS - REST API 엔드포인트 변경제한으로 인한 리소스 형태로 관리
* Bean Validation - 유효성검사
* Swagger - API DOCS 사용
* Junit - 단위테스트
* Mockito - 목객체 생성
* Gradle - 빌드


## 엔티티
**상품**

|속성|설명|
|----|-----|
| goodsId | 상품아이디 |
| name | 상품명 |
| price | 가격 |

**주문**

|속성|설명|
|----|-----|
| orderId | 주문아이디 |
| orderStatus | 주문상태(주문 접수, 주문 취소) |
| orderGoods | 주문상품목록 |
| delivery |  배달 |

**주문상품**

|속성|설명|
|----|-----|
| orderGoodsId | 주문상품아이디 |
| goodsId | 상품아이디 |
| name | 상품명 |
| count |  갯수 |
| price | 가격 |
| orderGoodsStatus | 주문상품상태(주문 접수, 주문 취소) |

**배달**

|속성|설명|
|----|-----|
| deliveryId | 배달아이디 |
| deliveryStatus | 배달상태(준비, 완료) |
| address | 이름 |
| addressDetail | 주민번호 |
| zipCode | 이름 |


## 구현기능

### 필수 
1. 상품관리 등록

2. 상품관리 수정

3. 상품관리 삭제

4. 주문관리 등록(상품목록 가능)

5. 주문 부분취소(일부상품 취소)

6. 주문 등록 및 취소시 알림(로그출력)

### 선택

7. 인증/인가 - 토큰

8. 로그인 사용자 관리

9. 예외처리
* 공통예외처리
* 수정시 해당 리소스 미존재시 에러
* 주문취소시 

10. 단위테스트

11. 추가적용 기술

## 별도 추가 구현 기능