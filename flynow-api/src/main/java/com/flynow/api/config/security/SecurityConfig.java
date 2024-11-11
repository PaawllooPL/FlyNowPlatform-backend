package com.flynow.api.config.security;

import com.flynow.api.config.security.jwt.JwtAuthenticationFilter;
import com.flynow.domain.models.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui",
            "/actuator",
            "/actuator/**"
            // other public endpoints of your API may be appended to this array
    };
    private static final String ALL_PATHS = "/**";
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(BASE_PATH + AUTHENTICATION + ALL_PATHS).permitAll()
                        .requestMatchers("/api/v1/auth-test").hasAuthority(RoleEnum.user.name())
                        .requestMatchers(BASE_PATH + TEST_CREATE_DATA + ALL_PATHS).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().permitAll()
//                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
