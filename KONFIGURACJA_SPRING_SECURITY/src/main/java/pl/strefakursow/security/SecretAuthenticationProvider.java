package pl.strefakursow.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SecretAuthenticationProvider implements AuthenticationProvider {

    private SecretUserRepository secretUserRepository;

    public SecretAuthenticationProvider(SecretUserRepository secretUserRepository) {
        this.secretUserRepository = secretUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String secret = (String) authentication.getCredentials();
        return secretUserRepository.loadBySecret(secret).map(u -> {
            AbstractAuthenticationToken authResult = new AbstractAuthenticationToken(List.of(new SimpleGrantedAuthority("ROLE_USER"))) {

                @Override
                public Object getCredentials() {
                    return secret;
                }

                @Override
                public Object getPrincipal() {
                    return u.getUsername();
                }
            };
            authResult.setAuthenticated(true);
            return authResult;
        }).orElseThrow(() -> new BadCredentialsException("Bad secret"));

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
