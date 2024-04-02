package pl.strefakursow.security.oauth2.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.strefakursow.security.oauth2.client.MyUserInfo;

@RestController
@RequestMapping("/user")
public class MyUserInfoController {

    private OAuth2AuthorizedClientService clientService;

    public MyUserInfoController(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public MyUserInfo userInfo(OAuth2AuthenticationToken token) {
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient
                (token.getAuthorizedClientRegistrationId(), token.getName());
        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint =
                client.getClientRegistration().getProviderDetails().getUserInfoEndpoint();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + client.getAccessToken().getTokenValue());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(userInfoEndpoint.getUri(),
                HttpMethod.GET, entity, MyUserInfo.class).getBody();
    }
}
