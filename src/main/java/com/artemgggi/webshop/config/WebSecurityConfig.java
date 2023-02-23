package com.artemgggi.webshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//                http.csrf().disable();
//                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/admin/**").hasRole("ROLE_MANAGER")
//                .requestMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//
//                // Login Form Details
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/j_spring_security_check")
//                .loginPage("/loginForm").permitAll()
//                .failureUrl("/loginForm?error=true")
//
//                // Logout Form Details
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
//                .logoutSuccessUrl("/")
//
//                // Exception Details
//                .and()
//                .exceptionHandling();
//        return http.build();
        http.cors().disable();
                http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(atzrx -> atzrx
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.formLogin().defaultSuccessUrl("/admin/index");
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
