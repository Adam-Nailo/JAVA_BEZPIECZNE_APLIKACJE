package pl.strefakursow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.strefakursow.security.secret.SecretAuthenticationProvider;
import pl.strefakursow.security.secret.SecretTokenFilter;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    @Qualifier("inMemoryMap")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureAuthenticationManager(
            AuthenticationManagerBuilder builder,
            SecretAuthenticationProvider secretProvider) throws Exception {
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder).and()
                .authenticationProvider(secretProvider);
    }

    @Configuration
    public static class PasswordEncoderConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Order(0)
    @Configuration
    public static class HttpBasicConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/secured-basic/**")
                    .authorizeRequests()
                    .antMatchers("/secured-basic/admin").hasRole("ADMIN")
                    .anyRequest().authenticated().and().httpBasic()
                    .and().csrf().disable();
        }
    }

    @Order(1)
    @Configuration
    public static class FormLoginConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/secured-form/**")
                    .authorizeRequests()
                    .antMatchers("/secured-form/login")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/secured-form/login")
                    .loginProcessingUrl("/secured-form/login");
        }
    }

    @Order(2)
    @Configuration
    public static class SecretConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationManager authManager;
        @Autowired
        private SecurityContextHolderStrategy securityContextStrategy;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/secured-secret")
                    .authorizeRequests().anyRequest()
                    .authenticated()
                    .and().addFilterBefore(new SecretTokenFilter(authManager, securityContextStrategy), BasicAuthenticationFilter.class)
                    .csrf().disable();
        }
    }

    @Order(3)
    @Configuration
    public static class RestApiConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**").authorizeRequests()
                    .anyRequest().authenticated().and().httpBasic()
                    .and().csrf().disable().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    public static class SecurityContextHolderConfig {
        @Bean
        public SecurityContextHolderStrategy securityContextHolderStrategy() {
            return SecurityContextHolder.getContextHolderStrategy();
        }
    }
}
