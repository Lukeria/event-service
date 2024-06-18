package com.eventmanager.eventservice.config;

import com.eventmanager.eventservice.filter.JwtFilter;
import com.eventmanager.eventservice.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(@Qualifier("JwtServiceImpl2") JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
//                .csrf(customizer -> customizer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/authenticate").permitAll()
                        .requestMatchers("/api/v1/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/events/{eventUuid}/invitation/rvsp").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/events/{eventUuid}/invitation/confirm").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/storage/image/{directory}/{subdirectory}/{filename}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/storage/image/{directory}/{filename}").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(
//                                "/api/v1/events",
//                                "/api/v1/events/{uuid}",
//                                "/api/v1/events/{uuid}/invite",
//                                "/api/v1/event-type",
//                                "/api/v1/requests",
                                "/api/v1/events/{eventUuid}/checklists/progress")
                            .authenticated()
//                        .requestMatchers(HttpMethod.DELETE, " /api/v1/events/{id}")
//                            .hasRole("ORGANIZER")
                        .requestMatchers(
                                "/api/v1/events/{eventUuid}/budget-categories",
                                "/api/v1/events/{eventUuid}/budget-categories/**",
                                "/api/v1/events/{eventUuid}/checklists",
                                "/api/v1/events/{eventUuid}/checklists/**")
                            .hasRole("ORGANIZER")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/requests/{id}")
//                            .hasRole("USER")
                        .requestMatchers(
                                "/api/v1/events/{eventUuid}/guests",
                                "/api/v1/events/{eventUuid}/guests/**",
                                "/api/v1/events/{eventUuid}/invitation",
                                "/api/v1/events/{eventUuid}/invitation/**")
                            .hasRole("USER")
                        .anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .headers(customizer -> customizer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
