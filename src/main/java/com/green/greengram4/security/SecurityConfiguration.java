package com.green.greengram4.security;

import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable())
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/user/signin"
                                , "/api/user/signup"
                                , "/api/user/signin"
                                , "/api/user/refresh-token"
                                , "/error"
                                , "/err"
                                , "/"
                                , "/pic/**"
                                , "/profile"
                                , "/profile/**"
                                , "/feed"
                                , "/feed/**"
                                , "/fimg/**"
                                , "/css/**"
                                , "/static/**"
                                , "/index.html"
                                , "/static/**"
                                , "/swagger.html"
                                , "/swagger-ui/**"
                                , "/v3/api-docs/**"
                                // swagger의 핵
                ).permitAll()
                                .anyRequest().authenticated()
                                /* .requestMatchers(HttpMethod.GET, "sign-api/refresh-token").permitAll()
                                .requestMatchers(HttpMethod.GET, "product/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/product/**").permitAll()
                                .requestMatchers("**exception**").permitAll()
                                .requestMatchers("/todo-api").hasAnyRole("USER", "ADMIN")
                                .anyRequest().hasRole("ADMIN") */
                )
                // permitAll은 아무값이 있어도 통과해둘것
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .build();
        // session을 사용하지 않겠음
        // session의 단점: 쿠키에 비해 더럽게 느림. 해킹의 위험성도 있는편.
        // csrf(Cross Site Request Forgery): 적어도 스프링 기법이 사용된 보안기법들 중 하나.
        // requestMatchers 반드시 구글링할것
        //
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 이놈은 위의 public을 참조하는 implement
    }

}
