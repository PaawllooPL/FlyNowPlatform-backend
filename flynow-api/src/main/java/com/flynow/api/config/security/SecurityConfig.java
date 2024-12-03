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
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .anonymous(AnonymousConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(BASE_PATH + AUTHENTICATION + ALL_PATHS).permitAll()
                        .requestMatchers(BASE_PATH + IMAGE + IMAGE_FILENAME_PARAMETER).permitAll()
                        .requestMatchers(BASE_PATH + OFFERS).permitAll()
                        .requestMatchers(BASE_PATH + OFFERS_DETAILS_URL).permitAll()
                        .requestMatchers(BASE_PATH + OFFERS_BUY_URL).hasAnyAuthority(RoleEnum.user.name(), RoleEnum.admin.name())
                        .requestMatchers(BASE_PATH + OFFERS_CREATE_URL).hasAnyAuthority(RoleEnum.organizer.name(), RoleEnum.admin.name())
                        .requestMatchers(BASE_PATH + COMMENTS_ADD_URL).authenticated()
                        .requestMatchers(BASE_PATH + COMPANY_CREATE_URL).hasAnyAuthority(RoleEnum.user.name(), RoleEnum.admin.name())
                        .requestMatchers(BASE_PATH + OFFERS_USER_URL).hasAuthority(RoleEnum.user.name())
                        .requestMatchers(BASE_PATH + OFFERS_ORGANIZER_URL).hasAuthority(RoleEnum.organizer.name())
                        .requestMatchers(BASE_PATH + OFFERS_ORGANIZER_DETAILS_URL).hasAuthority(RoleEnum.organizer.name())
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
