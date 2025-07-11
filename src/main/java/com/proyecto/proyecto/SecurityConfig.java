package com.proyecto.proyecto;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 🔥 Esto es lo que evita el error 403 con fetch
            .authorizeHttpRequests()
                .requestMatchers("/", "/index.html", "/api/cuentas/**").permitAll() // ⚠️ Permite acceso a estas rutas
                .anyRequest().permitAll()
            .and()
            .formLogin().disable(); // Opcional, si no usas login automático
        return http.build();
    }
}
