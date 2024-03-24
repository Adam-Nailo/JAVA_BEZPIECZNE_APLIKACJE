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
        users.put("goobar", new User("goobar", passwordEncoder.encode("goobar"), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
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
