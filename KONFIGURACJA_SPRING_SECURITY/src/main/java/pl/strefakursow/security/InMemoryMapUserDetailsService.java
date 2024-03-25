package pl.strefakursow.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component("inMemoryMap")
public class InMemoryMapUserDetailsService implements UserDetailsService {

    private ConcurrentMap<String, UserDetails> users;

    public InMemoryMapUserDetailsService(PasswordEncoder passwordEncoder) {
        users = new ConcurrentHashMap<>();
        users.put("goobar", user("goobar", passwordEncoder));
        users.put("user", user("user", passwordEncoder));
        users.put("admin", userWithRole("admin", passwordEncoder, "ROLE_ADMIN"));
        users.put("editor", userWithRole("editor", passwordEncoder, "ROLE_EDITOR"));
        users.put("reader", user("reader", passwordEncoder));
    }

    private UserDetails user(String username,
                             PasswordEncoder passwordEncoder) {
        return userWithRole(username, passwordEncoder, "ROLE_USER");
    }

    private UserDetails userWithRole(String username,
                                     PasswordEncoder passwordEncoder,
                                     String role) {
        return new User(username, passwordEncoder.encode(username),
                List.of(new SimpleGrantedAuthority(role)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails originalUserDetails = users.get(username);
        if (originalUserDetails == null) {
            throw new UsernameNotFoundException("Username doesn't exist");
        }
        return new User(originalUserDetails.getUsername(),
                originalUserDetails.getPassword(),
                originalUserDetails.isEnabled(),
                originalUserDetails.isAccountNonExpired(),
                originalUserDetails.isCredentialsNonExpired(),
                originalUserDetails.isAccountNonLocked(),
                originalUserDetails.getAuthorities());
    }
}
