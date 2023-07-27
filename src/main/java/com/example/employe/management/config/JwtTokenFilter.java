package com.example.employe.management.config;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.service.EmployeeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@NoArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${auth.header}")
    private  String TOKEN_HEADER;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmployeeService employeeService;

    public JwtTokenFilter(JwtTokenUtil tokenUtil,
                          EmployerRepository userRepo) {
        this.jwtTokenUtil = tokenUtil;
        this.employeeService = employeeService;
    }



    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        final String header =request.getHeader(TOKEN_HEADER);
        System.out.println(header);

        final SecurityContext securityContext= SecurityContextHolder.getContext();
        System.out.println(securityContext);

        if (header!=null && securityContext.getAuthentication() ==null){
            String token=header.substring("Bearer ".length());
            String username= jwtTokenUtil.getUsername(token);
            if (username!=null){
                UserDetails userDetails= employeeService.loadUserByUsername(username);
                if (jwtTokenUtil.isTokenValid(token, userDetails)){
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((jakarta.servlet.http.HttpServletRequest) request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            }



        }
        filterChain.doFilter(request,response);


    }


}
