package kr.project.goodsorderfeature.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.project.goodsorderfeature.core.security.filter.BearerJwtTokenAuthenticationFilter;
import kr.project.goodsorderfeature.core.security.filter.JsonUsernamePasswordAuthenticationFilter;
import kr.project.goodsorderfeature.core.security.handler.LoginFailureHandler;
import kr.project.goodsorderfeature.core.security.handler.LoginSuccessHandler;
import kr.project.goodsorderfeature.core.security.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.validation.Validator;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http,
                                               UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
                                               BearerJwtTokenAuthenticationFilter bearerJwtTokenAuthenticationFilter) throws Exception {

            return http
                    .headers()
                        .frameOptions()
                        .disable()
                        .and()
                    .httpBasic()
                        .disable()
                        .csrf()
                        .disable()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                    .addFilterAfter(bearerJwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAt(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                        .anyRequest()
                        .permitAll()
                        .and()
                    .formLogin()
                        .disable()
                    .exceptionHandling()
                        .and()
                    .build();
        }

        @Bean
        public UserDetailsService inMemoryUserDetailsService() {
            return new InMemoryUserDetailsManager(
                new User("test1", "$2a$10$TQ19kH.Q3c6qzn94Tgh6R.jWJveqW8plsAyEGnL1i09rRvmnSv9j.", Collections.emptyList()),
                new User("test2", "$2a$10$Bxiy1aHtGxbI2/4ohVavAegO8aRyP4sA5qvfIHhzfab6x9meXXABW", Collections.emptyList())
            );
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .build();
        }

        @Bean
        public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                                         ObjectMapper objectMapper,
                                                                                         Validator validator,
                                                                                         LoginSuccessHandler loginSuccessHandler,
                                                                                         LoginFailureHandler loginFailureHandler) {
            UsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager, objectMapper, validator);
            filter.setFilterProcessesUrl("/login");
            filter.setAuthenticationSuccessHandler(loginSuccessHandler);
            filter.setAuthenticationFailureHandler(loginFailureHandler);
            return filter;
        }

        @Bean
        public BearerJwtTokenAuthenticationFilter bearerJwtTokenAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
            return new BearerJwtTokenAuthenticationFilter(jwtAuthenticationProvider);
        }
}