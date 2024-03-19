package pl.strefakursow.security.secret;

import lombok.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Value
public class SecretToken extends AbstractAuthenticationToken {
    private String secret;
    private User principal;

    public SecretToken(Collection<? extends GrantedAuthority> authorities, String secret, User principal) {
        super(authorities);
        this.secret = secret;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return secret;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
