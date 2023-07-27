package com.example.employe.management.config;

import com.example.employe.management.model.Role;
import com.example.employe.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration
@EnableWebSecurity


public class SecurityConfig  {
    @Autowired
    private EmployeeService employeeService;

    private final JwtTokenFilter jwtTokenFilter;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private  CorsFilterConfig corsFilterConfig;
    public  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(employeeService);
        return provider;

    }
    @Bean

    public JwtTokenFilter authFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)


                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST,"/api/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/departement/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/project/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/departement/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/project/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/departement/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name())
                        .requestMatchers(HttpMethod.GET,"/project/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/leaveRequest/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/leaveRequest/All","/leaveRequest/accepted","/leaveRequest/rejected").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/leaveRequest/CurrentYear/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/leaveRequest/thisYear").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/project/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/project/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/work/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())

                        .requestMatchers(HttpMethod.GET,"/work/**").hasAnyAuthority(Role.EMPLOYER.name(), Role.MANAGER.name(), Role.ADMIN.name())

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider());

        // Add the JWT token filter only for authenticated requests
        http.addFilterBefore(corsFilterConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();}






        // Add JWT token filter








protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> employeeService.loadUserByUsername(username));
    }



}
