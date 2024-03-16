package pl.strefakursow.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@Component
public class AlwaysAllowFilter implements Filter {

    private SecurityContextHolderStrategy securityContextHolderStrategy;

    public AlwaysAllowFilter(SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        securityContextHolderStrategy.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials",
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
