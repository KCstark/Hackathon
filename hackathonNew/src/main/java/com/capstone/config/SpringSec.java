// package com.capstone.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SpringSec {
//     @Bean
//     public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeRequests(authorizeRequests ->
//                 authorizeRequests
//                     .antMatchers("/public/**").permitAll() // Publicly accessible URLs
//                     .antMatchers("/user/**").hasRole("USER")  // URLs accessible by users with role USER
//                     .antMatchers("/admin/**").hasRole("ADMIN") // URLs accessible by users with role ADMIN
//             )
//             .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Authentication
//         return http.build();
//     }
// }

