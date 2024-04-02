package pl.strefakursow.security.oauth2.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class OAuthConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(configurer -> configurer
                        .antMatchers(HttpMethod.GET, "/api/messages")
                        .hasAuthority("SCOPE_message:read"))
                .oauth2ResourceServer(
                        OAuth2ResourceServerConfigurer::jwt);
    }
}