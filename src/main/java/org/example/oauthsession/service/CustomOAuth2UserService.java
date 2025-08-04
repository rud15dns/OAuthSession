package org.example.oauthsession.service;

import org.example.oauthsession.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// OAuth2Service가 Login을 담당하는 Service단이다.
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService가 OAuth2UserService의 구현체이기 때문에
    // 이를 extends 하더라도 상관이 없다.

    // 부모 클래스의 속성과 메소드들을 자식 클래스에서 기능을 그대로 사용하거나 Override해서 커스텀.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        // 각각을 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        System.out.println("registrationID : " + registrationId);

        // 각 사이트마다 Response의 형식이 다르다.
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }

        else if (registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
//            System.out.println(oAuth2Response.getEmail() + " " + oAuth2Response.getName());
        }
        else{
            return null;
        }
        // 하드코딩으로 User 역할 강제
        String role = "ROLE_USER";

        return new CustomOAuth2User(oAuth2Response, role);

    }
}
