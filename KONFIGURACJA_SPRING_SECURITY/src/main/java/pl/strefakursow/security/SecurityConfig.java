package pl.strefakursow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Order(0)
    @Configuration
    public static class HttpBasicConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/secured-basic")
                    .authorizeRequests().anyRequest()
                    .authenticated()
                    .and().httpBasic()
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
                    .loginProcessingUrl("/secured-form/login")
                    .and().csrf().disable();
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
