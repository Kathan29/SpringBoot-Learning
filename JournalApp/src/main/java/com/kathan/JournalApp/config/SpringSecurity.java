package com.kathan.JournalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kathan.JournalApp.filter.JWTFilter;
import com.kathan.JournalApp.service.CustomUserServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {

	private final CustomUserServiceImpl customUserServiceImpl;
	private final JWTFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(request -> 
								request.requestMatchers("/public/**","/admin/**").permitAll()
								.requestMatchers("/journal/**","/user/**").authenticated()
								.requestMatchers("/admin/**").hasRole("ADMIN")
								.anyRequest().authenticated())
				           //.httpBasic(Customizer.withDefaults())
				           .csrf(csrf -> csrf.disable()) //.csrf(AbstractHttpConfigurer::disable)
				           .authenticationProvider(authenticationProvider())
				           .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				           .build();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserServiceImpl);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	



	
}
