# OAuth_example
OAuth example resource server

간단한 OAuth 서버입니다.<br/>
다만 spring-security-oauth2.0 은 사용하지 않았습니다. OAuth 2.0 framework 을 나름 customizing 하여 적용한 server입니다.<br/>

<br/>
1. userController 를 이용해서 강제로 계정 생성<br/>
2. 해당 계정을 통해 OAuth 통신을 통해 token 발급<br/>
3. /simpleData/get을 통해 data 를 가져오면 성공<br/><br/> 

> spec
>- java<br/>
>- postgreSQL<br/>
>- JPA<br/>
>- Spring boot <br/>
>- No spring-security


OAuth 2.0 framework uri : https://www.oauth.com <br/> 
