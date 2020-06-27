# kakaopay-task
2020.06. kakaopay 사전 과제

카카오페이 머니 뿌리기 기능을 간소화된 REST API로 구현한다. 

# 요구사항

## HTTP header 공통 전달값

|key|설명|타입|
|------|---|---|
|X-USER-ID|요청한 사용자의 식별값|숫자|
|X-ROOM-ID|요청한 사용자가 속한 대화방의 식별값|문자|

충분한 잔액을 보유하고 있다고 가정하고, 잔액은 별도로 고려하지 않습니다. 

# API

## 뿌리기 API 
뿌릴 금액, 뿌릴 인원을 요청값으로 받아 뿌리기 건에 대한 고유 응답 token 을 response로 내려줍니다. 
- 뿌릴 금액을 인원수에 맞게 분배하여 저장합니다.
- token은 3자리 문자열로, 예측이 불가능해야 합니다.


```
POST /api/spray
```

### Http header(공통 인자)
|key|설명|타입|
|------|---|---|
|X-USER-ID|요청한 사용자의 식별값|숫자|
|X-ROOM-ID|요청한 사용자가 속한 대화방의 식별값|문자|

### RequestBody

|Parameter|Description|Type|
|------|---|---|
|sprayMonney|뿌릴 금액|숫자|
|sprayNumber|뿌릴 인원|숫자|

```
MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /api/spray
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8", X-USER-ID:"123", X-ROOM-ID:"room", Content-Length:"35"]
             Body = {"sprayMoney":1000,"sprayNumber":3}
    Session Attrs = {}
```

### Response

|Parameter|Description|Type|
|------|---|---|
|resultCode|결과|문자|
|token|토큰|문자|
```
{
    "resultCode":"OK",
    "token":"3Bx"
}
```

## 받기 API
뿌리기 시 발급된 token 에 해당하는 뿌리기건 중 아직 누구에게도 할당되지 않은 분배건 하나를 API를 호출한 사용자에게 할당합니다. 
- token은 10분간만 유효합니다. 

```
GET /api/get/{token}
```
### Http header(공통 인자)
|key|설명|타입|
|------|---|---|
|X-USER-ID|요청한 사용자의 식별값|숫자|
|X-ROOM-ID|요청한 사용자가 속한 대화방의 식별값|문자|

### Request
|Parameter|Description|Type|
|------|---|---|
|token|토큰(@PathVariable)|문자|


### Response
|Parameter|Description|Type|
|------|---|---|
|resultCode|결과|문자|
|receiveMoney|분배값|숫자|
```
{
    "resultCode":"OK",
    "receiveMoney":333
}
```

## 조회 API
뿌리기 시 발급된 token으로 뿌리기 건의 현재 상태를 보여줍니다. 
- 뿌린 사람 자신만 조회를 할 수 있습니다. 
- 뿌린 건은 7일간 조회할 수 있습니다. 

```
GET /list/{token}
```
### Http header(공통 인자)
|key|설명|타입|
|------|---|---|
|X-USER-ID|요청한 사용자의 식별값|숫자|
|X-ROOM-ID|요청한 사용자가 속한 대화방의 식별값|문자|

### Request
|Parameter|Description|Type|
|------|---|---|
|token|토큰(@PathVariable)|문자|

### Response
|Parameter|Description|Type|
|------|---|---|
|resultCode|결과|문자|
|sprayDate|뿌린 시각|시간|
|sprayMoney|뿌린 금액|숫자|
|receiveMoney|받기 완료된 금액|숫자|
|receiveList|받기 완료된 정보|List|
|receiveList.receiveMoney|받은 금액|숫자|
|receiveList.receiveUserId|받은 사용자 아이디|숫자|

```
{
    "resultCode":"OK",
    "sprayDate":"2020-06-27T10:12:04.325071",
    "sprayMoney":1000,
    "receiveMoney":1000,
    "receiveList":[
        {"receiveMoney":333,"receiveUserId":999},
        {"receiveMoney":333,"receiveUserId":null},
        {"receiveMoney":334,"receiveUserId":null}
        ]
}
```

# Error Code
enum class입니다. 
|key|code|message|
|------|---|---|
|OK|ok|SUCCESS|
|ERROR|err00|에러가 발생했습니다.|
|INVALID_REQUEST|err01|요청값을 확인해주세요.|
|NEED_MORE_MONEY|err02|인원수보다 많은 금액을 입력하세요.|
|INVALID_USER|err03|사용자 정보가 없습니다.|
|CANNOT_DUPLICATE|err04|중복해서 받을 수 없습니다.|


# 개발 환경
- Java 8
- Spring boot 
- Spring data jpa
- Querydsl
- Gradle 
- h2 database