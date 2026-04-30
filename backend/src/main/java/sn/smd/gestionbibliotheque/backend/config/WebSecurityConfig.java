package sn.smd.gestionbibliotheque.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.*;


import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private PasswordEncoder passwordEncoder;

    // =========================
    // SECURITY FILTER CHAIN
    // =========================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CORS SAFE
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // CSRF OFF (JWT)
                .csrf(csrf -> csrf.disable())

                // STATELESS API
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // =========================
                // PUBLIC ROUTES
                // =========================
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/api/v1/utilisateurs/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/actuator/**"
                        ).permitAll()

                        // PRIVATE ROUTES
                        .requestMatchers("/api/v1/**").authenticated()

                        .anyRequest().authenticated()
                )

                // =========================
                // JWT RESOURCE SERVER
                // =========================
//                .oauth2ResourceServer(oauth2 ->
//                        oauth2.jwt()
//                )

                // =========================
                // SECURITY ERRORS HANDLING
                // =========================
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )
                        .accessDeniedHandler((req, res, ex2) ->
                                res.sendError(HttpStatus.FORBIDDEN.value(), "Accès refusé")
                        )
                );

        return http.build();
    }

    // =========================
    // AUTH MANAGER
    // =========================
//    @Bean
//    public AuthenticationManager authenticationManager() {
//
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(userDetailsService);
//        provider.setHideUserNotFoundExceptions(false);
//
//        return new ProviderManager(provider);
//    }

    // =========================
    // CORS CONFIG (SAFE VERSION)
    // =========================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:3000"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}