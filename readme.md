## OAuth2 소셜 로그인 구현 연습
- `개발자유미`님의 **OAuth2 클라이언트 세션**을 보고 소셜 로그인 구현을 따라해보고 있습니다.


### 발생했던 오류
google과 naver처럼 변수를 동일하게 설정했더니, kakao에서는 `invalidcredentials` 오류가 발생하였다.

1. logging의 level을 DEBUG까지 낮추어서 확인해보니, 401: [nobody]가 발생했음을 알 수 있었다.
- chatGPT에게 물어본 결과, Spring이 Kakao 토큰 요청 시에, client-id와 client-secret을 포함하지 않기 때문에 발생한 현상이라고 한다.
- 그래서 properties 파일에 `client-authentication-method`를 post로 명시하였다.

2. method를 post로 넣었더니 `IllegalArgumentException`이 발생하였다.
- Spring은 post가 아니라, client_secret_post를 허용해서 생긴 일이었다.
- 카카오는 client_id와 client_secret을 본문 파라미터로 전송하기 때문에 `client_secret_post` 방식으로 넣는 것 같다. 
  -  인증 토큰 발급 요청 메소드가 post이다.
  - Spring security 5.6 이후로 post가 client_secret_post로 변경되었다고 한다. 