package com.artemgggi.webshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/productsList", "/addProduct").hasAuthority("ROLE_MANAGER")
                .anyRequest().authenticated()

                // Login Form Details
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .defaultSuccessUrl("/index", true)

                // Logout Form Details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                // Exception Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");
        return http.build();
    }
}
