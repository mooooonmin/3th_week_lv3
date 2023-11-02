package com.level3.admin.config;

import com.level3.admin.jwt.JwtAuthenticationFilter;
import com.level3.admin.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 사용할수있게 주입해주기

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // manager 권한을 가진사람의 접근?
        HttpSecurity manager = http
//                .csrf().disable()
//                .formLogin().disable()

                .httpBasic(withDefaults())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests((authz) -> authz
                        .antMatchers("/api/user/login", "/api/user/join").permitAll()
                        // 여기는 로그인과 회원가입이니까 모든사람이 접근 가능
                        .antMatchers("/user").hasRole("MANAGER")
                        // user 경로(여기는 추후에 바뀔듯? 강의 관련 Crud)는 manager 권한가진 사람만 접근 가능
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(jwtTokenProvider)) // JWT 필터 추가
                .cors(withDefaults());

        return manager.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/file/**",
            "/image/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/h2/**"
    };
}
