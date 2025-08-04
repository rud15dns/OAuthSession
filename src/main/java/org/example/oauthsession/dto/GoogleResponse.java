package org.example.oauthsession.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{

    // 필드 변수는 private final로 하고 생성자는 Public으로 받는 이유에 대해서 생각해보았는데,
    // 생성자에서 public으로 한 번 초기화하면, 그 이후에 다른 곳에서 절대로 참조 주소를 변경하지 못하게 보호할 수 있기 때문인 것 같다.

    // 즉, 생성자를 통해서만 초기화 가능하고, 그 이후에는 변경 불가하다는 의미이다.
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute){
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
